package ru.geekbrains.domain.interactor.photos;

import io.reactivex.Completable;
import ru.geekbrains.domain.model.InnerStoragePhoto;
import ru.geekbrains.domain.repository.PhotosRepository;

public final class AddNewInnerStoragePhotoUseCase {

    private final PhotosRepository repository;


    public AddNewInnerStoragePhotoUseCase(PhotosRepository repository) {
        this.repository = repository;
    }

    public Completable execute(InnerStoragePhoto photo){
        return repository.addInnerStoragePhoto(photo);
    }
}
