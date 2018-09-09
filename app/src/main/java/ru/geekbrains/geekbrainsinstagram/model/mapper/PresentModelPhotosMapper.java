package ru.geekbrains.geekbrainsinstagram.model.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.geekbrains.domain.model.PhotoModel;
import ru.geekbrains.domain.model.AppThemeModel;
import ru.geekbrains.geekbrainsinstagram.model.PresentPhotoModel;

@Singleton
public final class PresentModelPhotosMapper implements IPresentModelPhotosMapper {

    @Inject
    PresentModelPhotosMapper() {
    }

    @Override
    public PhotoModel viewToDomain(PresentPhotoModel photo) {
        return new PhotoModel(photo.getId(), photo.isFavorite());
    }

    @Override
    public PhotoModel viewToDomainWithFavoriteChange(PresentPhotoModel photo) {
        return new PhotoModel(photo.getId(), !photo.isFavorite());
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
}
