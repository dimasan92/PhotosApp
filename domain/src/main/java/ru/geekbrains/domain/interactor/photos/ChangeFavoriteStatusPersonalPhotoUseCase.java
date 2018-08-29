package ru.geekbrains.domain.interactor.photos;

import io.reactivex.Completable;
import ru.geekbrains.domain.model.Photo;
import ru.geekbrains.domain.repository.IPhotosRepository;

public final class ChangeFavoriteStatusPersonalPhotoUseCase {

    private final IPhotosRepository photosRepository;

    public ChangeFavoriteStatusPersonalPhotoUseCase(IPhotosRepository photosRepository) {
        this.photosRepository = photosRepository;
    }

    public Completable execute(Photo photo) {
        return photosRepository.changeFavoriteStatusPersonalPhoto(photo);
    }
}
