package ru.geekbrains.domain.interactor.photos;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import ru.geekbrains.domain.model.PhotoModel;
import ru.geekbrains.domain.repository.PhotosRepository;

@Singleton
public final class SavePhotoUseCase {

    private final PhotosRepository photosRepository;

    @Inject
    SavePhotoUseCase(PhotosRepository photosRepository) {
        this.photosRepository = photosRepository;
    }

    public Completable execute(final PhotoModel photo) {
        return photosRepository.deletePhoto(photo);
    }
}
