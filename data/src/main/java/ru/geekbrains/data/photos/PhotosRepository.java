package ru.geekbrains.data.photos;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.data.mapper.IEntityMapper;
import ru.geekbrains.domain.model.PhotoModel;
import ru.geekbrains.domain.repository.IPhotosRepository;

@Singleton
public final class PhotosRepository implements IPhotosRepository {

    private final PhotosDao dao;
    private final IEntityMapper mapper;

    @Inject
    PhotosRepository(PhotosDao dao, IEntityMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    public Single<List<PhotoModel>> getPersonalPhotos() {
        return dao.getAllPersonalPhotos()
                .map(mapper::dataToDomain)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable changeFavoriteStatusPersonalPhoto(PhotoModel photo) {
        return Completable.fromAction(() -> dao.updatePersonalPhoto(mapper.domainToData(photo)))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable deletePersonalPhoto(PhotoModel photo) {
        return Completable.fromAction(() -> dao.deletePersonalPhoto(mapper.domainToData(photo)))
                .subscribeOn(Schedulers.io());
    }
}
