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
import ru.geekbrains.pictureapp.data.database.ImagesDao;
import ru.geekbrains.pictureapp.data.database.entities.FavoriteEntity;
import ru.geekbrains.pictureapp.data.exception.NetworkConnectionException;
import ru.geekbrains.pictureapp.data.mapper.ImageMapper;
import ru.geekbrains.pictureapp.data.service.api.ApiPhotosService;
import ru.geekbrains.pictureapp.data.util.FileUtils;
import ru.geekbrains.pictureapp.data.util.NetworkUtils;
import ru.geekbrains.pictureapp.domain.model.ImageModel;
import ru.geekbrains.pictureapp.domain.repository.PhotosRepository;

@Singleton
public final class PhotosRepositoryImpl implements PhotosRepository {

    private final ImagesDao imagesDao;
    private final ApiPhotosService photosService;
    private final ImageMapper imageMapper;
    private final FileUtils fileUtils;
    private final NetworkUtils networkUtils;

    @Inject
    PhotosRepositoryImpl(final ImagesDao imagesDao, final ApiPhotosService photosService,
                         final ImageMapper imageMapper, final FileUtils fileUtils,
                         final NetworkUtils networkUtils) {
        this.imagesDao = imagesDao;
        this.photosService = photosService;
        this.imageMapper = imageMapper;
        this.fileUtils = fileUtils;
        this.networkUtils = networkUtils;
    }

    @Override
    public Single<List<ImageModel>> getPicturesByQuery(final String query, final int count) {
        if (networkUtils.isOnline()) {
            return getPicturesByQueryTask(query, count);
        } else {
            return Single.error(new NetworkConnectionException());
        }
    }

    private Single<List<ImageModel>> getPicturesByQueryTask(final String query, final int count) {
        return photosService.getSquarishRandomQueryPhoto(query, count)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .flattenAsObservable(models -> models)
                .map(model -> {
                    final String pictureExt = fileUtils.getPictureExt(model.getUrls().getRegular());
                    final String filename = fileUtils.getFilenameForId(model.getId(), pictureExt);
                    final boolean isFavorite = isFavorite(model.getId());
                    final String filePath = isPictureSaved(filename) ?
                            fileUtils.getPictureFilePath(filename) : "";
                    return new ImageModel(model.getId(), isFavorite, filePath,
                            model.getUrls().getRegular(), model.getUrls().getSmall(), pictureExt);
                }).toList();
    }

    @Override
    public Single<List<ImageModel>> getSavedPictures() {
        return getSavedPicturesTask();
    }

    private Single<List<ImageModel>> getSavedPicturesTask() {
        return Single.fromCallable(fileUtils::getPicturesNamesFromStorage)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .flattenAsObservable(entities -> entities)
                .map(mapFilenameToModel(fileUtils::getPictureFilePath))
                .toList();
    }

    @Override
    public Single<List<ImageModel>> updatePictures(final List<ImageModel> imageModels) {
        return updatePicturesTask(imageModels);
    }

    private Single<List<ImageModel>> updatePicturesTask(final List<ImageModel> imageModels) {
        return Observable.fromIterable(imageModels)
                .subscribeOn(Schedulers.computation())
                .map(model -> {
                    final String filename = fileUtils.getFilenameForId(model.getId(), model.getExtension());
                    final boolean isFavorite = isFavorite(model.getId());
                    final String filePath = isPictureSaved(filename) ?
                            fileUtils.getPictureFilePath(filename) : "";
                    return new ImageModel(model, isFavorite, filePath);
                }).toList();
    }

    private boolean isPictureSaved(final String filename) {
        return fileUtils.getPicturesNamesFromStorage().contains(filename);
    }

    @Override
    public Single<ImageModel> savePicture(final ImageModel imageModel, final byte[] photoArray) {
        return Single.fromCallable(() -> fileUtils.writePictureToDevice(imageModel, photoArray))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<ImageModel> getPlaceForNewPhoto() {
        return Single.fromCallable(() -> {
            final String id = UUID.randomUUID().toString();
            final String photoExt = fileUtils.getPhotoExt();
            final String filename = fileUtils.getFilenameForId(id, photoExt);
            final String filePath = fileUtils.getPhotoFilePath(filename);
            return new ImageModel(id, filePath, photoExt);
        })
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<ImageModel>> getPhotos() {
        return getPhotosTask();
    }

    private Single<List<ImageModel>> getPhotosTask() {
        return Single.fromCallable(fileUtils::getPhotosNamesFromStorage)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .flattenAsObservable(entities -> entities)
                .map(mapFilenameToModel(fileUtils::getPhotoFilePath))
                .toList();
    }

    private Function<String, ImageModel> mapFilenameToModel(final Function<String, String> pathConverter) {
        return filename -> {
            final String id = fileUtils.getIdFromFilename(filename);
            final boolean isFavorite = isFavorite(id);
            final String ext = fileUtils.getPhotoExt(filename);
            return new ImageModel(id, isFavorite, pathConverter.apply(filename), ext);
        };
    }

    private boolean isFavorite(final String id) {
        return imagesDao.getAllFavorites()
                .flattenAsObservable(entities -> entities)
                .map(FavoriteEntity::getId)
                .contains(id)
                .as(Single::blockingGet);
    }

    @Override
    public Single<List<ImageModel>> getFavorites() {
        return imagesDao.getAllFavorites()
                .map(imageMapper::mapFavorites)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<ImageModel> setFavoriteStatus(final ImageModel imageModel) {
        return Single.fromCallable(() -> {
            final FavoriteEntity favoriteEntity = imageMapper.mapToFavoriteEntity(imageModel);
            if (imageModel.isFavorite()) {
                imagesDao.addFavorite(favoriteEntity);
            } else {
                imagesDao.deleteFavorite(favoriteEntity);
            }
            return imageModel;
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Completable deleteImage(final ImageModel imageModel) {
        return Completable.fromAction(() -> {
            if (imageModel.isFavorite()) {
                imagesDao.deleteFavorite(imageMapper.mapToFavoriteEntity(imageModel));
            }
            if (fileUtils.deletePhotoFromDevice(imageModel)) {
                return;
            }
            throw new IOException("Photo is not been deleted");
        }).subscribeOn(Schedulers.io());
    }
}
