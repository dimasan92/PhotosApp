package ru.geekbrains.data.mapper;

import ru.geekbrains.data.photos.personalphotos.PersonalPhotoEntity;
import ru.geekbrains.domain.model.Photo;

public final class EntityMapper implements IEntityMapper {

    @Override
    public PersonalPhotoEntity domainToData(Photo photo) {
        return new PersonalPhotoEntity(photo.getId().toString(),
                photo.getUri(),
                photo.isFavorite());
    }
}
