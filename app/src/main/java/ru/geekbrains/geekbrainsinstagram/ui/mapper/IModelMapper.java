package ru.geekbrains.geekbrainsinstagram.ui.mapper;

import ru.geekbrains.domain.model.Photo;
import ru.geekbrains.geekbrainsinstagram.ui.model.PhotoModel;

public interface IModelMapper {

    Photo viewToDomain(final PhotoModel photo);
}
