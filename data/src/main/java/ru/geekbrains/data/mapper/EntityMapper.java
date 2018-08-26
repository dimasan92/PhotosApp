package ru.geekbrains.data.mapper;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.data.photos.personalphotos.PersonalPhotoEntity;
import ru.geekbrains.domain.model.Photo;

public final class EntityMapper implements IEntityMapper {

    @Override
    public PersonalPhotoEntity domainToData(Photo photo) {
        return new PersonalPhotoEntity(photo.getId().toString(),
                photo.getUri(),
                photo.isFavorite());
    }

    @Override
    public List<Photo> dataToDomain(List<PersonalPhotoEntity> entities) {
        List<Photo> convertedPhotos = new ArrayList<>(entities.size());
        for (PersonalPhotoEntity entity : entities) {
            convertedPhotos.add(dataToDomain(entity));
        }
        return convertedPhotos;
    }

    @Override
    public Photo dataToDomain(PersonalPhotoEntity entity) {
        return new Photo(entity.getId(), entity.getUri(), entity.isFavorite());
    }
}
