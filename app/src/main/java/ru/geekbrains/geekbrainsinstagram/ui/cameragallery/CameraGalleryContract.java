package ru.geekbrains.geekbrainsinstagram.ui.cameragallery;

import android.content.Intent;
import android.net.Uri;

import java.util.List;

import androidx.annotation.StringRes;
import ru.geekbrains.geekbrainsinstagram.base.BaseContract;
import ru.geekbrains.geekbrainsinstagram.ui.model.InnerStoragePhotoViewModel;

public interface CameraGalleryContract {

    interface View extends BaseContract.View {

        boolean isCameraAvailable(Intent cameraIntent);

        boolean setCameraPermissions(Intent cameraIntent, Uri uri);

        void startCamera(Intent cameraIntent);

        void showPhotos(List<InnerStoragePhotoViewModel> photos);

        void showNotifyingMessage(@StringRes int message);
    }

    interface Presenter extends BaseContract.Presenter<View> {

        void takeAPhoto();

        void photoHasTaken(boolean took);

        void changePhotoFavorite(InnerStoragePhotoViewModel photoModel);

        void deletePhoto(InnerStoragePhotoViewModel photoModel);
    }
}
