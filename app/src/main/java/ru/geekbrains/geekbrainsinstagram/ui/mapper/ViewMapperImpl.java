package ru.geekbrains.geekbrainsinstagram.ui.mapper;

import ru.geekbrains.domain.model.InnerStoragePhoto;
import ru.geekbrains.geekbrainsinstagram.ui.model.InnerStoragePhotoViewModel;

public final class ViewMapperImpl implements ViewMapper {

    @Override
    public InnerStoragePhoto viewToDomain(InnerStoragePhotoViewModel photoModel) {
        return new InnerStoragePhoto(photoModel.getId(), photoModel.getUri().toString(), photoModel.isFavorite());
    }
}
