package ru.geekbrains.geekbrainsinstagram.ui.screens.personalphotos;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import ru.geekbrains.domain.interactor.photos.AddNewInnerStoragePhotoUseCase;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.base.BasePresenter;
import ru.geekbrains.geekbrainsinstagram.exception.LaunchCameraException;
import ru.geekbrains.geekbrainsinstagram.ui.mapper.ViewMapper;
import ru.geekbrains.geekbrainsinstagram.ui.model.PhotoModel;
import ru.geekbrains.geekbrainsinstagram.utils.IFilesUtils;
import ru.geekbrains.geekbrainsinstagram.utils.ICameraUtils;

public final class PersonalPhotosPresenter extends BasePresenter<IPersonalPhotosPresenter.IView>
        implements IPersonalPhotosPresenter {

    @Inject
    ICameraUtils cameraUtils;

    @Inject
    AddNewInnerStoragePhotoUseCase addNewInnerStoragePhotoUseCase;

    @Inject
    ViewMapper mapper;

    private PhotoModel currentPhoto;

    @Override
    public void viewIsReady() {
//        disposables.add(takeSavedPhotos());
    }

    @Override
    public void takeAPhoto() {
        currentPhoto = new PhotoModel();

        try {
            view.startCamera(cameraUtils.getAdjustedCameraInvoker(currentPhoto));
        } catch (LaunchCameraException e) {
            errorLaunchCamera();
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
    public void changePhotoFavorite(PhotoModel photoModel) {

    }

    @Override
    public void deletePhoto(PhotoModel photoModel) {

    }

    private void addNewPhotoToDb(PhotoModel photoModel) {
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

    private void errorLaunchCamera() {
        view.showNotifyingMessage(R.string.error_take_photo_message);
    }
}
