package ru.geekbrains.pictureapp.domain.repository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.geekbrains.pictureapp.domain.model.ImageModel;

public interface PhotosRepository {

    Single<List<ImageModel>> getPicturesByQuery(final String query, final int count);

    Single<List<ImageModel>> getSavedPictures();

    Single<ImageModel> savePicture(final ImageModel imageModel, final byte[] photoArray);

    Single<List<ImageModel>> updatePictures(final List<ImageModel> imageModels);

    Single<ImageModel> getPlaceForNewPhoto();

    Single<List<ImageModel>> getPhotos();

    Single<List<ImageModel>> getFavorites();

    Single<ImageModel> setFavoriteStatus(final ImageModel imageModel);

    Completable deleteImage(final ImageModel imageModel);
}
