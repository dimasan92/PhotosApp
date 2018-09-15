package ru.geekbrains.domain.interactor.photos;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import ru.geekbrains.domain.model.PhotoModel;
import ru.geekbrains.domain.repository.PhotosRepository;

@Singleton
public final class ChangeFavoritePhotoStatusUseCase {

    private final PhotosRepository photosRepository;

    @Inject
    ChangeFavoritePhotoStatusUseCase(PhotosRepository photosRepository) {
        this.photosRepository = photosRepository;
    }

    public Completable execute(final PhotoModel photo) {
        return photosRepository.changeFavoritePhotoStatus(photo);
    }
}
