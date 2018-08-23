package ru.geekbrains.data.photos;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.data.innerstoragephotos.InnerStoragePhotosDao;
import ru.geekbrains.data.mapper.DataMapper;
import ru.geekbrains.domain.model.InnerStoragePhoto;
import ru.geekbrains.domain.repository.PhotosRepository;

public final class PhotosRepositoryImpl implements PhotosRepository {

    private final InnerStoragePhotosDao dao;
    private final DataMapper mapper;

    public PhotosRepositoryImpl(InnerStoragePhotosDao dao, DataMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    public Completable addInnerStoragePhoto(InnerStoragePhoto photo) {
        return Completable.fromAction(() -> dao.insert(mapper.domainToData(photo)))
                .subscribeOn(Schedulers.io());
    }
}
