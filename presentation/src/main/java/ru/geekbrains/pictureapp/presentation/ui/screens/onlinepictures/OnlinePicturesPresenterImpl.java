package ru.geekbrains.pictureapp.presentation.ui.screens.onlinepictures;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.functions.Action;
import ru.geekbrains.pictureapp.data.exception.NetworkConnectionException;
import ru.geekbrains.pictureapp.domain.interactor.photos.common.DeleteImageUseCase;
import ru.geekbrains.pictureapp.domain.interactor.photos.favorites.ChangeFavoriteStatusUseCase;
import ru.geekbrains.pictureapp.domain.interactor.photos.pictures.GetPicturesByQueryUseCase;
import ru.geekbrains.pictureapp.domain.interactor.photos.pictures.SavePictureUseCase;
import ru.geekbrains.pictureapp.domain.interactor.photos.pictures.UpdatePicturesUseCase;
import ru.geekbrains.pictureapp.domain.model.ImageModel;
import ru.geekbrains.pictureapp.presentation.di.ui.home.HomeScope;
import ru.geekbrains.pictureapp.presentation.ui.base.BasePresenterImpl;
import ru.geekbrains.pictureapp.presentation.ui.base.photos.BaseListPresenter.ListView;
import ru.geekbrains.pictureapp.presentation.ui.base.photos.BaseListPresenterImpl;
import ru.geekbrains.pictureapp.presentation.ui.navigator.MainNavigator;
import ru.geekbrains.pictureapp.presentation.ui.screens.onlinepictures.OnlinePicturesListPresenter.OnlineSearchRowView;
import ru.geekbrains.pictureapp.presentation.ui.screens.onlinepictures.OnlinePicturesPresenter.OnlinePicturesView;
import ru.geekbrains.pictureapp.presentation.ui.updater.Update;
import ru.geekbrains.pictureapp.presentation.ui.updater.Updater;
import ru.geekbrains.pictureapp.presentation.util.ParseUtils;
import ru.geekbrains.pictureapp.presentation.util.PictureUtils;

