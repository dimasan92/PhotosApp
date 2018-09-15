package ru.geekbrains.geekbrainsinstagram.model.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.geekbrains.domain.model.PhotoModel;
import ru.geekbrains.geekbrainsinstagram.model.ViewPhotoModel;

@Singleton
public final class ViewPhotoModelMapperImpl implements ViewPhotoModelMapper {

    @Inject
    ViewPhotoModelMapperImpl() {
    }

    @Override
    public PhotoModel viewToDomain(final ViewPhotoModel photo) {
        return new PhotoModel(photo.getId(), photo.isFavorite());
    }

    @Override
    public PhotoModel viewToDomainWithFavoriteChange(final ViewPhotoModel photo) {
        return new PhotoModel(photo.getId(), !photo.isFavorite());
    }

    @Override
    public List<ViewPhotoModel> domainToView(final List<PhotoModel> photos) {
        List<ViewPhotoModel> convertedPhotos = new ArrayList<>(photos.size());
        for (PhotoModel photo : photos) {
            convertedPhotos.add(domainToView(photo));
        }
        return convertedPhotos;
    }

    @Override
    public ViewPhotoModel domainToView(final PhotoModel photo) {
        return new ViewPhotoModel(photo.getId(), photo.isFavorite());
    }
}
