package ru.geekbrains.geekbrainsinstagram.ui.screens.personalphotos;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import ru.geekbrains.domain.interactor.photos.GetPersonalPhotosUseCase;
import ru.geekbrains.domain.interactor.photos.SaveNewPersonalPhotoUseCase;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.base.BasePresenter;
import ru.geekbrains.geekbrainsinstagram.exception.LaunchCameraException;
import ru.geekbrains.geekbrainsinstagram.model.mapper.IModelMapper;
import ru.geekbrains.geekbrainsinstagram.model.PhotoModel;
import ru.geekbrains.geekbrainsinstagram.utils.ICameraUtils;

public final class PersonalPhotosPresenter extends BasePresenter<IPersonalPhotosPresenter.IView>
        implements IPersonalPhotosPresenter {

    @Inject
    ICameraUtils cameraUtils;

    @Inject
    SaveNewPersonalPhotoUseCase saveNewPersonalPhotoUseCase;

    @Inject
    GetPersonalPhotosUseCase getPersonalPhotosUseCase;

    @Inject
    IModelMapper mapper;

    private PhotoModel currentPhoto;

    @Override
    public void viewIsReady() {
        disposables.add(updatePhotos());
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
    public void photoHasTaken() {
        cameraHasClosed(true);
    }

    @Override
    public void photoHasCanceled() {
        cameraHasClosed(false);
    }

    @Override
    public void changePhotoFavorite(PhotoModel photoModel) {

    }

    @Override
    public void deletePhoto(PhotoModel photoModel) {

    }

    private Disposable updatePhotos() {
        return getPersonalPhotosUseCase.execute()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(photos -> view.updatePhotos(mapper.domainToView(photos)));
    }

    private void successAddPhoto() {
        view.showNotifyingMessage(R.string.photo_successfully_added_message);
    }

    private void errorLaunchCamera() {
        view.showNotifyingMessage(R.string.error_take_photo_message);
    }

    private void cameraHasClosed(boolean isPhotoTaken) {
        if (currentPhoto != null) {
            if (isPhotoTaken) {
                saveNewPhotoToDevice(currentPhoto);
            }
            cameraUtils.revokeCameraPermissions(currentPhoto);
        }
        currentPhoto = null;
    }

    private void saveNewPhotoToDevice(PhotoModel photoModel) {
        disposables.add(saveNewPersonalPhotoUseCase.execute(mapper.viewToDomain(photoModel))
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(this::successAddPhoto));
    }
}
