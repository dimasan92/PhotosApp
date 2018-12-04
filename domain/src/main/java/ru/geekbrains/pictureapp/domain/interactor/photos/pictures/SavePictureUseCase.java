package ru.geekbrains.pictureapp.domain.interactor.photos.pictures;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import ru.geekbrains.pictureapp.domain.model.ImageModel;
import ru.geekbrains.pictureapp.domain.repository.PhotosRepository;

@Singleton
public final class SavePictureUseCase {

    private final PhotosRepository photosRepository;

    @Inject
    SavePictureUseCase(final PhotosRepository photosRepository) {
        this.photosRepository = photosRepository;
    }

    public Single<ImageModel> execute(final ImageModel imageModel, final byte[] photoArray) {
        return photosRepository.savePicture(imageModel, photoArray);
    }
}
