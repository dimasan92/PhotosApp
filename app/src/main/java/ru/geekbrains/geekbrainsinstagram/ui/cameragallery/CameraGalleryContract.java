package ru.geekbrains.geekbrainsinstagram.ui.cameragallery;

import android.content.Intent;
import android.net.Uri;

import androidx.annotation.StringRes;
import ru.geekbrains.geekbrainsinstagram.base.BaseContract;

public interface CameraGalleryContract {

    interface View extends BaseContract.View {

        boolean isCameraAvailable(Intent cameraIntent);

        boolean setCameraPermissions(Intent cameraIntent, Uri uri);

        void startCamera(Intent cameraIntent);

        void showNotifyingMessage(@StringRes int message);
    }

    interface Presenter extends BaseContract.Presenter<View> {

        void takeAPhoto();

        void photoTook(boolean took);
    }
}
