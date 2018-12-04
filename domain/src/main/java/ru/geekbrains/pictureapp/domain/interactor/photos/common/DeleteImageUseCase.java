package ru.geekbrains.pictureapp.domain.interactor.photos.common;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import ru.geekbrains.pictureapp.domain.model.ImageModel;
import ru.geekbrains.pictureapp.domain.repository.PhotosRepository;

@Singleton
public final class DeleteImageUseCase {

    private final PhotosRepository photosRepository;

    @Inject
    DeleteImageUseCase(final PhotosRepository photosRepository) {
        this.photosRepository = photosRepository;
    }

    public Completable execute(final ImageModel imageModel) {
        return photosRepository.deleteImage(imageModel);
    }
}
