package ru.geekbrains.geekbrainsinstagram.ui.screens.cameraphotos;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import ru.geekbrains.domain.interactor.photos.CameraPhotoUpdaterUseCase;
import ru.geekbrains.domain.interactor.photos.ChangePhotoFavoriteStatusUseCase;
import ru.geekbrains.domain.interactor.photos.DeletePhotoUseCase;
import ru.geekbrains.domain.interactor.photos.GetCameraPhotosUseCase;
import ru.geekbrains.domain.interactor.photos.GetPlaceForNewCameraPhotoUseCase;
import ru.geekbrains.domain.model.PhotoModel;
import ru.geekbrains.geekbrainsinstagram.di.ui.home.HomeScope;
import ru.geekbrains.geekbrainsinstagram.ui.base.BasePresenterImpl;
import ru.geekbrains.geekbrainsinstagram.ui.base.photos.BaseListPresenter.ListView;
import ru.geekbrains.geekbrainsinstagram.ui.base.photos.BaseListPresenterImpl;
import ru.geekbrains.geekbrainsinstagram.ui.screens.cameraphotos.CameraPhotoListPresenter.CameraPhotoRowView;
import ru.geekbrains.geekbrainsinstagram.ui.screens.cameraphotos.CameraPhotosPresenter.CameraPhotosView;
import ru.geekbrains.geekbrainsinstagram.util.CameraUtils;

