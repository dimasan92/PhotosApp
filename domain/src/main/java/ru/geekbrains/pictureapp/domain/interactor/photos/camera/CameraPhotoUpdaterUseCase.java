package ru.geekbrains.pictureapp.domain.interactor.photos.camera;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

@Singleton
public final class CameraPhotoUpdaterUseCase {

    private final Subject<Boolean> updater;
    private CompositeDisposable disposables;

    @Inject CameraPhotoUpdaterUseCase() {
        disposables = new CompositeDisposable();
        updater = PublishSubject.create();
    }

    public void execute() {
        updater.onNext(true);
    }

    public void subscribe(final Consumer<Boolean> next) {
        disposables.add(updater.subscribe(next));
    }

    public void dispose() {
        disposables.dispose();
    }
}
