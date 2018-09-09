package ru.geekbrains.geekbrainsinstagram.model.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.geekbrains.domain.model.PhotoModel;
import ru.geekbrains.geekbrainsinstagram.model.AppTheme;
import ru.geekbrains.geekbrainsinstagram.model.PresentPhotoModel;

@Singleton
public final class PresentModelPhotosMapper implements IPresentModelPhotosMapper {

    @Inject
    PresentModelPhotosMapper() {
    }

    @Override
    public PhotoModel viewToDomain(PresentPhotoModel photoModel) {
        return new PhotoModel(photoModel.getId(), photoModel.isFavorite());
    }

    @Override
    public List<PresentPhotoModel> domainToView(List<PhotoModel> photos) {
        List<PresentPhotoModel> convertedPhotos = new ArrayList<>(photos.size());
        for (PhotoModel photo : photos) {
            convertedPhotos.add(domainToView(photo));
        }
        return convertedPhotos;
    }

    @Override
    public PresentPhotoModel domainToView(PhotoModel photo) {
        return new PresentPhotoModel(photo.getId(), photo.isFavorite());
    }

    @Override
    public String viewToDomain(AppTheme theme) {
        return theme.getThemeName();
    }

    @Override
    public AppTheme domainToView(String theme) {
        if (theme.equals(AppTheme.RED_THEME.getThemeName())) {
            return AppTheme.RED_THEME;
        } else if (theme.equals(AppTheme.BLUE_THEME.getThemeName())) {
            return AppTheme.BLUE_THEME;
        } else {
            return AppTheme.GREEN_THEME;
        }
    }
}
