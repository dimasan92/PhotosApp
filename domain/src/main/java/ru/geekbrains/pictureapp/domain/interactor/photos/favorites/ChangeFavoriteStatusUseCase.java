package ru.geekbrains.pictureapp.domain.interactor.photos.favorites;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import ru.geekbrains.pictureapp.domain.model.ImageModel;
import ru.geekbrains.pictureapp.domain.repository.PhotosRepository;

@Singleton
public final class ChangeFavoriteStatusUseCase {

    private final PhotosRepository photosRepository;

    @Inject
    ChangeFavoriteStatusUseCase(final PhotosRepository photosRepository) {
        this.photosRepository = photosRepository;
    }

    public Single<ImageModel> execute(final ImageModel imageModel) {
        final ImageModel newImageModel = new ImageModel(imageModel, !imageModel.isFavorite());
        return photosRepository.setFavoriteStatus(newImageModel);
    }
}
