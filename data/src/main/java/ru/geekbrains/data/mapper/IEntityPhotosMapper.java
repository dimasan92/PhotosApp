package ru.geekbrains.data.mapper;

import ru.geekbrains.data.photos.personalphotos.FavoritePhotoEntity;
import ru.geekbrains.domain.model.PhotoModel;

public interface IEntityPhotosMapper {

    FavoritePhotoEntity domainToData(final PhotoModel photo);
}
