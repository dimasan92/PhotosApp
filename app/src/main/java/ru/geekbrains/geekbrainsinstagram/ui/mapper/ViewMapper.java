package ru.geekbrains.geekbrainsinstagram.ui.mapper;

import ru.geekbrains.domain.model.InnerStoragePhoto;
import ru.geekbrains.geekbrainsinstagram.ui.model.InnerStoragePhotoViewModel;

public interface ViewMapper {

    InnerStoragePhoto viewToDomain(final InnerStoragePhotoViewModel photo);
}