@HomeScope
public final class CameraPhotosPresenterImpl extends BasePresenterImpl<CameraPhotosView>
        implements CameraPhotosPresenter {

    private final GetCameraPhotosUseCase getCameraPhotosUseCase;
    private final GetPlaceForNewCameraPhotoUseCase getPlaceForNewCameraPhotoUseCase;
    private final DeletePhotoUseCase deletePhotoUseCase;
    private final CameraPhotoUpdaterUseCase cameraPhotoUpdaterUseCase;

    private final Scheduler uiScheduler;
    private final CameraUtils cameraUtils;
    private final CameraPhotosListPresenterImpl listPresenter;
    private int resultOk;

    private boolean wasPhotosLoad;
    private PhotoModel newCameraPhotoModel;

    @Inject CameraPhotosPresenterImpl(final GetCameraPhotosUseCase getCameraPhotosUseCase,
                                      final GetPlaceForNewCameraPhotoUseCase getPlaceForNewCameraPhotoUseCase,
                                      final DeletePhotoUseCase deletePhotoUseCase,
                                      final CameraPhotoUpdaterUseCase cameraPhotoUpdaterUseCase,
                                      final CameraPhotosListPresenterImpl listPresenter,
                                      final Scheduler uiScheduler, final CameraUtils cameraUtils) {
        this.getCameraPhotosUseCase = getCameraPhotosUseCase;
        this.getPlaceForNewCameraPhotoUseCase = getPlaceForNewCameraPhotoUseCase;
        this.deletePhotoUseCase = deletePhotoUseCase;
        this.cameraPhotoUpdaterUseCase = cameraPhotoUpdaterUseCase;
        this.uiScheduler = uiScheduler;
        this.cameraUtils = cameraUtils;
        this.listPresenter = listPresenter;
    }

    @Override public void create() {
        view.init(listPresenter);
        cameraPhotoUpdaterUseCase.subscribe(b -> wasPhotosLoad = false);
    }

    @Override public void attachListView(final ListView listView) {
        listPresenter.attachView(view, listView);
    }

    @Override public void start() {
        if (!wasPhotosLoad) {
            uploadPhotos();
        }
    }

    @Override public void stop() {
        super.stop();
        listPresenter.detachView();
    }

    @Override public void setCameraResultOkCode(final int resultOkCode) {
        resultOk = resultOkCode;
    }

    @Override public void takeAPhotoRequest() {
        addDisposable(getPlaceForNewCameraPhotoUseCase.execute()
                .subscribeOn(uiScheduler)
                .subscribe(this::invokeCamera,
                        throwable -> cameraCouldNotLaunch()));
    }

    @Override public void cameraHasClosed(final int resultCode) {
        if (newCameraPhotoModel != null) {
            if (resultCode == resultOk) {
                listPresenter.addPhotoModel(newCameraPhotoModel);
                view.showPhotoSuccessAddedMessage();
            }
            cameraUtils.revokeCameraPermissions(newCameraPhotoModel.getFilePath());
        }
        newCameraPhotoModel = null;
    }

    @Override public void cameraCouldNotLaunch() {
        view.showCouldNotLaunchCameraMessage();
        newCameraPhotoModel = null;
    }

    @Override public void deletePhotoConfirm(final PhotoModel photoModel) {
        addDisposable(deletePhotoUseCase.execute(photoModel)
                .observeOn(uiScheduler)
                .subscribe(() -> {
                            listPresenter.deletePhotoModel(photoModel);
                            cameraPhotoUpdaterUseCase.execute();
                            view.showPhotoSuccessDeletedMessage();
                        },
                        throwable -> view.showErrorDeletingPhotoMessage()));
    }

    private void uploadPhotos() {
        addDisposable(getCameraPhotosUseCase.execute()
                .observeOn(uiScheduler)
                .subscribe(photoModels -> {
                            listPresenter.setPhotoModels(photoModels);
                            wasPhotosLoad = true;
                        },
                        getDefaultErrorHandler()));
    }

    private void invokeCamera(final PhotoModel newCameraPhotoModel) {
        this.newCameraPhotoModel = newCameraPhotoModel;
        view.startCamera(newCameraPhotoModel.getFilePath());
    }

    @HomeScope
    static class CameraPhotosListPresenterImpl extends BaseListPresenterImpl<CameraPhotosView, CameraPhotoRowView>
            implements CameraPhotoListPresenter {

        private final ChangePhotoFavoriteStatusUseCase changePhotoFavoriteStatusUseCase;
        private final CameraPhotoUpdaterUseCase cameraPhotoUpdaterUseCase;
        private final Scheduler uiScheduler;

        @Inject CameraPhotosListPresenterImpl(final ChangePhotoFavoriteStatusUseCase changePhotoFavoriteStatusUseCase,
                                              final CameraPhotoUpdaterUseCase cameraPhotoUpdaterUseCase,
                                              final Scheduler uiScheduler) {
            this.changePhotoFavoriteStatusUseCase = changePhotoFavoriteStatusUseCase;
            this.cameraPhotoUpdaterUseCase = cameraPhotoUpdaterUseCase;
            this.uiScheduler = uiScheduler;
        }

        @Override public void bind(final int position, final CameraPhotoRowView view) {
            final PhotoModel photoModel = photoModels.get(position);
            view.loadImage(photoModel.getFilePath());
            view.setFavorite(photoModel.isFavorite());
        }

        @Override public void onFavoriteClick(final int position) {
            changePhotoFavoriteState(photoModels.get(position));
        }

        @Override public void onDeleteClick(final int position) {
            deleteRequest(photoModels.get(position));
        }

        private void changePhotoFavoriteState(final PhotoModel photoModel) {
            addDisposable(changePhotoFavoriteStatusUseCase.execute(photoModel)
                    .observeOn(uiScheduler)
                    .subscribe(newPhotoModel -> {
                                updatePhotoModel(newPhotoModel);
                                cameraPhotoUpdaterUseCase.execute();
                            },
                            throwable -> errorChangeFavoriteStatus(photoModel)));
        }

        private void errorChangeFavoriteStatus(final PhotoModel photoModel) {
            if (photoModel.isFavorite()) {
                mainView.showErrorDeletingFromFavoritesMessage();
            } else {
                mainView.showErrorAddingToFavoritesMessage();
            }
        }

        private void deleteRequest(final PhotoModel photoModel) {
            mainView.showPhotoDeleteDialog(photoModel);
        }
    }
}
