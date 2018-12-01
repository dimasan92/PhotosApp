package ru.geekbrains.pictureapp.presentation.ui.screens.photos;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import ru.geekbrains.pictureapp.domain.interactor.photos.camera.GetPhotosUseCase;
import ru.geekbrains.pictureapp.domain.interactor.photos.camera.GetPlaceForNewPhotoUseCase;
import ru.geekbrains.pictureapp.domain.interactor.photos.common.DeleteImageUseCase;
import ru.geekbrains.pictureapp.domain.interactor.photos.favorites.ChangeFavoriteStatusUseCase;
import ru.geekbrains.pictureapp.domain.model.ImageModel;
import ru.geekbrains.pictureapp.presentation.di.ui.home.HomeScope;
import ru.geekbrains.pictureapp.presentation.ui.base.BasePresenterImpl;
import ru.geekbrains.pictureapp.presentation.ui.base.photos.BaseListPresenter.ListView;
import ru.geekbrains.pictureapp.presentation.ui.base.photos.BaseListPresenterImpl;
import ru.geekbrains.pictureapp.presentation.ui.navigator.MainNavigator;
import ru.geekbrains.pictureapp.presentation.ui.screens.photos.PhotosListPresenter.PhotoRowView;
import ru.geekbrains.pictureapp.presentation.ui.screens.photos.PhotosPresenter.PhotosView;
import ru.geekbrains.pictureapp.presentation.ui.updater.Update;
import ru.geekbrains.pictureapp.presentation.ui.updater.Updater;
import ru.geekbrains.pictureapp.presentation.util.CameraUtils;
import ru.geekbrains.pictureapp.presentation.util.ParseUtils;

@HomeScope
public final class PhotosPresenterImpl extends BasePresenterImpl<PhotosView>
        implements PhotosPresenter {

    private final GetPhotosUseCase getPhotosUseCase;
    private final GetPlaceForNewPhotoUseCase getPlaceForNewPhotoUseCase;
    private final DeleteImageUseCase deleteImageUseCase;
    private final Updater updater;

    private final Scheduler uiScheduler;
    private final CameraUtils cameraUtils;
    private final PhotosListPresenterImpl listPresenter;
    private int resultOk;

    private boolean wasPhotosLoad;
    private ImageModel newCameraImageModel;

    @Inject
    PhotosPresenterImpl(final GetPhotosUseCase getPhotosUseCase,
                        final GetPlaceForNewPhotoUseCase getPlaceForNewPhotoUseCase,
                        final DeleteImageUseCase deleteImageUseCase,
                        final Updater updater, final PhotosListPresenterImpl listPresenter,
                        final Scheduler uiScheduler, final CameraUtils cameraUtils) {
        this.getPhotosUseCase = getPhotosUseCase;
        this.getPlaceForNewPhotoUseCase = getPlaceForNewPhotoUseCase;
        this.deleteImageUseCase = deleteImageUseCase;
        this.updater = updater;
        this.uiScheduler = uiScheduler;
        this.cameraUtils = cameraUtils;
        this.listPresenter = listPresenter;
    }

    @Override
    public void create() {
        view.init(listPresenter);
        updater.subscribe(update -> {
            if (update == Update.PHOTO) {
                wasPhotosLoad = false;
            }
        });
    }

    @Override
    public void attachListView(final ListView listView) {
        listPresenter.attachView(view, listView);
    }

    @Override
    public void start() {
        if (!wasPhotosLoad) {
            uploadPhotos();
        }
    }

    private void uploadPhotos() {
        addDisposable(getPhotosUseCase.execute()
                .observeOn(uiScheduler)
                .subscribe(photoModels -> {
                            listPresenter.setImageModels(photoModels);
                            wasPhotosLoad = true;
                        },
                        getDefaultErrorHandler()));
    }

    @Override
    public void stop() {
        super.stop();
        listPresenter.detachView();
    }

    @Override
    public void setCameraResultOkCode(final int resultOkCode) {
        resultOk = resultOkCode;
    }

    @Override
    public void takeAPhotoRequest() {
        addDisposable(getPlaceForNewPhotoUseCase.execute()
                .subscribeOn(uiScheduler)
                .subscribe(this::invokeCamera,
                        throwable -> cameraCouldNotLaunch()));
    }

    private void invokeCamera(final ImageModel newCameraImageModel) {
        this.newCameraImageModel = newCameraImageModel;
        view.startCamera(newCameraImageModel.getFilePath());
    }

    @Override
    public void cameraHasClosed(final int resultCode) {
        if (newCameraImageModel != null) {
            if (resultCode == resultOk) {
                listPresenter.addImageModel(newCameraImageModel);
            }
            cameraUtils.revokeCameraPermissions(newCameraImageModel.getFilePath());
        }
        newCameraImageModel = null;
    }

    @Override
    public void cameraCouldNotLaunch() {
        view.showCouldNotLaunchCameraMessage();
        newCameraImageModel = null;
    }

    @Override
    public void deletePhotoConfirm(final ImageModel imageModel) {
        addDisposable(deleteImageUseCase.execute(imageModel)
                .observeOn(uiScheduler)
                .subscribe(() -> {
                            listPresenter.deleteImageModel(imageModel);
                            updater.update(imageModel);
                        },
                        throwable -> view.showErrorDeletingPhotoMessage()));
    }

    @HomeScope
    static class PhotosListPresenterImpl extends BaseListPresenterImpl<PhotosView, PhotoRowView>
            implements PhotosListPresenter {

        private final ChangeFavoriteStatusUseCase changeFavoriteStatusUseCase;
        private final Updater updater;
        private final Scheduler uiScheduler;
        private final MainNavigator mainNavigator;
        private final ParseUtils parseUtils;

        @Inject
        PhotosListPresenterImpl(final ChangeFavoriteStatusUseCase changeFavoriteStatusUseCase,
                                final Updater updater, final Scheduler uiScheduler,
                                final MainNavigator mainNavigator, final ParseUtils parseUtils) {
            this.changeFavoriteStatusUseCase = changeFavoriteStatusUseCase;
            this.updater = updater;
            this.uiScheduler = uiScheduler;
            this.mainNavigator = mainNavigator;
            this.parseUtils = parseUtils;
        }

        @Override
        public void bind(final int position, final PhotoRowView view) {
            final ImageModel imageModel = imageModels.get(position);
            view.loadImage(imageModel);
            view.setFavorite(imageModel.isFavorite());
        }

        @Override
        public void onFavoriteClick(final int position) {
            changePhotoFavoriteState(imageModels.get(position));
        }

        private void changePhotoFavoriteState(final ImageModel imageModel) {
            addDisposable(changeFavoriteStatusUseCase.execute(imageModel)
                    .observeOn(uiScheduler)
                    .subscribe(newPhotoModel -> {
                                updateImageModel(newPhotoModel);
                                updater.update(newPhotoModel);
                            },
                            throwable -> errorChangeFavoriteStatus(imageModel)));
        }

        private void errorChangeFavoriteStatus(final ImageModel imageModel) {
            if (imageModel.isFavorite()) {
                mainView.showErrorDeletingFromFavoritesMessage();
            } else {
                mainView.showErrorAddingToFavoritesMessage();
            }
        }

        @Override
        public void onDeleteClick(final int position) {
            deleteRequest(imageModels.get(position));
        }

        private void deleteRequest(final ImageModel imageModel) {
            mainView.showPhotoDeleteDialog(imageModel);
        }

        @Override
        public void onFullClick(int position) {
            final String[] jsons = parseUtils.parseObjects(imageModels);
            mainNavigator.navigateToDetails(jsons, position);
        }
    }
}
