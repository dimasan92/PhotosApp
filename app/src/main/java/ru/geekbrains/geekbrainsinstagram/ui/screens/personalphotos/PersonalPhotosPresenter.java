package ru.geekbrains.geekbrainsinstagram.ui.screens.personalphotos;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekbrains.domain.interactor.photos.ChangeFavoritePhotoStatusUseCase;
import ru.geekbrains.domain.interactor.photos.DeletePhotoUseCase;
import ru.geekbrains.domain.interactor.photos.GetPersonalPhotosUseCase;
import ru.geekbrains.geekbrainsinstagram.base.BasePresenter;
import ru.geekbrains.geekbrainsinstagram.di.childfragment.ChildFragmentScope;
import ru.geekbrains.geekbrainsinstagram.model.ViewPhotoModel;
import ru.geekbrains.geekbrainsinstagram.model.mapper.ViewPhotoModelMapper;
import ru.geekbrains.geekbrainsinstagram.ui.common.NotifyingMessage;
import ru.geekbrains.geekbrainsinstagram.util.CameraUtils;

@ChildFragmentScope
public final class PersonalPhotosPresenter extends BasePresenter<IPersonalPhotosPresenter.IView>
        implements IPersonalPhotosPresenter {

    private static final int RESULT_OK = -1;

    private final GetPersonalPhotosUseCase getPersonalPhotosUseCase;
    private final ChangeFavoritePhotoStatusUseCase changeFavoritePhotoStatusUseCase;
    private final DeletePhotoUseCase deletePhotoUseCase;
    private final CameraUtils cameraUtils;
    private final ViewPhotoModelMapper photosMapper;

    private ViewPhotoModel newCameraPhoto;

    @Inject
    PersonalPhotosPresenter(GetPersonalPhotosUseCase getPersonalPhotosUseCase,
                            ChangeFavoritePhotoStatusUseCase changeFavoritePhotoStatusUseCase,
                            DeletePhotoUseCase deletePhotoUseCase,
                            CameraUtils cameraUtils, ViewPhotoModelMapper photosMapper) {
        this.getPersonalPhotosUseCase = getPersonalPhotosUseCase;
        this.changeFavoritePhotoStatusUseCase = changeFavoritePhotoStatusUseCase;
        this.deletePhotoUseCase = deletePhotoUseCase;
        this.cameraUtils = cameraUtils;
        this.photosMapper = photosMapper;
    }

    @Override
    public void start() {
        uploadPhotos();
    }

    @Override
    public void takeAPhotoRequest() {
        newCameraPhoto = new ViewPhotoModel();
        view.startCamera(newCameraPhoto);
    }

    @Override
    public void cameraCannotLaunch() {
        view.showNotifyingMessage(NotifyingMessage.ERROR_CAMERA_OPEN);
        newCameraPhoto = null;
    }

    @Override
    public void cameraHasClosed(int resultCode) {
        if (newCameraPhoto != null) {
            if (resultCode == RESULT_OK) {
                view.addNewPhoto(newCameraPhoto);
                view.showNotifyingMessage(NotifyingMessage.PHOTO_SUCCESSFULLY_ADDED);
            }
            cameraUtils.revokeCameraPermissions(newCameraPhoto);
        }
        newCameraPhoto = null;
    }

    @Override
    public void changePhotoFavoriteState(ViewPhotoModel photo) {
        ViewPhotoModel photoWithChangedState =
                new ViewPhotoModel(photo.getId(), !photo.isFavorite());
        addDisposable(changeFavoritePhotoStatusUseCase
                .execute(photosMapper.viewToDomain(photoWithChangedState))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> view.updatePhoto(photoWithChangedState),
                        throwable -> errorChangeFavoriteStatus(photo)));
    }


    @Override
    public void deleteRequest(ViewPhotoModel photo) {
        view.showDeletePhotoDialog(photo);
    }

    @Override
    public void deletePhoto(ViewPhotoModel photo) {
        addDisposable((deletePhotoUseCase
                .execute(photosMapper.viewToDomain(photo))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> successDeletePhoto(photo),
                        throwable -> view.showNotifyingMessage(NotifyingMessage.ERROR_DELETE_PHOTO))));
    }

    private void uploadPhotos() {
        addDisposable(getPersonalPhotosUseCase.execute()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(photos -> view.addPhotos(photosMapper.domainToView(photos)),
                        getDefaultErrorHandler()));
    }

    private void successDeletePhoto(ViewPhotoModel photo) {
        view.deletePhoto(photo);
        view.showNotifyingMessage(NotifyingMessage.PHOTO_SUCCESSFULLY_DELETED);
    }

    private void errorChangeFavoriteStatus(ViewPhotoModel photo) {
        if (photo.isFavorite()) {
            view.showNotifyingMessage(NotifyingMessage.ERROR_DELETE_PHOTO_FROM_FAVORITES);
        } else {
            view.showNotifyingMessage(NotifyingMessage.ERROR_ADD_PHOTO_TO_FAVORITES);
        }
    }
}
