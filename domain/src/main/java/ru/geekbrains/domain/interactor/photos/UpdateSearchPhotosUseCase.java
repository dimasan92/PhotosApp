package ru.geekbrains.domain.interactor.photos;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import ru.geekbrains.domain.model.PhotoModel;
import ru.geekbrains.domain.repository.PhotosRepository;

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
