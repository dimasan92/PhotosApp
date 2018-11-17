package ru.geekbrains.pictureapp.domain.interactor.photos;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import ru.geekbrains.pictureapp.domain.model.PhotoModel;
import ru.geekbrains.pictureapp.domain.repository.PhotosRepository;

@Singleton
public final class DeletePhotoUseCase {

    private final PhotosRepository photosRepository;

    @Inject DeletePhotoUseCase(final PhotosRepository photosRepository) {
        this.photosRepository = photosRepository;
    }

    public Completable execute(final PhotoModel photoModel) {
        return photosRepository.deletePhoto(photoModel);
    }
}
