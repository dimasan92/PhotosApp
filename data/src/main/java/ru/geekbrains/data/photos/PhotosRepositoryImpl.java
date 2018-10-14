package ru.geekbrains.data.photos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.data.mapper.EntityPhotoMapper;
import ru.geekbrains.data.photos.entities.FavoritePhotoEntity;
import ru.geekbrains.data.util.FilesUtils;
import ru.geekbrains.domain.model.PhotoModel;
import ru.geekbrains.domain.repository.PhotosRepository;

@Singleton
public final class PhotosRepositoryImpl implements PhotosRepository {

    private final PhotosDao photosDao;
    private final EntityPhotoMapper entityPhotoMapper;
    private final FilesUtils filesUtils;

    @Inject
    PhotosRepositoryImpl(PhotosDao photosDao, EntityPhotoMapper entityPhotoMapper, FilesUtils filesUtils) {
        this.photosDao = photosDao;
        this.entityPhotoMapper = entityPhotoMapper;
        this.filesUtils = filesUtils;
    }

    @Override
    public Single<List<PhotoModel>> getCameraPhotos() {
        return Single.fromCallable(this::getCameraPhotosTask)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<PhotoModel>> getFavorites() {
        return Single.fromCallable(photosDao::getAllFavorites)
                .map(entityPhotoMapper::dataToDomain)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable changeFavoritePhotoStatus(final PhotoModel photo) {
        return Completable.fromAction(() -> {
            if (photo.isFavorite()) {
                photosDao.addFavoritePhoto(entityPhotoMapper.domainToData(photo));
            } else {
                photosDao.deleteFavoritePhoto(entityPhotoMapper.domainToData(photo));
            }
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Completable deletePhoto(final PhotoModel photo) {
        return Completable.fromAction(() -> {
            if (photo.isFavorite()) {
                photosDao.deleteFavoritePhoto(entityPhotoMapper.domainToData(photo));
            }
            if (filesUtils.deletePhotoFromDevice(photo.getId())) {
                return;
            }
            throw new IOException("Photo is not been deleted");
        }).subscribeOn(Schedulers.io());
    }

    private List<PhotoModel> getCameraPhotosTask() {
        String[] photoIds = filesUtils.getPhotosIdsFromDevice();
        List<FavoritePhotoEntity> favorites = photosDao.getAllFavorites();

        List<PhotoModel> cameraPhotos = new ArrayList<>();

        boolean isFavorite;
        for (String photoId : photoIds) {
            isFavorite = false;
            for (FavoritePhotoEntity entity : favorites) {
                if (entity.getId().equals(photoId)) {
                    isFavorite = true;
                    break;
                }
            }
            cameraPhotos.add(new PhotoModel(photoId, isFavorite));
        }
        return cameraPhotos;
    }
}
