package ru.geekbrains.domain.repository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.geekbrains.domain.model.Photo;

public interface IPhotosRepository {

    Completable savePersonalPhoto(Photo photo);

    Single<List<Photo>> getPersonalPhotos();

    Completable changeFavoriteStatusPersonalPhoto(Photo photo);

    Completable deletePersonalPhoto(Photo photo);
}
