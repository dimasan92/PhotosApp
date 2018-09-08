package ru.geekbrains.data.mapper;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.geekbrains.data.photos.personalphotos.FavoritePhotoEntity;
import ru.geekbrains.domain.model.PhotoModel;

@Singleton
public final class EntityPhotosMapper implements IEntityPhotosMapper {

    @Inject
    EntityPhotosMapper() {
    }

    @Override
    public FavoritePhotoEntity domainToData(PhotoModel photo) {
        return new FavoritePhotoEntity(photo.getId());
    }
}
