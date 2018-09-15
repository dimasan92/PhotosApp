package ru.geekbrains.geekbrainsinstagram.ui.screens.photodetails;

import ru.geekbrains.geekbrainsinstagram.base.IBasePresenter;
import ru.geekbrains.geekbrainsinstagram.model.ViewPhotoModel;
import ru.geekbrains.geekbrainsinstagram.ui.common.NotifyingMessage;

public interface IPhotoDetailsPresenter extends IBasePresenter<IPhotoDetailsPresenter.IView> {

    interface IView extends IBasePresenter.IView {

        void loadPhoto(ViewPhotoModel photo);

        void showNotifyingMessage(NotifyingMessage message);
    }

    void start(String photoId);
}
