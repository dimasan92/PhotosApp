package ru.geekbrains.geekbrainsinstagram.ui.screens.fullscreenphotos;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.geekbrains.geekbrainsinstagram.base.BasePresenter;
import ru.geekbrains.geekbrainsinstagram.di.fragment.FragmentScope;
import ru.geekbrains.geekbrainsinstagram.model.ViewPhotoModel;
import ru.geekbrains.geekbrainsinstagram.ui.common.NotifyingMessage;

@FragmentScope
public final class FullscreenPhotosPresenterImpl extends BasePresenter<FullscreenPhotosPresenter.IView>
        implements FullscreenPhotosPresenter {

    @Inject
    FullscreenPhotosPresenterImpl() {
    }

    @Override
    public void start() {
        view.showNotifyingMessage(NotifyingMessage.ERROR_LOAD_PHOTO);
    }

    @Override
    public void start(String[] photoIds) {
        List<ViewPhotoModel> photos = new ArrayList<>(photoIds.length);
        for (String photoId : photoIds) {
            photos.add(new ViewPhotoModel(photoId));
        }
        view.updatePhotos(photos);
    }

    @Override
    public void deletePhoto(ViewPhotoModel photo) {
        view.deletePhoto(photo);
    }
}
