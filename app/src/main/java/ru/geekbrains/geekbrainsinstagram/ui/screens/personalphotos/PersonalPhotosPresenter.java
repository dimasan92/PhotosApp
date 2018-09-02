package ru.geekbrains.geekbrainsinstagram.ui.screens.personalphotos;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekbrains.domain.interactor.photos.ChangeFavoriteStatusPersonalPhotoUseCase;
import ru.geekbrains.domain.interactor.photos.DeletePersonalPhotoUseCase;
import ru.geekbrains.domain.interactor.photos.GetPersonalPhotosUseCase;
import ru.geekbrains.domain.interactor.photos.SaveNewPersonalPhotoUseCase;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.base.BasePresenter;
import ru.geekbrains.geekbrainsinstagram.di.fragment.FragmentScope;
import ru.geekbrains.geekbrainsinstagram.model.PresentPhotoModel;
import ru.geekbrains.geekbrainsinstagram.model.mapper.IPresentModelMapper;
import ru.geekbrains.geekbrainsinstagram.util.ICameraUtils;

@FragmentScope
public final class PersonalPhotosPresenter extends BasePresenter<IPersonalPhotosPresenter.IView>
        implements IPersonalPhotosPresenter {

    private final SaveNewPersonalPhotoUseCase saveNewPersonalPhotoUseCase;
    private final GetPersonalPhotosUseCase getPersonalPhotosUseCase;
    private final ChangeFavoriteStatusPersonalPhotoUseCase changeFavoriteStatusPersonalPhotoUseCase;
    private final DeletePersonalPhotoUseCase deletePersonalPhotoUseCase;
    private final ICameraUtils cameraUtils;
    private final IPresentModelMapper mapper;

    private PresentPhotoModel newCameraPhoto;

    @Inject
    PersonalPhotosPresenter(SaveNewPersonalPhotoUseCase saveNewPersonalPhotoUseCase,
                            GetPersonalPhotosUseCase getPersonalPhotosUseCase,
                            ChangeFavoriteStatusPersonalPhotoUseCase changeFavoriteStatusPersonalPhotoUseCase,
                            DeletePersonalPhotoUseCase deletePersonalPhotoUseCase,
                            ICameraUtils cameraUtils, IPresentModelMapper mapper) {
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
    public void takeAPhotoRequest() {
        newCameraPhoto = new PresentPhotoModel(false);
        view.startCamera(newCameraPhoto);
    }

    @Override
    public void cameraCannotLaunch() {
        errorLaunchCamera();
        newCameraPhoto = null;
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
    public void changePhotoFavoriteState(PresentPhotoModel photo) {
        PresentPhotoModel photoWithChangedState =
                new PresentPhotoModel(photo.getId(), !photo.isFavorite());
        disposables.add(changeFavoriteStatusPersonalPhotoUseCase
                .execute(mapper.viewToDomain(photoWithChangedState))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> view.updatePhoto(photoWithChangedState),
                        throwable -> errorChangeFavoriteStatus(photo)));
    }


    @Override
    public void deleteRequest(PresentPhotoModel photo) {
        view.showDeletePhotoDialog(photo);
    }

    @Override
    public void deletePhoto(PresentPhotoModel photo) {
        disposables.add(deletePersonalPhotoUseCase
                .execute(mapper.viewToDomain(photo))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> successDeletePhoto(photo),
                        throwable -> errorDeletePhoto()));
    }

    private void updatePhotos() {
        disposables.add(getPersonalPhotosUseCase.execute()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(photos -> view.addPhotos(mapper.domainToView(photos))));
    }

    private void successAddPhoto(PresentPhotoModel photo) {
        view.addNewPhoto(photo);
        view.showNotifyingMessage(R.string.photo_successfully_added_message);
    }

    private void successDeletePhoto(PresentPhotoModel photo) {
        view.deletePhoto(photo);
        view.showNotifyingMessage(R.string.photo_successfully_deleted_message);
    }

    private void errorLaunchCamera() {
        view.showNotifyingMessage(R.string.error_take_photo_message);
    }

    private void errorChangeFavoriteStatus(PresentPhotoModel photo) {
        if (photo.isFavorite()) {
            view.showNotifyingMessage(R.string.error_delete_photo_from_favorites_message);
        } else {
            view.showNotifyingMessage(R.string.error_add_photo_to_favorites_message);
        }
    }

    private void errorDeletePhoto() {
        view.showNotifyingMessage(R.string.error_delete_photo_message);
    }

    private void cameraHasClosed(boolean isPhotoTaken) {
        if (newCameraPhoto != null) {
            if (isPhotoTaken) {
                saveNewPhotoToDevice(newCameraPhoto);
            }
            cameraUtils.revokeCameraPermissions(newCameraPhoto);
        }
        newCameraPhoto = null;
    }

    private void saveNewPhotoToDevice(PresentPhotoModel photo) {
        disposables.add(saveNewPersonalPhotoUseCase.execute(mapper.viewToDomain(photo))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> successAddPhoto(photo)));
    }
}
