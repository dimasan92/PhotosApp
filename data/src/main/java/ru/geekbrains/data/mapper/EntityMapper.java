package ru.geekbrains.data.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.geekbrains.data.photos.personalphotos.FavoritePhotoEntity;
import ru.geekbrains.domain.model.PhotoModel;

@Singleton
public final class EntityMapper implements IEntityMapper {

    @Inject
    EntityMapper() {
    }

    @Override
    public FavoritePhotoEntity domainToData(PhotoModel photo) {
        return new FavoritePhotoEntity(photo.getId());
    }
}
