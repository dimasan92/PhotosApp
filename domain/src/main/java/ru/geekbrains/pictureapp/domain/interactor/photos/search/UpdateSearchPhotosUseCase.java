package ru.geekbrains.pictureapp.domain.interactor.photos.search;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import ru.geekbrains.pictureapp.domain.model.PhotoModel;
import ru.geekbrains.pictureapp.domain.repository.PhotosRepository;

@Singleton
public final class UpdateSearchPhotosUseCase {

    private final PhotosRepository repository;

    @Inject UpdateSearchPhotosUseCase(PhotosRepository repository) {
        this.repository = repository;
    }

    public Single<List<PhotoModel>> execute(final List<PhotoModel> photoModels) {
        return repository.updateSearchPhotos(photoModels);
    }
}
