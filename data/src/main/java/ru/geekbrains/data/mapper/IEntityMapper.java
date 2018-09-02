package ru.geekbrains.data.mapper;

import java.util.List;

import ru.geekbrains.data.photos.personalphotos.PersonalPhotoEntity;
import ru.geekbrains.domain.model.PhotoModel;

public interface IEntityMapper {

    PersonalPhotoEntity domainToData(final PhotoModel photo);

    List<PhotoModel> dataToDomain(final List<PersonalPhotoEntity> entities);

    PhotoModel dataToDomain(final PersonalPhotoEntity entity);
}
