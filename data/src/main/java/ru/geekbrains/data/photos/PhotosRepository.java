package ru.geekbrains.data.photos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.data.mapper.IEntityPhotosMapper;
import ru.geekbrains.data.photos.personalphotos.FavoritePhotoEntity;
import ru.geekbrains.data.util.IFilesUtils;
import ru.geekbrains.domain.model.PhotoModel;
import ru.geekbrains.domain.repository.IPhotosRepository;

@Singleton
public final class PhotosRepository implements IPhotosRepository {

    private final PhotosDao dao;
    private final IEntityPhotosMapper mapper;
    private final IFilesUtils filesUtils;

    @Inject
    PhotosRepository(PhotosDao dao, IEntityPhotosMapper mapper, IFilesUtils filesUtils) {
        this.dao = dao;
        this.mapper = mapper;
        this.filesUtils = filesUtils;
    }

    @Override
    public Single<List<PhotoModel>> getPersonalPhotos() {
        return Single.fromCallable(this::getPersonalPhotosTask)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable changeFavoritePhotoStatus(PhotoModel photo) {
        return Completable.fromAction(() -> {
            if (photo.isFavorite()) {
                dao.addFavoritePhoto(mapper.domainToData(photo));
            } else {
                dao.deleteFavoritePhoto(mapper.domainToData(photo));
            }
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Completable deletePhoto(PhotoModel photo) {
        return Completable.fromAction(() -> {
            if (photo.isFavorite()) {
                dao.deleteFavoritePhoto(mapper.domainToData(photo));
            }
            if (filesUtils.deletePhotoFromDevice(photo.getId())) {
                return;
            }
            throw new IOException("Photo is not been deleted");
        }).subscribeOn(Schedulers.io());
    }

    private List<PhotoModel> getPersonalPhotosTask() {
        String[] photoIds = filesUtils.getPhotosIdsFromDevice();
        List<FavoritePhotoEntity> favorites = dao.getAllFavoritePhotos();

        List<PhotoModel> list = new ArrayList<>();

        boolean isFavorite;
        for (String photoId : photoIds) {
            isFavorite = false;
            for (FavoritePhotoEntity entity : favorites) {
                if (entity.getId().equals(photoId)) {
                    isFavorite = true;
                    break;
                }
            }
            list.add(new PhotoModel(photoId, isFavorite));
        }
        return list;
    }
}
