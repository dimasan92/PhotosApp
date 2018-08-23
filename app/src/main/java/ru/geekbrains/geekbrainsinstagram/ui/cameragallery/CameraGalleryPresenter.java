package ru.geekbrains.geekbrainsinstagram.ui.cameragallery;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import javax.inject.Inject;

import io.reactivex.Single;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.base.BasePresenter;
import ru.geekbrains.geekbrainsinstagram.ui.model.InnerStoragePhotoModel;
import ru.geekbrains.geekbrainsinstagram.utils.FilesUtils;

public class CameraGalleryPresenter extends BasePresenter<CameraGalleryContract.View>
        implements CameraGalleryContract.Presenter {

    @Inject
    FilesUtils filesUtils;

    private Single<Boolean> 

    @Override
    public void viewIsReady() {
    }

    @Override
    public void takeAPhoto() {
        final Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (!view.isCameraAvailable(cameraIntent) || !filesUtils.isCatalogAvailable()) {
            errorTakePhoto();
            return;
        }
        InnerStoragePhotoModel model = new InnerStoragePhotoModel();
        Uri uri = filesUtils.getUriForFile(filesUtils.getInnerPhotoFile(model));
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        if (view.setCameraPermissions(cameraIntent, uri)) {
            view.startCamera(cameraIntent);
        } else {
            errorTakePhoto();
        }
    }

    @Override
    public void photoTook(boolean took) {

    }

    private void errorTakePhoto() {
        view.showNotifyingMessage(R.string.error_take_photo_message);
    }
}
