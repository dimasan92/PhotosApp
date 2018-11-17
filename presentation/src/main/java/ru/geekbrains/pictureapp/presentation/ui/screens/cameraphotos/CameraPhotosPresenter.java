package ru.geekbrains.pictureapp.presentation.ui.screens.cameraphotos;

import ru.geekbrains.pictureapp.domain.model.PhotoModel;
import ru.geekbrains.pictureapp.presentation.ui.base.BasePresenter;
import ru.geekbrains.pictureapp.presentation.ui.base.photos.BaseListPresenter.ListView;

import static ru.geekbrains.pictureapp.presentation.ui.screens.cameraphotos.CameraPhotosPresenter.CameraPhotosView;

public interface CameraPhotosPresenter extends BasePresenter<CameraPhotosView> {

    interface CameraPhotosView extends BasePresenter.BaseView {

        void init(final CameraPhotoListPresenter listPresenter);

        void startCamera(final String filePath);

        void showPhotoDeleteDialog(final PhotoModel photoModel);

        void showCouldNotLaunchCameraMessage();

        void showPhotoSuccessAddedMessage();

        void showPhotoSuccessDeletedMessage();

        void showErrorAddingToFavoritesMessage();

        void showErrorDeletingFromFavoritesMessage();

        void showErrorDeletingPhotoMessage();
    }

    void create();

    void attachListView(final ListView listVew);

    void setCameraResultOkCode(final int resultOkCode);

    void takeAPhotoRequest();

    void cameraHasClosed(final int resultCode);

    void cameraCouldNotLaunch();

    void deletePhotoConfirm(final PhotoModel photoModel);
}
