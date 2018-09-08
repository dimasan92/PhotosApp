package ru.geekbrains.domain.repository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.geekbrains.domain.model.PhotoModel;

public interface IPhotosRepository {

    Single<List<PhotoModel>> getPersonalPhotos();

    Completable changeFavoritePhotoStatus(PhotoModel photo);

    Completable deletePersonalPhoto(PhotoModel photo);
}
