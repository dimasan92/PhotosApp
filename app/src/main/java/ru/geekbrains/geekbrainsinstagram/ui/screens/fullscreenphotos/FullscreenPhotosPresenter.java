package ru.geekbrains.geekbrainsinstagram.ui.screens.fullscreenphotos;

import java.util.List;

import ru.geekbrains.domain.model.PhotoModel;
import ru.geekbrains.geekbrainsinstagram.ui.base.BasePresenter;
import ru.geekbrains.geekbrainsinstagram.ui.common.NotifyingMessage;

public interface FullscreenPhotosPresenter extends BasePresenter<FullscreenPhotosPresenter.IView> {

    interface IView extends BasePresenter.BaseView {

        void updatePhotos(List<PhotoModel> photos);

        void deletePhoto(PhotoModel photo);

        void showNotifyingMessage(NotifyingMessage message);
    }

    void start(String[] photoIds);

    void deletePhoto(PhotoModel photo);
}
