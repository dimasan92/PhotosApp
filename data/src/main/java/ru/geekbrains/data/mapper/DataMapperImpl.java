package ru.geekbrains.data.mapper;

import ru.geekbrains.data.innerstoragephotos.InnerStoragePhotoEntity;
import ru.geekbrains.domain.model.InnerStoragePhoto;

public final class DataMapperImpl implements DataMapper {

    @Override
    public InnerStoragePhotoEntity domainToData(InnerStoragePhoto photo) {
        return new InnerStoragePhotoEntity(photo.getId().toString(),
                photo.getUri(),
                photo.isFavorite());
    }
}
