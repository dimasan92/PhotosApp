package ru.geekbrains.geekbrainsinstagram.ui.screens.personalphotos;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekbrains.domain.interactor.photos.ChangeFavoriteStatusPersonalPhotoUseCase;
import ru.geekbrains.domain.interactor.photos.DeletePersonalPhotoUseCase;
import ru.geekbrains.domain.interactor.photos.GetPersonalPhotosUseCase;
import ru.geekbrains.domain.interactor.photos.SaveNewPersonalPhotoUseCase;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.base.BasePresenter;
import ru.geekbrains.geekbrainsinstagram.di.fragment.FragmentScope;
import ru.geekbrains.geekbrainsinstagram.exception.LaunchCameraException;
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
    public void takeAPhoto() {
        newCameraPhoto = new PresentPhotoModel();

        try {
            view.startCamera(cameraUtils.getAdjustedCameraInvoker(newCameraPhoto));
        } catch (LaunchCameraException e) {
            errorLaunchCamera();
            newCameraPhoto = null;
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
    public void changePhotoFavoriteState(Observable<PresentPhotoModel> favoritesObservable) {
        disposables.add(favoritesObservable.subscribe(this::changePhotoFavoriteState));
    }

    @Override
    public void deletePhoto(PresentPhotoModel photo) {
        disposables.add(deletePersonalPhotoUseCase
                .execute(mapper.viewToDomain(photo))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> successDeletePhoto(photo),
                        throwable -> errorDeletePhoto()));
    }

    @Override
    public void deleteRequest(Observable<PresentPhotoModel> deleteObservable) {
        disposables.add(deleteObservable.subscribe(photoModel ->
                view.showDeletePhotoDialog(photoModel)));
    }

    private void updatePhotos() {
        disposables.add(getPersonalPhotosUseCase.execute()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(photos -> view.addPhotos(mapper.domainToView(photos))));
    }

    private void changePhotoFavoriteState(PresentPhotoModel photo) {
        photo.setFavorite(!photo.isFavorite());
        disposables.add(changeFavoriteStatusPersonalPhotoUseCase
                .execute(mapper.viewToDomain(photo))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> view.updatePhoto(photo),
                        throwable -> errorChangeFavoriteStatus(photo)));
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
            view.showNotifyingMessage(R.string.error_add_photo_to_favorites_message);
        } else {
            view.showNotifyingMessage(R.string.error_delete_photo_from_favorites_message);
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
