package ru.geekbrains.geekbrainsinstagram.ui.mapper;

import ru.geekbrains.domain.model.Photo;
import ru.geekbrains.geekbrainsinstagram.ui.model.PhotoModel;

public final class ModelMapper implements IModelMapper {

    @Override
    public Photo viewToDomain(PhotoModel photoModel) {
        return new Photo(photoModel.getId(), photoModel.getUri(), photoModel.isFavorite());
    }
}
