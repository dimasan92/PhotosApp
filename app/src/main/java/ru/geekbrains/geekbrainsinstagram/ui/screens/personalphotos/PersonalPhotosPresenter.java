package ru.geekbrains.geekbrainsinstagram.ui.screens.personalphotos;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekbrains.domain.interactor.photos.ChangeFavoriteStatusPersonalPhotoUseCase;
import ru.geekbrains.domain.interactor.photos.DeletePersonalPhotoUseCase;
import ru.geekbrains.domain.interactor.photos.GetPersonalPhotosUseCase;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.base.BasePresenter;
import ru.geekbrains.geekbrainsinstagram.di.fragment.FragmentScope;
import ru.geekbrains.geekbrainsinstagram.model.PresentPhotoModel;
import ru.geekbrains.geekbrainsinstagram.model.mapper.IPresentModelPhotosMapper;
import ru.geekbrains.geekbrainsinstagram.util.ICameraUtils;

@FragmentScope
public final class PersonalPhotosPresenter extends BasePresenter<IPersonalPhotosPresenter.IView>
        implements IPersonalPhotosPresenter {

    private final GetPersonalPhotosUseCase getPersonalPhotosUseCase;
    private final ChangeFavoriteStatusPersonalPhotoUseCase changeFavoriteStatusPersonalPhotoUseCase;
    private final DeletePersonalPhotoUseCase deletePersonalPhotoUseCase;
    private final ICameraUtils cameraUtils;
    private final IPresentModelPhotosMapper mapper;

    private PresentPhotoModel newCameraPhoto;

    @Inject
    PersonalPhotosPresenter(GetPersonalPhotosUseCase getPersonalPhotosUseCase,
                            ChangeFavoriteStatusPersonalPhotoUseCase changeFavoriteStatusPersonalPhotoUseCase,
                            DeletePersonalPhotoUseCase deletePersonalPhotoUseCase,
                            ICameraUtils cameraUtils, IPresentModelPhotosMapper mapper) {
        this.getPersonalPhotosUseCase = getPersonalPhotosUseCase;
        this.changeFavoriteStatusPersonalPhotoUseCase = changeFavoriteStatusPersonalPhotoUseCase;
        this.deletePersonalPhotoUseCase = deletePersonalPhotoUseCase;
        this.cameraUtils = cameraUtils;
        this.mapper = mapper;
    }

    @Override
    public void viewIsReady() {
        uploadPhotos();
    }

    @Override
    public void takeAPhotoRequest() {
        newCameraPhoto = new PresentPhotoModel();
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
        addDisposable(changeFavoriteStatusPersonalPhotoUseCase
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
        addDisposable((deletePersonalPhotoUseCase
                .execute(mapper.viewToDomain(photo))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> successDeletePhoto(photo),
                        throwable -> errorDeletePhoto())));
    }

    private void uploadPhotos() {
        addDisposable(getPersonalPhotosUseCase.execute()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(photos -> view.addPhotos(mapper.domainToView(photos)),
                        getDefaultErrorHandler()));
    }

    private void cameraHasClosed(boolean isPhotoTaken) {
        if (newCameraPhoto != null) {
            if (isPhotoTaken) {
                view.addNewPhoto(newCameraPhoto);
                view.showNotifyingMessage(R.string.photo_successfully_added_message);
            }
            cameraUtils.revokeCameraPermissions(newCameraPhoto);
        }
        newCameraPhoto = null;
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
}
