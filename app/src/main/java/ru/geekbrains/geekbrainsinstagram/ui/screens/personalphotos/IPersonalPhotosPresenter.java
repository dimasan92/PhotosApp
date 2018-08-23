package ru.geekbrains.geekbrainsinstagram.ui.screens.personalphotos;

import android.content.Intent;
import android.net.Uri;

import java.util.List;

import androidx.annotation.StringRes;
import ru.geekbrains.geekbrainsinstagram.base.IBasePresenter;
import ru.geekbrains.geekbrainsinstagram.ui.model.InnerStoragePhotoViewModel;

public interface IPersonalPhotosPresenter extends IBasePresenter<IPersonalPhotosPresenter.IView> {

    interface IView extends IBasePresenter.IView {

        boolean isCameraAvailable(Intent cameraIntent);

        boolean setCameraPermissions(Intent cameraIntent, Uri uri);

        void startCamera(Intent cameraIntent);

        void showPhotos(List<InnerStoragePhotoViewModel> photos);

        void showNotifyingMessage(@StringRes int message);
    }

    void takeAPhoto();

    void photoHasTaken(boolean took);

    void changePhotoFavorite(InnerStoragePhotoViewModel photoModel);

    void deletePhoto(InnerStoragePhotoViewModel photoModel);
}
