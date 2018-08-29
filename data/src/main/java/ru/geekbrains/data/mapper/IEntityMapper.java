package ru.geekbrains.data.mapper;

import java.util.List;

import ru.geekbrains.data.photos.personalphotos.PersonalPhotoEntity;
import ru.geekbrains.domain.model.Photo;

public interface IEntityMapper {

    PersonalPhotoEntity domainToData(final Photo photo);

    List<Photo> dataToDomain(final List<PersonalPhotoEntity> entities);

    Photo dataToDomain(final PersonalPhotoEntity entity);
}
