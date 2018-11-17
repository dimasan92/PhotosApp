package ru.geekbrains.pictureapp.data.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.geekbrains.pictureapp.data.database.entities.FavoriteEntity;
import ru.geekbrains.pictureapp.domain.model.PhotoModel;

@Singleton
public final class PhotoMapperImpl implements PhotoMapper {

    @Inject PhotoMapperImpl() {
    }

    @Override public List<PhotoModel> mapFavorites(final List<FavoriteEntity> photos) {
        final List<PhotoModel> list = new ArrayList<>(photos.size());
        for (FavoriteEntity photo : photos) {
            list.add(mapFavorite(photo));
        }
        return list;
    }

    @Override public FavoriteEntity mapToFavoriteEntity(final PhotoModel photoModel) {
        return new FavoriteEntity(photoModel.getId(), photoModel.getFilePath());
    }

    private PhotoModel mapFavorite(final FavoriteEntity photoEntity) {
        return new PhotoModel(photoEntity.getId(), photoEntity.getFilePath());
    }

}
