package ru.geekbrains.domain.repository;

import io.reactivex.Completable;
import ru.geekbrains.domain.model.Photo;

public interface IPhotosRepository {

    Completable savePersonalPhoto(Photo photo);
}
