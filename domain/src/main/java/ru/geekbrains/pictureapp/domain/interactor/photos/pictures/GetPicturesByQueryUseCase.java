package ru.geekbrains.pictureapp.domain.interactor.photos.pictures;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import ru.geekbrains.pictureapp.domain.model.ImageModel;
import ru.geekbrains.pictureapp.domain.repository.PhotosRepository;

@Singleton
public final class GetPicturesByQueryUseCase {

    private final PhotosRepository photosRepository;

    @Inject
    GetPicturesByQueryUseCase(final PhotosRepository photosRepository) {
        this.photosRepository = photosRepository;
    }

    public Single<List<ImageModel>> execute(final String query, final int count) {
        return photosRepository.getPicturesByQuery(query, count);
    }
}
