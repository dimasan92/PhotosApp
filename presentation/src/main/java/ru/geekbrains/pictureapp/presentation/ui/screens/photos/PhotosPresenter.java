package ru.geekbrains.pictureapp.presentation.ui.screens.photos;

import ru.geekbrains.pictureapp.domain.model.ImageModel;
import ru.geekbrains.pictureapp.presentation.ui.base.BasePresenter;
import ru.geekbrains.pictureapp.presentation.ui.base.photos.BaseListPresenter.ListView;

import static ru.geekbrains.pictureapp.presentation.ui.screens.photos.PhotosPresenter.PhotosView;

public interface PhotosPresenter extends BasePresenter<PhotosView> {

    interface PhotosView extends BasePresenter.BaseView {

        void init(final PhotosListPresenter listPresenter);

        void startCamera(final String filePath);

        void showPhotoDeleteDialog(final ImageModel imageModel);

        void showCouldNotLaunchCameraMessage();

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

    void deletePhotoConfirm(final ImageModel imageModel);
}
