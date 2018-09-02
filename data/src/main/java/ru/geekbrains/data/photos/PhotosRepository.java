package ru.geekbrains.data.photos;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.data.mapper.IEntityMapper;
import ru.geekbrains.domain.model.Photo;
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
    public Completable savePersonalPhoto(Photo photo) {
        return Completable.fromAction(() -> dao.insertPersonalPhoto(mapper.domainToData(photo)))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<Photo>> getPersonalPhotos() {
        return dao.getAllPersonalPhotos()
                .map(mapper::dataToDomain)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable changeFavoriteStatusPersonalPhoto(Photo photo) {
        return Completable.fromAction(() -> dao.updatePersonalPhoto(mapper.domainToData(photo)))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable deletePersonalPhoto(Photo photo) {
        return Completable.fromAction(() -> dao.deletePersonalPhoto(mapper.domainToData(photo)))
                .subscribeOn(Schedulers.io());
    }
}
