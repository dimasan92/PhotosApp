package ru.geekbrains.geekbrainsinstagram.ui.screens.personalphotos;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekbrains.domain.interactor.photos.ChangeFavoriteStatusPersonalPhotoUseCase;
import ru.geekbrains.domain.interactor.photos.DeletePersonalPhotoUseCase;
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

    private final SaveNewPersonalPhotoUseCase saveNewPersonalPhotoUseCase;
    private final GetPersonalPhotosUseCase getPersonalPhotosUseCase;
    private final ChangeFavoriteStatusPersonalPhotoUseCase changeFavoriteStatusPersonalPhotoUseCase;
    private final DeletePersonalPhotoUseCase deletePersonalPhotoUseCase;
    private final ICameraUtils cameraUtils;
    private final IModelMapper mapper;

    private PhotoModel currentPhoto;

    public PersonalPhotosPresenter(SaveNewPersonalPhotoUseCase saveNewPersonalPhotoUseCase,
                                   GetPersonalPhotosUseCase getPersonalPhotosUseCase,
                                   ChangeFavoriteStatusPersonalPhotoUseCase changeFavoriteStatusPersonalPhotoUseCase,
                                   DeletePersonalPhotoUseCase deletePersonalPhotoUseCase,
                                   ICameraUtils cameraUtils, IModelMapper mapper) {
        this.saveNewPersonalPhotoUseCase = saveNewPersonalPhotoUseCase;
        this.getPersonalPhotosUseCase = getPersonalPhotosUseCase;
        this.changeFavoriteStatusPersonalPhotoUseCase = changeFavoriteStatusPersonalPhotoUseCase;
        this.deletePersonalPhotoUseCase = deletePersonalPhotoUseCase;
        this.cameraUtils = cameraUtils;
        this.mapper = mapper;
    }

    @Override
    public void viewIsReady() {
        updatePhotos();
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
    public void changePhotoFavoriteState(Observable<PhotoModel> favoritesObservable) {
        disposables.add(favoritesObservable.subscribe(this::changePhotoFavoriteState));
    }

    @Override
    public void deletePhoto(PhotoModel photoModel) {
        disposables.add(deletePersonalPhotoUseCase
                .execute(mapper.viewToDomain(photoModel))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> successDeletePhoto(photoModel),
                        throwable -> errorDeletePhoto()));
    }

    @Override
    public void deleteRequest(Observable<PhotoModel> deleteObservable) {
        disposables.add(deleteObservable.subscribe(photoModel ->
                view.showDeletePhotoDialog(photoModel)));
    }

    private void updatePhotos() {
        disposables.add(getPersonalPhotosUseCase.execute()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(photos -> view.addPhotos(mapper.domainToView(photos))));
    }

    private void changePhotoFavoriteState(PhotoModel photoModel) {
        photoModel.setFavorite(!photoModel.isFavorite());
        disposables.add(changeFavoriteStatusPersonalPhotoUseCase
                .execute(mapper.viewToDomain(photoModel))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> view.updatePhoto(photoModel),
                        throwable -> errorChangeFavoriteStatus(photoModel)));
    }

    private void successAddPhoto(PhotoModel photoModel) {
        view.addNewPhoto(photoModel);
        view.showNotifyingMessage(R.string.photo_successfully_added_message);
    }

    private void successDeletePhoto(PhotoModel photoModel) {
        view.deletePhoto(photoModel);
        view.showNotifyingMessage(R.string.photo_successfully_deleted_message);
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

    private void errorDeletePhoto() {
        view.showNotifyingMessage(R.string.error_delete_photo_message);
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
