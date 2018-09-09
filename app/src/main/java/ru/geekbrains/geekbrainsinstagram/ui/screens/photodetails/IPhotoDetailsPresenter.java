package ru.geekbrains.geekbrainsinstagram.ui.screens.photodetails;

import ru.geekbrains.geekbrainsinstagram.base.IBasePresenter;
import ru.geekbrains.geekbrainsinstagram.model.PresentPhotoModel;

public interface IPhotoDetailsPresenter extends IBasePresenter<IPhotoDetailsPresenter.IView> {

    interface IView extends IBasePresenter.IView {

        void loadPhoto(PresentPhotoModel photo);

        void showNotifyingMessage(int messageId);
    }

    void start(String photoId);
}
