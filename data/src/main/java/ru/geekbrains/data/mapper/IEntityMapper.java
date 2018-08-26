package ru.geekbrains.data.mapper;

import ru.geekbrains.data.photos.personalphotos.PersonalPhotoEntity;
import ru.geekbrains.domain.model.Photo;

public interface IEntityMapper {

    PersonalPhotoEntity domainToData(final Photo photo);
}
