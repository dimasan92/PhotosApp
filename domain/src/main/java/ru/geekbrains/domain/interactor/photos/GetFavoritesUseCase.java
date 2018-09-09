package ru.geekbrains.domain.interactor.photos;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import ru.geekbrains.domain.model.PhotoModel;
import ru.geekbrains.domain.repository.IPhotosRepository;

@Singleton
public final class GetFavoritesUseCase {

    private final IPhotosRepository repository;

    @Inject
    GetFavoritesUseCase(IPhotosRepository repository) {
        this.repository = repository;
    }

    public Single<List<PhotoModel>> execute() {
        return repository.getFavorites();
    }
}
