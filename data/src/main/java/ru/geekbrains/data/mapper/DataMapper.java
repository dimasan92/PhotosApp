package ru.geekbrains.data.mapper;

import ru.geekbrains.data.innerstoragephotos.InnerStoragePhotoEntity;
import ru.geekbrains.domain.model.InnerStoragePhoto;

public interface DataMapper {

    InnerStoragePhotoEntity domainToData(final InnerStoragePhoto photo);
}
