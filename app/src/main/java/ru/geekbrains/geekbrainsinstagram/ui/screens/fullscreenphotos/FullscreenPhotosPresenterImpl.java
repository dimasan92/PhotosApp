package ru.geekbrains.geekbrainsinstagram.ui.screens.fullscreenphotos;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.geekbrains.domain.model.PhotoModel;
import ru.geekbrains.geekbrainsinstagram.ui.base.BasePresenterImpl;
import ru.geekbrains.geekbrainsinstagram.ui.common.NotifyingMessage;

public final class FullscreenPhotosPresenterImpl extends BasePresenterImpl<FullscreenPhotosPresenter.IView>
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
        List<PhotoModel> photos = new ArrayList<>(photoIds.length);
        for (String photoId : photoIds) {
//            photos.add(new ViewPhotoModel(photoId));
        }
        view.updatePhotos(photos);
    }

    @Override
    public void deletePhoto(PhotoModel photo) {
        view.deletePhoto(photo);
    }
}
