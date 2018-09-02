package ru.geekbrains.domain.interactor.photos;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import ru.geekbrains.domain.model.Photo;
import ru.geekbrains.domain.repository.IPhotosRepository;

@Singleton
public final class DeletePersonalPhotoUseCase {

    private final IPhotosRepository repository;

    @Inject
    DeletePersonalPhotoUseCase(IPhotosRepository repository) {
        this.repository = repository;
    }

    public Completable execute(Photo photo) {
        return repository.deletePersonalPhoto(photo);
    }
}
