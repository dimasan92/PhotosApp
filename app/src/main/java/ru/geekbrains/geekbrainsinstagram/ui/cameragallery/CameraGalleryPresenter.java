package ru.geekbrains.geekbrainsinstagram.ui.cameragallery;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import ru.geekbrains.domain.interactor.photos.AddNewInnerStoragePhotoUseCase;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.base.BasePresenter;
import ru.geekbrains.geekbrainsinstagram.ui.mapper.ViewMapper;
import ru.geekbrains.geekbrainsinstagram.ui.model.InnerStoragePhotoViewModel;
import ru.geekbrains.geekbrainsinstagram.utils.FilesUtils;

public final class CameraGalleryPresenter extends BasePresenter<CameraGalleryContract.View>
        implements CameraGalleryContract.Presenter {

    @Inject
    FilesUtils filesUtils;

    @Inject
    AddNewInnerStoragePhotoUseCase addNewInnerStoragePhotoUseCase;

    @Inject
    ViewMapper mapper;

    private InnerStoragePhotoViewModel currentPhoto;

    @Override
    public void viewIsReady() {
//        disposables.add(takeSavedPhotos());
    }

    @Override
    public void takeAPhoto() {
        final Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (!view.isCameraAvailable(cameraIntent) || !filesUtils.isCatalogAvailable()) {
            errorTakePhoto();
            return;
        }
        currentPhoto = new InnerStoragePhotoViewModel();
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
    public void photoHasTaken(boolean took) {
        if (took && currentPhoto != null) {
            addNewPhotoToDb(currentPhoto);
        }
        currentPhoto = null;
    }

    @Override
    public void changePhotoFavorite(InnerStoragePhotoViewModel photoModel) {

    }

    @Override
    public void deletePhoto(InnerStoragePhotoViewModel photoModel) {

    }

    private void addNewPhotoToDb(InnerStoragePhotoViewModel photoModel) {
        disposables.add(addNewInnerStoragePhotoUseCase.execute(mapper.viewToDomain(photoModel))
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(this::successAddPhoto));
    }

    private Disposable takeSavedPhotos() {
        return null;
    }

    private void successAddPhoto() {
        view.showNotifyingMessage(R.string.photo_successfully_added_message);
    }

    private void errorTakePhoto() {
        view.showNotifyingMessage(R.string.error_take_photo_message);
    }
}
