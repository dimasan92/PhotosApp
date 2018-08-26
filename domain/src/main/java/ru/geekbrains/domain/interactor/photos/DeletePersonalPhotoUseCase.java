package ru.geekbrains.domain.interactor.photos;

import io.reactivex.Completable;
import ru.geekbrains.domain.model.Photo;
import ru.geekbrains.domain.repository.IPhotosRepository;

public final class DeletePersonalPhotoUseCase {

    private final IPhotosRepository repository;


    public DeletePersonalPhotoUseCase(IPhotosRepository repository) {
        this.repository = repository;
    }

    public Completable execute(Photo photo) {
        return repository.deletePersonalPhoto(photo);
    }
}
