package ru.geekbrains.domain.repository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.geekbrains.domain.model.PhotoModel;

public interface PhotosRepository {

    Single<List<PhotoModel>> getCameraPhotos();

    Single<List<PhotoModel>> getFavorites();

    Completable changeFavoritePhotoStatus(final PhotoModel photo);

    Completable deletePhoto(final PhotoModel photo);
}
