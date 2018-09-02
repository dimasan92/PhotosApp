package ru.geekbrains.domain.interactor.photos;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import ru.geekbrains.domain.model.Photo;
import ru.geekbrains.domain.repository.IPhotosRepository;

@Singleton
public final class ChangeFavoriteStatusPersonalPhotoUseCase {

    private final IPhotosRepository photosRepository;

    @Inject
    ChangeFavoriteStatusPersonalPhotoUseCase(IPhotosRepository photosRepository) {
        this.photosRepository = photosRepository;
    }

    public Completable execute(Photo photo) {
        return photosRepository.changeFavoriteStatusPersonalPhoto(photo);
    }
}
