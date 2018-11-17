package ru.geekbrains.pictureapp.domain.interactor.photos;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import ru.geekbrains.pictureapp.domain.model.PhotoModel;
import ru.geekbrains.pictureapp.domain.repository.PhotosRepository;

@Singleton
public final class GetPhotosBySearchUseCase {

    private final PhotosRepository photosRepository;

    @Inject GetPhotosBySearchUseCase(final PhotosRepository photosRepository) {
        this.photosRepository = photosRepository;
    }

    public Single<List<PhotoModel>> execute(final String query, final int count) {
        return photosRepository.getPhotosBySearch(query, count);
    }
}
