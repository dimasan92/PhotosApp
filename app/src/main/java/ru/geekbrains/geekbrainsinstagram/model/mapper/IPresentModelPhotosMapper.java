package ru.geekbrains.geekbrainsinstagram.model.mapper;

import java.util.List;

import ru.geekbrains.domain.model.PhotoModel;
import ru.geekbrains.geekbrainsinstagram.model.AppTheme;
import ru.geekbrains.geekbrainsinstagram.model.PresentPhotoModel;

public interface IPresentModelPhotosMapper {

    PhotoModel viewToDomain(final PresentPhotoModel photo);

    List<PresentPhotoModel> domainToView(final List<PhotoModel> photos);

    PresentPhotoModel domainToView(final PhotoModel photo);

    String viewToDomain(final AppTheme theme);

    AppTheme domainToView(final String theme);
}