package ru.geekbrains.data.mapper;

import java.util.List;

import ru.geekbrains.data.database.entities.FavoriteEntity;
import ru.geekbrains.domain.model.PhotoModel;

public interface PhotoMapper {

    List<PhotoModel> mapFavorites(final List<FavoriteEntity> favoritePhotoEntities);

    FavoriteEntity mapToFavoriteEntity(final PhotoModel photoModel);
}
