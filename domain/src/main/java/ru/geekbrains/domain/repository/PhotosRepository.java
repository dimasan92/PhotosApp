package ru.geekbrains.domain.repository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.geekbrains.domain.model.PhotoModel;

public interface PhotosRepository {

    Single<List<PhotoModel>> getPhotosBySearch(final String query, final int count);

    Single<List<PhotoModel>> getSavedSearchPhotos();

    Single<PhotoModel> getPlaceForNewCameraPhoto();

    Single<List<PhotoModel>> getCameraPhotos();

    Single<List<PhotoModel>> getFavorites();

    Completable setFavoritePhotoStatus(final PhotoModel photoModel);

    Single<PhotoModel> saveSearchPhoto(final PhotoModel photoModel, final byte[] photoArray);

    Completable deletePhoto(final PhotoModel photoModel);

    Single<List<PhotoModel>> updateSearchPhotos(final List<PhotoModel> photoModels);
}
