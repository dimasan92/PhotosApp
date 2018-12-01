package ru.geekbrains.pictureapp.presentation.ui.screens.savedpictures;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import ru.geekbrains.pictureapp.domain.interactor.photos.common.DeleteImageUseCase;
import ru.geekbrains.pictureapp.domain.interactor.photos.favorites.ChangeFavoriteStatusUseCase;
import ru.geekbrains.pictureapp.domain.interactor.photos.pictures.GetSavedPicturesUseCase;
import ru.geekbrains.pictureapp.domain.model.ImageModel;
import ru.geekbrains.pictureapp.presentation.di.ui.home.HomeScope;
import ru.geekbrains.pictureapp.presentation.ui.base.BasePresenterImpl;
import ru.geekbrains.pictureapp.presentation.ui.base.photos.BaseListPresenter.ListView;
import ru.geekbrains.pictureapp.presentation.ui.base.photos.BaseListPresenterImpl;
import ru.geekbrains.pictureapp.presentation.ui.navigator.MainNavigator;
import ru.geekbrains.pictureapp.presentation.ui.screens.savedpictures.SavedPicturesListPresenter.SavedPicturesRowView;
import ru.geekbrains.pictureapp.presentation.ui.screens.savedpictures.SavedPicturesPresenter.SavedPicturesView;
import ru.geekbrains.pictureapp.presentation.ui.updater.Update;
import ru.geekbrains.pictureapp.presentation.ui.updater.Updater;
import ru.geekbrains.pictureapp.presentation.util.ParseUtils;

@HomeScope
public final class SavedPicturesPresenterImpl extends BasePresenterImpl<SavedPicturesView>
        implements SavedPicturesPresenter {

    private final GetSavedPicturesUseCase getSavedPicturesUseCase;
    private final Updater updater;

    private final Scheduler uiScheduler;
    private final SavedPicturesListPresenterImpl listPresenter;

    private boolean wasPhotosLoad;

    @Inject
    SavedPicturesPresenterImpl(final GetSavedPicturesUseCase getSavedPicturesUseCase,
                               final Updater updater,
                               final SavedPicturesListPresenterImpl listPresenter,
                               final Scheduler uiScheduler) {
        this.getSavedPicturesUseCase = getSavedPicturesUseCase;
        this.updater = updater;
        this.uiScheduler = uiScheduler;
        this.listPresenter = listPresenter;
    }

    @Override
    public void create() {
        view.init(listPresenter);
        updater.subscribe(update -> {
            if (update == Update.PICTURE) {
                wasPhotosLoad = false;
            }
        });
    }

    @Override
    public void userVisibleHint() {
        checkPhotosLoading();
    }

    @Override
    public void start() {
        checkPhotosLoading();
    }

    private void checkPhotosLoading() {
        if (!wasPhotosLoad) {
            wasPhotosLoad = true;
            uploadPhotos();
        }
    }

    private void uploadPhotos() {
        addDisposable(getSavedPicturesUseCase.execute()
                .observeOn(uiScheduler)
                .subscribe(listPresenter::setImageModels,
                        throwable -> wasPhotosLoad = false));
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

    @HomeScope
    static class SavedPicturesListPresenterImpl extends BaseListPresenterImpl<SavedPicturesView, SavedPicturesRowView>
            implements SavedPicturesListPresenter {

        private final ChangeFavoriteStatusUseCase changeFavoriteStatusUseCase;
        private final DeleteImageUseCase deleteImageUseCase;
        private final Updater updater;
        private final Scheduler uiScheduler;
        private final MainNavigator mainNavigator;
        private final ParseUtils parseUtils;

        @Inject
        SavedPicturesListPresenterImpl(final ChangeFavoriteStatusUseCase changeFavoriteStatusUseCase,
                                       final DeleteImageUseCase deleteImageUseCase,
                                       final Updater updater, final Scheduler uiScheduler,
                                       final MainNavigator mainNavigator,
                                       final ParseUtils parseUtils) {
            this.changeFavoriteStatusUseCase = changeFavoriteStatusUseCase;
            this.deleteImageUseCase = deleteImageUseCase;
            this.updater = updater;
            this.uiScheduler = uiScheduler;
            this.mainNavigator = mainNavigator;
            this.parseUtils = parseUtils;
        }


        @Override
        public void bind(final int position, final SavedPicturesRowView rowView) {
            final ImageModel imageModel = imageModels.get(position);
            rowView.loadImage(imageModel);
            rowView.setFavorite(imageModel.isFavorite());
        }

        @Override
        public void onFavoriteClick(final int position) {
            changePhotoFavoriteState(imageModels.get(position));
        }

        private void changePhotoFavoriteState(final ImageModel imageModel) {
            addDisposable(changeFavoriteStatusUseCase
                    .execute(imageModel)
                    .observeOn(uiScheduler)
                    .subscribe(newPhotoModel -> {
                                updateImageModel(newPhotoModel);
                                updater.update(newPhotoModel);
                            },
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
            deletePhoto(imageModel);
        }

        private void deletePhoto(final ImageModel imageModel) {
            addDisposable(deleteImageUseCase.execute(imageModel)
                    .observeOn(uiScheduler)
                    .subscribe(() -> {
                                deleteImageModel(imageModel);
                                updater.update(imageModel);
                            },
                            throwable -> mainView.showErrorDeletingPhoto()));
        }

        @Override
        public void onFullClick(int position) {
            final String[] jsons = parseUtils.parseObjects(imageModels);
            mainNavigator.navigateToDetails(jsons, position);
        }
    }
}
