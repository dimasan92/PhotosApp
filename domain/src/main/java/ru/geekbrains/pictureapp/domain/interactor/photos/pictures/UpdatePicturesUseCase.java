package ru.geekbrains.pictureapp.domain.interactor.photos.pictures;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import ru.geekbrains.pictureapp.domain.model.ImageModel;
import ru.geekbrains.pictureapp.domain.repository.PhotosRepository;

@Singleton
public final class UpdatePicturesUseCase {

    private final PhotosRepository repository;

    @Inject
    UpdatePicturesUseCase(PhotosRepository repository) {
        this.repository = repository;
    }

    public Single<List<ImageModel>> execute(final List<ImageModel> imageModels) {
        return repository.updatePictures(imageModels);
    }
}
