package ru.geekbrains.geekbrainsinstagram.ui.screens.photodetails;

import ru.geekbrains.geekbrainsinstagram.base.IBasePresenter;
import ru.geekbrains.geekbrainsinstagram.model.PresentPhotoModel;
import ru.geekbrains.geekbrainsinstagram.ui.common.NotifyingMessage;

public interface IPhotoDetailsPresenter extends IBasePresenter<IPhotoDetailsPresenter.IView> {

    interface IView extends IBasePresenter.IView {

        void loadPhoto(PresentPhotoModel photo);

        void showNotifyingMessage(NotifyingMessage message);
    }

    void start(String photoId);
}
