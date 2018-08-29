package ru.geekbrains.geekbrainsinstagram.model.mapper;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.domain.model.Photo;
import ru.geekbrains.geekbrainsinstagram.model.AppTheme;
import ru.geekbrains.geekbrainsinstagram.model.PhotoModel;

public final class ModelMapper implements IModelMapper {

    @Override
    public Photo viewToDomain(PhotoModel photoModel) {
        return new Photo(photoModel.getId(), photoModel.getUri(), photoModel.isFavorite());
    }

    @Override
    public List<PhotoModel> domainToView(List<Photo> photos) {
        List<PhotoModel> convertedPhotos = new ArrayList<>(photos.size());
        for (Photo photo : photos) {
            convertedPhotos.add(domainToView(photo));
        }
        return convertedPhotos;
    }

    @Override
    public PhotoModel domainToView(Photo photo) {
        return new PhotoModel(photo.getId(), photo.getUri(), photo.isFavorite());
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
