package ru.geekbrains.geekbrainsinstagram.ui.screens.photodetails;

import ru.geekbrains.geekbrainsinstagram.base.IBasePresenter;

public interface IPhotoDetailsPresenter extends IBasePresenter<IPhotoDetailsPresenter.IView> {

    interface IView extends IBasePresenter.IView {

        void loadPhoto();

        void showNotifyingMessage(int messageId);
    }

    void start(String photoId);
}
