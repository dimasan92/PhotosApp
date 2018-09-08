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
        return new FavoritePhotoEntity(photo.getId(), photo.isFavorite());
    }

    @Override
    public List<PhotoModel> dataToDomain(List<FavoritePhotoEntity> entities) {
        List<PhotoModel> convertedPhotos = new ArrayList<>(entities.size());
        for (FavoritePhotoEntity entity : entities) {
            convertedPhotos.add(dataToDomain(entity));
        }
        return convertedPhotos;
    }

    @Override
    public PhotoModel dataToDomain(FavoritePhotoEntity entity) {
        return new PhotoModel(entity.getId(), entity.isFavorite());
    }
}
