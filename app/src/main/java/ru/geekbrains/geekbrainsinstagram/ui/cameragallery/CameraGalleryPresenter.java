package ru.geekbrains.geekbrainsinstagram.ui.cameragallery;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import javax.inject.Inject;

import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.base.BasePresenter;
import ru.geekbrains.geekbrainsinstagram.ui.model.InnerStoragePhotoModel;
import ru.geekbrains.geekbrainsinstagram.utils.FilesUtils;

public class CameraGalleryPresenter extends BasePresenter<CameraGalleryContract.View>
        implements CameraGalleryContract.Presenter {

    @Inject
    FilesUtils filesUtils;

    private InnerStoragePhotoModel currentPhoto;

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
        currentPhoto = new InnerStoragePhotoModel();
        Uri uri = filesUtils.getUriForFile(filesUtils.getInnerPhotoFile(currentPhoto));
        currentPhoto.setUri(uri);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        if (view.setCameraPermissions(cameraIntent, uri)) {
            view.startCamera(cameraIntent);
        } else {
            errorTakePhoto();
            currentPhoto = null;
        }
    }

    @Override
    public void photoTook(boolean took) {
        if (took && currentPhoto != null) {
            // save to DB
        }
        currentPhoto = null;
    }

    private void errorTakePhoto() {
        view.showNotifyingMessage(R.string.error_take_photo_message);
    }
}
