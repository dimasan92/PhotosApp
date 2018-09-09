package ru.geekbrains.data.mapper;

import java.util.List;

import ru.geekbrains.data.photos.personalphotos.FavoritePhotoEntity;
import ru.geekbrains.domain.model.PhotoModel;

public interface IEntityPhotosMapper {

    FavoritePhotoEntity domainToData(final PhotoModel photo);

    List<PhotoModel> dataToDomain(final List<FavoritePhotoEntity> photos);

    PhotoModel dataToDomain(final FavoritePhotoEntity photo);
}
