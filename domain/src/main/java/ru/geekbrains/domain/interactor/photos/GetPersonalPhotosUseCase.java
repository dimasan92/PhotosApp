package ru.geekbrains.domain.interactor.photos;

import java.util.List;

import io.reactivex.Single;
import ru.geekbrains.domain.model.Photo;
import ru.geekbrains.domain.repository.IPhotosRepository;

public final class GetPersonalPhotosUseCase {

    private final IPhotosRepository repository;

    public GetPersonalPhotosUseCase(IPhotosRepository repository) {
        this.repository = repository;
    }

    public Single<List<Photo>> execute() {
        return repository.getPersonalPhotos();
    }
}
