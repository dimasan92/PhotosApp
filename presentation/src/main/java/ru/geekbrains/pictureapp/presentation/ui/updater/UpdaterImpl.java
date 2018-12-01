package ru.geekbrains.pictureapp.presentation.ui.updater;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import ru.geekbrains.pictureapp.domain.model.ImageModel;

@Singleton
public final class UpdaterImpl implements Updater {

    private final Subject<Update> updater;
    private final CompositeDisposable disposables;

    @Inject
    UpdaterImpl() {
        disposables = new CompositeDisposable();
        updater = PublishSubject.create();
    }

    @Override
    public void update(final ImageModel imageModel) {
        if (imageModel.isPhoto()) {
            updater.onNext(Update.PHOTO);
        } else {
            updater.onNext(Update.PICTURE);
        }
    }

    @Override
    public void subscribe(final Consumer<Update> consumer) {
        disposables.add(updater.subscribe(consumer));
    }

    @Override
    public void dispose() {
        disposables.dispose();
    }
}
