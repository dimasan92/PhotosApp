package ru.geekbrains.geekbrainsinstagram.model.mapper;

import java.util.List;

import ru.geekbrains.domain.model.Photo;
import ru.geekbrains.geekbrainsinstagram.model.AppTheme;
import ru.geekbrains.geekbrainsinstagram.model.PresentPhotoModel;

public interface IPresentModelMapper {

    Photo viewToDomain(final PresentPhotoModel photo);

    List<PresentPhotoModel> domainToView(final List<Photo> photos);

    PresentPhotoModel domainToView(final Photo photo);

    String viewToDomain(final AppTheme theme);

    AppTheme domainToView(final String theme);
}
