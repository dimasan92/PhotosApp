package ru.geekbrains.geekbrainsinstagram.model.mapper;

import java.util.List;

import ru.geekbrains.domain.model.PhotoModel;
import ru.geekbrains.geekbrainsinstagram.model.ViewPhotoModel;

public interface ViewPhotoModelMapper {

    PhotoModel viewToDomain(final ViewPhotoModel photo);

    PhotoModel viewToDomainWithFavoriteChange(final ViewPhotoModel photo);

    List<ViewPhotoModel> domainToView(final List<PhotoModel> photos);

    ViewPhotoModel domainToView(final PhotoModel photo);
}
