package ru.geekbrains.pictureapp.data.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.geekbrains.pictureapp.data.database.entities.FavoriteEntity;
import ru.geekbrains.pictureapp.domain.model.ImageModel;

@Singleton
public final class ImageMapperImpl implements ImageMapper {

    @Inject
    ImageMapperImpl() {
    }

    @Override
    public List<ImageModel> mapFavorites(final List<FavoriteEntity> favoriteEntities) {
        final List<ImageModel> list = new ArrayList<>(favoriteEntities.size());
        for (FavoriteEntity entity : favoriteEntities) {
            list.add(mapFavorite(entity));
        }
        return list;
    }

    private ImageModel mapFavorite(final FavoriteEntity photoEntity) {
        return new ImageModel(photoEntity.getId(), true, photoEntity.getFilePath());
    }

    @Override
    public FavoriteEntity mapToFavoriteEntity(final ImageModel imageModel) {
        return new FavoriteEntity(imageModel.getId(), imageModel.getFilePath());
    }
}
