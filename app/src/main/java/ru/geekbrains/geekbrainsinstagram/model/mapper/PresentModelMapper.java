package ru.geekbrains.geekbrainsinstagram.model.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.geekbrains.domain.model.Photo;
import ru.geekbrains.geekbrainsinstagram.model.AppTheme;
import ru.geekbrains.geekbrainsinstagram.model.PresentPhotoModel;

@Singleton
public final class PresentModelMapper implements IPresentModelMapper {

    @Inject
    PresentModelMapper() {
    }

    @Override
    public Photo viewToDomain(PresentPhotoModel photoModel) {
        return new Photo(photoModel.getId(), photoModel.getUri(), photoModel.isFavorite());
    }

    @Override
    public List<PresentPhotoModel> domainToView(List<Photo> photos) {
        List<PresentPhotoModel> convertedPhotos = new ArrayList<>(photos.size());
        for (Photo photo : photos) {
            convertedPhotos.add(domainToView(photo));
        }
        return convertedPhotos;
    }

    @Override
    public PresentPhotoModel domainToView(Photo photo) {
        return new PresentPhotoModel(photo.getId(), photo.getUri(), photo.isFavorite());
    }

    @Override
    public String viewToDomain(AppTheme theme) {
        return theme.toString();
    }

    @Override
    public AppTheme domainToView(String theme) {
        if (theme.equals(AppTheme.RED_THEME.toString())) {
            return AppTheme.RED_THEME;
        } else if (theme.equals(AppTheme.BLUE_THEME.toString())) {
            return AppTheme.BLUE_THEME;
        } else {
            return AppTheme.GREEN_THEME;
        }
    }
}
