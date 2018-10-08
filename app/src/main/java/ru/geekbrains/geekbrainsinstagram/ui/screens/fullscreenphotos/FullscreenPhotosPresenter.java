package ru.geekbrains.geekbrainsinstagram.ui.screens.fullscreenphotos;

import java.util.List;

import ru.geekbrains.geekbrainsinstagram.base.IBasePresenter;
import ru.geekbrains.geekbrainsinstagram.model.ViewPhotoModel;
import ru.geekbrains.geekbrainsinstagram.ui.common.NotifyingMessage;

public interface FullscreenPhotosPresenter extends IBasePresenter<FullscreenPhotosPresenter.IView> {

    interface IView extends IBasePresenter.IView {

        void updatePhotos(List<ViewPhotoModel> photos);

        void deletePhoto(ViewPhotoModel photo);

        void showNotifyingMessage(NotifyingMessage message);
    }

    void start(String[] photoIds);

    void deletePhoto(ViewPhotoModel photo);
}
