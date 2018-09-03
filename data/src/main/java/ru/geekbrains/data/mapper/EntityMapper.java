package ru.geekbrains.data.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.geekbrains.data.photos.personalphotos.PersonalPhotoEntity;
import ru.geekbrains.domain.model.PhotoModel;

@Singleton
public final class EntityMapper implements IEntityMapper {

    @Inject
    EntityMapper() {
    }

    @Override
    public PersonalPhotoEntity domainToData(PhotoModel photo) {
        return new PersonalPhotoEntity(photo.getId(), photo.isFavorite());
    }

    @Override
    public List<PhotoModel> dataToDomain(List<PersonalPhotoEntity> entities) {
        List<PhotoModel> convertedPhotos = new ArrayList<>(entities.size());
        for (PersonalPhotoEntity entity : entities) {
            convertedPhotos.add(dataToDomain(entity));
        }
        return convertedPhotos;
    }

    @Override
    public PhotoModel dataToDomain(PersonalPhotoEntity entity) {
        return new PhotoModel(entity.getId(), entity.isFavorite());
    }
}
