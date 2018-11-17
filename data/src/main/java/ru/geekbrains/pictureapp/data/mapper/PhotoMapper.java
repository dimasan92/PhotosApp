package ru.geekbrains.pictureapp.data.mapper;

import java.util.List;

import ru.geekbrains.pictureapp.data.database.entities.FavoriteEntity;
import ru.geekbrains.pictureapp.domain.model.PhotoModel;

public interface PhotoMapper {

    List<PhotoModel> mapFavorites(final List<FavoriteEntity> favoritePhotoEntities);

    FavoriteEntity mapToFavoriteEntity(final PhotoModel photoModel);
}
