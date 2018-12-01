package ru.geekbrains.pictureapp.domain.interactor.photos.camera;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import ru.geekbrains.pictureapp.domain.model.ImageModel;
import ru.geekbrains.pictureapp.domain.repository.PhotosRepository;

@Singleton
public final class GetPlaceForNewPhotoUseCase {

    private final PhotosRepository photosRepository;

    @Inject
    GetPlaceForNewPhotoUseCase(final PhotosRepository photosRepository) {
        this.photosRepository = photosRepository;
    }

    public Single<ImageModel> execute() {
        return photosRepository.getPlaceForNewPhoto();
    }
}
