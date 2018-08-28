package ru.geekbrains.domain.interactor.photos;

import io.reactivex.Completable;
import ru.geekbrains.domain.model.Photo;
import ru.geekbrains.domain.repository.IPhotosRepository;

public final class SaveNewPersonalPhotoUseCase {

    private final IPhotosRepository repository;

    public SaveNewPersonalPhotoUseCase(IPhotosRepository repository) {
        this.repository = repository;
    }

    public Completable execute(Photo photo) {
        return repository.savePersonalPhoto(photo);
    }
}
