package ru.geekbrains.geekbrainsinstagram.ui.screens.personalphotos;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import ru.geekbrains.domain.interactor.photos.ChangeFavoriteStatusPersonalPhotoUseCase;
import ru.geekbrains.domain.interactor.photos.GetPersonalPhotosUseCase;
import ru.geekbrains.domain.interactor.photos.SaveNewPersonalPhotoUseCase;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.base.BasePresenter;
import ru.geekbrains.geekbrainsinstagram.exception.LaunchCameraException;
import ru.geekbrains.geekbrainsinstagram.model.PhotoModel;
import ru.geekbrains.geekbrainsinstagram.model.mapper.IModelMapper;
import ru.geekbrains.geekbrainsinstagram.utils.ICameraUtils;

public final class PersonalPhotosPresenter extends BasePresenter<IPersonalPhotosPresenter.IView>
        implements IPersonalPhotosPresenter {

    @Inject
    SaveNewPersonalPhotoUseCase saveNewPersonalPhotoUseCase;

    @Inject
    GetPersonalPhotosUseCase getPersonalPhotosUseCase;

    @Inject
    ChangeFavoriteStatusPersonalPhotoUseCase changeFavoriteStatusPersonalPhotoUseCase;

    @Inject
    ICameraUtils cameraUtils;

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
    public void changePhotoFavorite(Observable<PhotoModel> favoritesObservable) {
        disposables.add(favoritesObservable.subscribe(photoModel -> {
                    photoModel.setFavorite(!photoModel.isFavorite());
                    disposables.add(changeFavoriteStatusPersonalPhotoUseCase
                            .execute(mapper.viewToDomain(photoModel))
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(() -> view.updatePhoto(photoModel),
                                    throwable -> errorChangeFavoriteStatus(photoModel)));
                }
        ));
    }

    @Override
    public void deletePhoto(Observable<PhotoModel> deleteObservable) {

    }

    private Disposable updatePhotos() {
        return getPersonalPhotosUseCase.execute()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(photos -> view.addPhotos(mapper.domainToView(photos)));
    }

    private void successAddPhoto(PhotoModel photoModel) {
        view.addNewPhoto(photoModel);
        view.showNotifyingMessage(R.string.photo_successfully_added_message);
    }

    private void errorLaunchCamera() {
        view.showNotifyingMessage(R.string.error_take_photo_message);
    }

    private void errorChangeFavoriteStatus(PhotoModel photoModel) {
        if (photoModel.isFavorite()) {
            view.showNotifyingMessage(R.string.error_add_photo_to_favorites_message);
        } else {
            view.showNotifyingMessage(R.string.error_delete_photo_from_favorites_message);
        }
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
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> successAddPhoto(photoModel)));
    }
}
