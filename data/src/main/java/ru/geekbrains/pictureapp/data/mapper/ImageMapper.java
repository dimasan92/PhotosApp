package ru.geekbrains.pictureapp.data.mapper;

import java.util.List;

import ru.geekbrains.pictureapp.data.database.entities.FavoriteEntity;
import ru.geekbrains.pictureapp.domain.model.ImageModel;

public interface ImageMapper {

    List<ImageModel> mapFavorites(final List<FavoriteEntity> favoriteEntities);

    FavoriteEntity mapToFavoriteEntity(final ImageModel imageModel);
}
