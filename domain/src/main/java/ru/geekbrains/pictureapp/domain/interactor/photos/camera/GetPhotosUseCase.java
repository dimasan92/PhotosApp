package ru.geekbrains.pictureapp.domain.interactor.photos.camera;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import ru.geekbrains.pictureapp.domain.model.ImageModel;
import ru.geekbrains.pictureapp.domain.repository.PhotosRepository;

@Singleton
public final class GetPhotosUseCase {

    private final PhotosRepository photosRepository;

    @Inject
    GetPhotosUseCase(final PhotosRepository photosRepository) {
        this.photosRepository = photosRepository;
    }

    public Single<List<ImageModel>> execute() {
        return photosRepository.getPhotos();
    }
}
