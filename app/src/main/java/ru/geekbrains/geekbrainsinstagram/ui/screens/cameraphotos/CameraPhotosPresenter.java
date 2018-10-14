package ru.geekbrains.geekbrainsinstagram.ui.screens.cameraphotos;

import ru.geekbrains.geekbrainsinstagram.base.BasePresenter;
import ru.geekbrains.geekbrainsinstagram.model.ViewPhotoModel;
import ru.geekbrains.geekbrainsinstagram.ui.screens.cameraphotos.CameraPhotoListPresenter.CameraPhotosListView;

import static ru.geekbrains.geekbrainsinstagram.ui.screens.cameraphotos.CameraPhotosPresenter.CameraPhotosView;

public interface CameraPhotosPresenter extends BasePresenter<CameraPhotosView> {

    interface CameraPhotosView extends BasePresenter.BaseView {

        void init(final CameraPhotoListPresenter presenter);

        void startCamera(final ViewPhotoModel photoModel);

        void showDeletePhotoDialog(final ViewPhotoModel photoModel);

        void showCannotLaunchCameraMessage();

        void showPhotoSuccessAddMessage();

        void showPhotoSuccessDeleteMessage();

        void showErrorAddToFavoritesMessage();

        void showErrorDeleteFromFavoritesMessage();

        void showErrorPhotoDeletePhotoMessage();
    }

    void create();

    void setCameraResultOkCode(final int resultOkCode);

    void takeAPhotoRequest();

    void cameraHasClosed(final int resultCode);

    void cameraCannotLaunch();

    void attachListView(final CameraPhotosListView view);

    void deletePhotoConfirm(final ViewPhotoModel photoModel);
}
