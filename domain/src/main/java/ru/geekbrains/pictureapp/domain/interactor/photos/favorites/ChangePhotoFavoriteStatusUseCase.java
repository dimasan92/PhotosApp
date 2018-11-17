package ru.geekbrains.pictureapp.domain.interactor.photos.favorites;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import ru.geekbrains.pictureapp.domain.model.PhotoModel;
import ru.geekbrains.pictureapp.domain.repository.PhotosRepository;

@Singleton
public final class ChangePhotoFavoriteStatusUseCase {

    private final PhotosRepository photosRepository;

    @Inject ChangePhotoFavoriteStatusUseCase(PhotosRepository photosRepository) {
        this.photosRepository = photosRepository;
    }

    public Single<PhotoModel> execute(final PhotoModel photoModel) {
        final PhotoModel newPhotoModel = new PhotoModel(photoModel, !photoModel.isFavorite());
        return photosRepository.setFavoritePhotoStatus(newPhotoModel);
    }
}
