package ru.geekbrains.domain.interactor.photos;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import ru.geekbrains.domain.model.PhotoModel;
import ru.geekbrains.domain.repository.PhotosRepository;

@Singleton
public final class GetCameraPhotosUseCase {

    private final PhotosRepository photosRepository;

    @Inject
    GetCameraPhotosUseCase(PhotosRepository photosRepository) {
        this.photosRepository = photosRepository;
    }

    public Single<List<PhotoModel>> execute() {
        return photosRepository.getCameraPhotos();
    }
}
