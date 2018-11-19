package ru.geekbrains.pictureapp.data.repository;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.pictureapp.data.database.PhotosDao;
import ru.geekbrains.pictureapp.data.database.entities.FavoriteEntity;
import ru.geekbrains.pictureapp.data.exception.NoNetworkConnectionException;
import ru.geekbrains.pictureapp.data.mapper.PhotoMapper;
import ru.geekbrains.pictureapp.data.service.api.ApiPhotosService;
import ru.geekbrains.pictureapp.data.util.FileUtils;
import ru.geekbrains.pictureapp.data.util.NetworkUtils;
import ru.geekbrains.pictureapp.domain.model.PhotoModel;
import ru.geekbrains.pictureapp.domain.repository.PhotosRepository;

@Singleton
public final class PhotosRepositoryImpl implements PhotosRepository {

    private final PhotosDao photosDao;
    private final ApiPhotosService photosService;
    private final PhotoMapper photoMapper;
    private final FileUtils fileUtils;
    private final NetworkUtils networkUtils;

    @Inject PhotosRepositoryImpl(final PhotosDao photosDao, final ApiPhotosService photosService,
                                 final PhotoMapper photoMapper,
                                 final FileUtils fileUtils, final NetworkUtils networkUtils) {
        this.photosDao = photosDao;
        this.photosService = photosService;
        this.photoMapper = photoMapper;
        this.fileUtils = fileUtils;
        this.networkUtils = networkUtils;
    }

    @Override public Single<List<PhotoModel>> getPhotosBySearch(final String query, final int count) {
        if (networkUtils.isOnline()) {
            return getPhotosBySearchTask(query, count);
        } else {
            return Single.error(new NoNetworkConnectionException());
        }
    }

    @Override public Single<List<PhotoModel>> getSavedSearchPhotos() {
        return getSavedSearchPhotosTask();
    }

    @Override public Single<List<PhotoModel>> updateSearchPhotos(List<PhotoModel> photoModels) {
        return updateSearchPhotosTask(photoModels);
    }

    @Override public Single<PhotoModel> getPlaceForNewCameraPhoto() {
        return Single.fromCallable(() -> {
            final String id = UUID.randomUUID().toString();
            final String photoExt = fileUtils.getCameraPhotoExt();
            final String filename = fileUtils.getFilenameForId(id, photoExt);
            final String filePath = fileUtils.getCameraPhotoFilePath(filename);
            return new PhotoModel(id, filePath, photoExt);
        })
                .subscribeOn(Schedulers.io());
    }

    @Override public Single<List<PhotoModel>> getCameraPhotos() {
        return getCameraPhotosTask();
    }

    @Override public Single<List<PhotoModel>> getFavorites() {
        return photosDao.getAllFavorites()
                .map(photoMapper::mapFavorites)
                .subscribeOn(Schedulers.io());
    }

    @Override public Single<PhotoModel> setFavoritePhotoStatus(final PhotoModel photoModel) {
        return Single.fromCallable(() -> {
            final FavoriteEntity favoriteEntity = photoMapper.mapToFavoriteEntity(photoModel);
            if (photoModel.isFavorite()) {
                photosDao.addFavorite(favoriteEntity);
            } else {
                photosDao.deleteFavorite(favoriteEntity);
            }
            return photoModel;
        }).subscribeOn(Schedulers.io());
    }

    @Override public Single<PhotoModel> saveSearchPhoto(final PhotoModel photoModel, final byte[] photoArray) {
        return Single.fromCallable(() -> fileUtils.writeSearchPhotoToDevice(photoModel, photoArray))
                .subscribeOn(Schedulers.io());
    }

    @Override public Completable deletePhoto(final PhotoModel photoModel) {
        return Completable.fromAction(() -> {
            if (photoModel.isFavorite()) {
                photosDao.deleteFavorite(photoMapper.mapToFavoriteEntity(photoModel));
            }
            if (fileUtils.deletePhotoFromDevice(photoModel)) {
                return;
            }
            throw new IOException("Photo is not been deleted");
        }).subscribeOn(Schedulers.io());
    }

    private Single<List<PhotoModel>> getPhotosBySearchTask(final String query, final int count) {
        return photosService.getSquarishRandomQueryPhoto(query, count)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .flattenAsObservable(models -> models)
                .map(model -> {
                    final String ext = fileUtils.getSearchPhotoExt(model.getUrls().getRegular());
                    final String filename = fileUtils.getFilenameForId(model.getId(), ext);
                    final boolean isFavorite = isFavorite(model.getId());
                    final String filePath = isSearchSaved(filename) ?
                            fileUtils.getSearchPhotoFilePath(filename) : "";
                    return new PhotoModel(model.getId(), isFavorite, filePath,
                            model.getUrls().getRegular(), model.getUrls().getSmall(), ext);
                }).toList();
    }

    private Single<List<PhotoModel>> updateSearchPhotosTask(final List<PhotoModel> photoModels) {
        return Observable.fromIterable(photoModels)
                .observeOn(Schedulers.computation())
                .map(model -> {
                    final String filename = fileUtils.getFilenameForId(model.getId(), model.getPhotoExt());
                    final boolean isFavorite = isFavorite(model.getId());
                    final String filePath = isSearchSaved(filename) ?
                            fileUtils.getSearchPhotoFilePath(filename) : "";
                    return new PhotoModel(model, isFavorite, filePath);
                }).toList();
    }

    private Single<List<PhotoModel>> getSavedSearchPhotosTask() {
        return Single.fromCallable(fileUtils::getSearchPhotosNamesFromStorage)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .flattenAsObservable(entities -> entities)
                .map(mapFilenameToModel(fileUtils::getSearchPhotoFilePath))
                .toList();
    }

    private Single<List<PhotoModel>> getCameraPhotosTask() {
        return Single.fromCallable(fileUtils::getCameraPhotosNamesFromStorage)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .flattenAsObservable(entities -> entities)
                .map(mapFilenameToModel(fileUtils::getCameraPhotoFilePath))
                .toList();
    }

    private Function<String, PhotoModel> mapFilenameToModel(final Function<String, String> pathConverter) {
        return filename -> {
            final String id = fileUtils.getIdFromFilename(filename);
            final boolean isFavorite = isFavorite(id);
            final String ext = fileUtils.getPhotoExt(filename);
            return new PhotoModel(id, isFavorite, pathConverter.apply(filename), ext);
        };
    }

    private boolean isFavorite(final String id) {
        return photosDao.getAllFavorites()
                .flattenAsObservable(entities -> entities)
                .map(FavoriteEntity::getId)
                .contains(id)
                .as(Single::blockingGet);
    }

    private boolean isSearchSaved(final String filename) {
        return fileUtils.getSearchPhotosNamesFromStorage().contains(filename);
    }
}
