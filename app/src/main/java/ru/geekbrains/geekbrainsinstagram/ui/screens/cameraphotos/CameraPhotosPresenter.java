package ru.geekbrains.geekbrainsinstagram.ui.screens.cameraphotos;

import ru.geekbrains.geekbrainsinstagram.base.BasePresenter;

public interface CameraPhotosPresenter extends BasePresenter<CameraPhotosPresenter.View> {

    interface View extends BasePresenter.View {

        void updatePhotos();

        void updatePhoto(int position);

        void deletePhoto(int position);
    }
}
