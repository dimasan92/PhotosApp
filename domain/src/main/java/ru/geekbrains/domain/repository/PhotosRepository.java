package ru.geekbrains.domain.repository;

import io.reactivex.Completable;
import ru.geekbrains.domain.model.InnerStoragePhoto;

public interface PhotosRepository {

    Completable addInnerStoragePhoto(InnerStoragePhoto photo);
}
