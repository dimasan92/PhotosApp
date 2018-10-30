package ru.geekbrains.domain.interactor.photos;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import ru.geekbrains.domain.model.PhotoModel;
import ru.geekbrains.domain.repository.PhotosRepository;

@Singleton
public final class GetPlaceForNewCameraPhotoUseCase {

    private final PhotosRepository photosRepository;

    @Inject GetPlaceForNewCameraPhotoUseCase(final PhotosRepository photosRepository) {
        this.photosRepository = photosRepository;
    }

    public Single<PhotoModel> execute() {
        return photosRepository.getPlaceForNewCameraPhoto();
    }
}
