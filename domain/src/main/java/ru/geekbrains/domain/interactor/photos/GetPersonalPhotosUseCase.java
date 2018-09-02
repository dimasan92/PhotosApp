package ru.geekbrains.domain.interactor.photos;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import ru.geekbrains.domain.model.Photo;
import ru.geekbrains.domain.repository.IPhotosRepository;

@Singleton
public final class GetPersonalPhotosUseCase {

    private final IPhotosRepository repository;

    @Inject
    GetPersonalPhotosUseCase(IPhotosRepository repository) {
        this.repository = repository;
    }

    public Single<List<Photo>> execute() {
        return repository.getPersonalPhotos();
    }
}
