package ru.geekbrains.geekbrainsinstagram.ui.screens.cameraphotos;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import ru.geekbrains.domain.interactor.photos.ChangeFavoritePhotoStatusUseCase;
import ru.geekbrains.domain.interactor.photos.DeletePhotoUseCase;
import ru.geekbrains.domain.interactor.photos.GetCameraPhotosUseCase;
import ru.geekbrains.geekbrainsinstagram.base.BasePresenterImpl;
import ru.geekbrains.geekbrainsinstagram.di.fragment.cameraphotos.CameraPhotosFragmentScope;
import ru.geekbrains.geekbrainsinstagram.model.ViewPhotoModel;
import ru.geekbrains.geekbrainsinstagram.model.mapper.ViewPhotoModelMapper;
import ru.geekbrains.geekbrainsinstagram.ui.screens.cameraphotos.CameraPhotoListPresenter.CameraPhotosListView;
import ru.geekbrains.geekbrainsinstagram.util.CameraUtils;

@CameraPhotosFragmentScope
public final class CameraPhotosPresenterImpl extends BasePresenterImpl<CameraPhotosPresenter.CameraPhotosView>
        implements CameraPhotosPresenter {

    private final GetCameraPhotosUseCase getCameraPhotosUseCase;
    private final ChangeFavoritePhotoStatusUseCase changeFavoritePhotoStatusUseCase;
    private final DeletePhotoUseCase deletePhotoUseCase;

    private final Scheduler uiScheduler;
    private final CameraUtils cameraUtils;
    private final ViewPhotoModelMapper photosMapper;
    private final CameraPhotosListPresenterImpl listPresenter;

    private int resultOk;
    private ViewPhotoModel newCameraPhoto;

    @Inject
    CameraPhotosPresenterImpl(GetCameraPhotosUseCase getCameraPhotosUseCase,
                              ChangeFavoritePhotoStatusUseCase changeFavoritePhotoStatusUseCase,
                              DeletePhotoUseCase deletePhotoUseCase, Scheduler uiScheduler,
                              CameraUtils cameraUtils, ViewPhotoModelMapper photosMapper) {
        this.getCameraPhotosUseCase = getCameraPhotosUseCase;
        this.changeFavoritePhotoStatusUseCase = changeFavoritePhotoStatusUseCase;
        this.deletePhotoUseCase = deletePhotoUseCase;
        this.uiScheduler = uiScheduler;
        this.cameraUtils = cameraUtils;
        this.photosMapper = photosMapper;
        listPresenter = new CameraPhotosListPresenterImpl();
    }

    @Override
    public void create() {
        view.init(listPresenter);
    }

    @Override
    public void start() {
        uploadPhotos();
    }

    @Override
    public void stop() {
        super.stop();
        listPresenter.detachView();
    }

    @Override
    public void attachListView(final CameraPhotosListView view) {
        listPresenter.attachView(view);
    }


    @Override
    public void setCameraResultOkCode(final int resultOkCode) {
        resultOk = resultOkCode;
    }

    @Override
    public void takeAPhotoRequest() {
        newCameraPhoto = new ViewPhotoModel();
        view.startCamera(newCameraPhoto);
    }

    @Override
    public void cameraHasClosed(final int resultCode) {
        if (newCameraPhoto != null) {
            if (resultCode == resultOk) {
                listPresenter.addPhoto(newCameraPhoto);
                view.showPhotoSuccessAddMessage();
            }
            cameraUtils.revokeCameraPermissions(newCameraPhoto);
        }
        newCameraPhoto = null;
    }

    @Override
    public void cameraCannotLaunch() {
        view.showCannotLaunchCameraMessage();
        newCameraPhoto = null;
    }

    @Override
    public void deletePhotoConfirm(final ViewPhotoModel photo) {
        addDisposable(deletePhotoUseCase
                .execute(photosMapper.viewToDomain(photo))
                .observeOn(uiScheduler)
                .subscribe(() -> {
                            listPresenter.deletePhoto(photo);
                            view.showPhotoSuccessDeleteMessage();
                        },
                        throwable -> view.showErrorPhotoDeletePhotoMessage()));
    }

    private void uploadPhotos() {
        addDisposable(getCameraPhotosUseCase.execute()
                .observeOn(uiScheduler)
                .subscribe(photos -> listPresenter.setPhotos(photosMapper.domainToView(photos)),
                        getDefaultErrorHandler()));
    }

    private void changePhotoFavoriteState(final ViewPhotoModel photo) {
        final ViewPhotoModel photoWithChangedState =
                new ViewPhotoModel(photo.getId(), !photo.isFavorite());
        addDisposable(changeFavoritePhotoStatusUseCase
                .execute(photosMapper.viewToDomain(photoWithChangedState))
                .observeOn(uiScheduler)
                .subscribe(() -> listPresenter.updatePhoto(photoWithChangedState),
                        throwable -> errorChangeFavoriteStatus(photo)));
    }

    private void errorChangeFavoriteStatus(final ViewPhotoModel photoModel) {
        if (photoModel.isFavorite()) {
            view.showErrorDeleteFromFavoritesMessage();
        } else {
            view.showErrorAddToFavoritesMessage();
        }
    }

    private void deleteRequest(final ViewPhotoModel photoModel) {
        view.showDeletePhotoDialog(photoModel);
    }

    class CameraPhotosListPresenterImpl implements CameraPhotoListPresenter {

        private List<ViewPhotoModel> photos = new ArrayList<>();
        private CameraPhotosListView listView;

        void setPhotos(List<ViewPhotoModel> photos) {
            this.photos = photos;
            listView.updatePhotos();
        }

        void attachView(CameraPhotosListView listView) {
            this.listView = listView;
        }

        void detachView() {
            listView = null;
        }

        @Override
        public void bind(final int position, final CameraPhotoView view) {
            final ViewPhotoModel photoModel = photos.get(position);
            view.loadImage(photoModel);
            view.setFavorite(photoModel.isFavorite());
        }

        @Override
        public int getCount() {
            return photos.size();
        }

        @Override
        public void onFavoriteClick(final int position) {
            changePhotoFavoriteState(photos.get(position));
        }

        @Override
        public void onDeleteClick(final int position) {
            deleteRequest(photos.get(position));
        }

        void addPhoto(ViewPhotoModel photoModel) {
            photos.add(photoModel);
            listView.updatePhoto(photos.indexOf(photoModel));
        }

        void updatePhoto(final ViewPhotoModel photoModel) {
            int position = searchItemPosition(photoModel);
            if (position == -1) {
                return;
            }
            photos.set(position, photoModel);
            listView.updatePhoto(position);
        }

        void deletePhoto(final ViewPhotoModel photoModel) {
            listView.deletePhoto(photos.indexOf(photoModel));
            photos.remove(photoModel);
        }

        private int searchItemPosition(final ViewPhotoModel photo) {
            int position = -1;
            for (int i = 0; i < photos.size(); i++) {
                if (photos.get(i).getId().equals(photo.getId())) {
                    position = i;
                    break;
                }
            }
            return position;
        }
    }
}