@HomeScope
public final class OnlinePicturesPresenterImpl extends BasePresenterImpl<OnlinePicturesView>
        implements OnlinePicturesPresenter {

    private static final int PHOTOS_COUNT = 30;

    private final GetPicturesByQueryUseCase getPicturesByQueryUseCase;
    private final Updater updater;

    private final Scheduler uiScheduler;
    private final OnlinePicturesListPresenterImpl listPresenter;
    private boolean wasPhotosUpdated;

    @Inject
    OnlinePicturesPresenterImpl(final GetPicturesByQueryUseCase getPicturesByQueryUseCase,
                                final Updater updater,
                                final OnlinePicturesListPresenterImpl listPresenter,
                                final Scheduler uiScheduler) {
        this.getPicturesByQueryUseCase = getPicturesByQueryUseCase;
        this.updater = updater;
        this.listPresenter = listPresenter;
        this.uiScheduler = uiScheduler;
    }

    @Override
    public void create() {
        view.init(listPresenter);
        updater.subscribe(update -> {
            if (update == Update.PICTURE) {
                wasPhotosUpdated = true;
            }
        });
    }

    @Override
    public void userVisibleHint() {
        checkPhotosUpdates();
    }

    @Override
    public void start() {
        checkPhotosUpdates();
    }

    private void checkPhotosUpdates() {
        if (wasPhotosUpdated) {
            wasPhotosUpdated = false;
            listPresenter.photosWasUpdated(() -> wasPhotosUpdated = true);
        }
    }

    @Override
    public void stop() {
        super.stop();
        listPresenter.detachView();
    }

    @Override
    public void attachListView(final ListView listView) {
        listPresenter.attachView(view, listView);
    }

    @Override
    public void onSearchClick(final String query) {
        addDisposable(getPicturesByQueryUseCase.execute(query, PHOTOS_COUNT)
                .observeOn(uiScheduler)
                .subscribe(listPresenter::setImageModels,
                        throwable -> {
                            if (throwable instanceof NetworkConnectionException) {
                                view.showErrorNetworkMessage();
                            } else {
                                view.showErrorDownloadingPhotosMessage();
                            }
                        }));
    }

    @HomeScope
    static class OnlinePicturesListPresenterImpl extends BaseListPresenterImpl<OnlinePicturesView, OnlineSearchRowView>
            implements OnlinePicturesListPresenter {

        final ChangeFavoriteStatusUseCase changeFavoriteStatusUseCase;
        private final SavePictureUseCase savePictureUseCase;
        private final DeleteImageUseCase deleteImageUseCase;
        private final UpdatePicturesUseCase updatePicturesUseCase;
        private final Updater updater;
        private final PictureUtils pictureUtils;
        private final Scheduler uiScheduler;
        private final MainNavigator mainNavigator;
        private final ParseUtils parseUtils;

        @Inject
        OnlinePicturesListPresenterImpl(final ChangeFavoriteStatusUseCase changeFavoriteStatusUseCase,
                                        final SavePictureUseCase savePictureUseCase,
                                        final DeleteImageUseCase deleteImageUseCase,
                                        final UpdatePicturesUseCase updatePicturesUseCase,
                                        final Updater updater,
                                        final PictureUtils pictureUtils, final Scheduler uiScheduler,
                                        final MainNavigator mainNavigator, final ParseUtils parseUtils) {
            this.changeFavoriteStatusUseCase = changeFavoriteStatusUseCase;
            this.savePictureUseCase = savePictureUseCase;
            this.deleteImageUseCase = deleteImageUseCase;
            this.updatePicturesUseCase = updatePicturesUseCase;
            this.updater = updater;
            this.pictureUtils = pictureUtils;
            this.uiScheduler = uiScheduler;
            this.mainNavigator = mainNavigator;
            this.parseUtils = parseUtils;
        }


        @Override
        public void bind(final int position, final OnlineSearchRowView rowView) {
            final ImageModel imageModel = imageModels.get(position);
            rowView.loadImage(imageModel);
            final boolean isSaved = imageModel.isSaved();
            rowView.setSaving(isSaved);
            rowView.favoriteVisibility(isSaved);
            if (isSaved) {
                rowView.setFavorite(imageModel.isFavorite());
            }
        }

        @Override
        public void onFavoriteClick(final int position) {
            setPhotoFavoriteState(imageModels.get(position));
        }

        private void setPhotoFavoriteState(final ImageModel imageModel) {
            addDisposable(changeFavoriteStatusUseCase
                    .execute(imageModel)
                    .observeOn(uiScheduler)
                    .subscribe(this::updatePhotoView,
                            throwable -> {
                                if (imageModel.isFavorite()) {
                                    mainView.showErrorDeletingFromFavoritesMessage();
                                } else {
                                    mainView.showErrorAddingToFavoritesMessage();
                                }
                            }));
        }

        @Override
        public void onIoActionClick(final int position) {
            final ImageModel imageModel = imageModels.get(position);
            if (imageModel.isSaved()) {
                deletePhoto(imageModel);
            } else {
                savePhoto(imageModel);
            }
        }

        private void savePhoto(final ImageModel imageModel) {
            addDisposable(pictureUtils.getImageArray(imageModel.getRegularUrl())
                    .subscribe(bytes -> addDisposable(savePictureUseCase
                                    .execute(imageModel, bytes)
                                    .observeOn(uiScheduler)
                                    .subscribe(this::updatePhotoView,
                                            throwable -> mainView.showErrorSavingPhoto())),
                            throwable -> {
                                mainView.showErrorSavingPhoto();
                                throwable.printStackTrace();
                            }));
        }

        private void deletePhoto(final ImageModel imageModel) {
            addDisposable(deleteImageUseCase.execute(imageModel)
                    .observeOn(uiScheduler)
                    .subscribe(() -> updatePhotoView(new ImageModel(imageModel, "")),
                            throwable -> mainView.showErrorDeletingPhoto()));
        }

        private void updatePhotoView(final ImageModel imageModel) {
            updateImageModel(imageModel);
            updater.update(imageModel);
        }

        @Override
        public void onFullClick(int position) {
            final String[] jsons = parseUtils.parseObjects(imageModels);
            mainNavigator.navigateToDetails(jsons, position);
        }

        void photosWasUpdated(final Action errorAction) {
            addDisposable(updatePicturesUseCase.execute(imageModels)
                    .observeOn(uiScheduler)
                    .subscribe(this::setImageModels,
                            throwable -> errorAction.run()));
        }
    }
}
