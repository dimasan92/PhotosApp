package ru.geekbrains.data.photos;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.data.mapper.IEntityMapper;
import ru.geekbrains.domain.model.Photo;
import ru.geekbrains.domain.repository.IPhotosRepository;

public final class PhotosRepository implements IPhotosRepository {

    private final PhotosDao dao;
    private final IEntityMapper mapper;

    public PhotosRepository(PhotosDao dao, IEntityMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    public Completable savePersonalPhoto(Photo photo) {
        return Completable.fromAction(() -> dao.insertPersonalPhoto(mapper.domainToData(photo)))
                .subscribeOn(Schedulers.io());
    }
}
