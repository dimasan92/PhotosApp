package ru.geekbrains.pictureapp.domain.interactor.photos.search;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import ru.geekbrains.pictureapp.domain.model.PhotoModel;
import ru.geekbrains.pictureapp.domain.repository.PhotosRepository;

@Singleton
public final class GetSavedSearchPhotosUseCase {

    private final PhotosRepository photosRepository;

    @Inject GetSavedSearchPhotosUseCase(final PhotosRepository photosRepository) {
        this.photosRepository = photosRepository;
    }

    public Single<List<PhotoModel>> execute() {
        return photosRepository.getSavedSearchPhotos();
    }
}
