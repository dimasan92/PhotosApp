package ru.geekbrains.domain.repository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.geekbrains.domain.model.PhotoModel;

public interface IPhotosRepository {

    Single<List<PhotoModel>> getPersonalPhotos();

    Single<List<PhotoModel>> getFavorites();

    Completable changeFavoritePhotoStatus(PhotoModel photo);

    Completable deletePhoto(PhotoModel photo);
}
