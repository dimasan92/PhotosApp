package ru.geekbrains.pictureapp.domain.interactor.photos.search;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import ru.geekbrains.pictureapp.domain.model.PhotoModel;
import ru.geekbrains.pictureapp.domain.repository.PhotosRepository;

@Singleton
public final class SaveSearchPhotoUseCase {

    private final PhotosRepository photosRepository;

    @Inject
    SaveSearchPhotoUseCase(final PhotosRepository photosRepository) {
        this.photosRepository = photosRepository;
    }

    public Single<PhotoModel> execute(final PhotoModel photoModel, final byte[] photoArray) {
        return photosRepository.saveSearchPhoto(photoModel, photoArray);
    }
}
