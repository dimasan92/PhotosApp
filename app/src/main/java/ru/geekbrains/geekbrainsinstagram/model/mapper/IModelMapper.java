package ru.geekbrains.geekbrainsinstagram.model.mapper;

import java.util.List;

import ru.geekbrains.domain.model.Photo;
import ru.geekbrains.geekbrainsinstagram.model.PhotoModel;

public interface IModelMapper {

    Photo viewToDomain(final PhotoModel photo);

    List<PhotoModel> domainToView(final List<Photo> photos);

    PhotoModel domainToView(final Photo photo);
}
