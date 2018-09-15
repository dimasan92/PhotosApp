package ru.geekbrains.data.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.geekbrains.data.photos.entities.FavoritePhotoEntity;
import ru.geekbrains.domain.model.PhotoModel;

@Singleton
public final class EntityPhotosMapperImpl implements EntityPhotosMapper {

    @Inject
    EntityPhotosMapperImpl() {
    }

    @Override
    public FavoritePhotoEntity domainToData(final PhotoModel photo) {
        return new FavoritePhotoEntity(photo.getId());
    }

    @Override
    public List<PhotoModel> dataToDomain(final List<FavoritePhotoEntity> photos) {
        List<PhotoModel> list = new ArrayList<>(photos.size());
        for (FavoritePhotoEntity photo : photos) {
            list.add(dataToDomain(photo));
        }
        return list;
    }

    @Override
    public PhotoModel dataToDomain(final FavoritePhotoEntity photo) {
        return new PhotoModel(photo.getId(), true);
    }
}
