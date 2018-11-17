package ru.geekbrains.pictureapp.presentation.ui.screens.favorites;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import ru.geekbrains.pictureapp.domain.interactor.photos.camera.CameraPhotoUpdaterUseCase;
import ru.geekbrains.pictureapp.domain.interactor.photos.favorites.ChangePhotoFavoriteStatusUseCase;
import ru.geekbrains.pictureapp.domain.interactor.photos.common.DeletePhotoUseCase;
import ru.geekbrains.pictureapp.domain.interactor.photos.favorites.GetFavoritesUseCase;
import ru.geekbrains.pictureapp.domain.interactor.photos.search.SearchPhotoUpdaterUseCase;
import ru.geekbrains.pictureapp.domain.model.PhotoModel;
import ru.geekbrains.pictureapp.presentation.di.ui.home.HomeScope;
import ru.geekbrains.pictureapp.presentation.ui.base.BasePresenterImpl;
import ru.geekbrains.pictureapp.presentation.ui.base.photos.BaseListPresenter.ListView;
import ru.geekbrains.pictureapp.presentation.ui.base.photos.BaseListPresenter.RowView;
import ru.geekbrains.pictureapp.presentation.ui.base.photos.BaseListPresenterImpl;

import static ru.geekbrains.pictureapp.presentation.ui.screens.favorites.FavoritesPresenter.FavoritesView;

@HomeScope
public final class FavoritesPresenterImpl extends BasePresenterImpl<FavoritesView>
        implements FavoritesPresenter {

    private final GetFavoritesUseCase getFavoritesUseCase;
    private final CameraPhotoUpdaterUseCase cameraPhotoUpdaterUseCase;
    private final SearchPhotoUpdaterUseCase searchPhotoUpdaterUseCase;

    private final Scheduler uiScheduler;
    private final FavoritesListPresenterImpl listPresenter;
    private boolean wasPhotosLoad;

    @Inject FavoritesPresenterImpl(final GetFavoritesUseCase getFavoritesUseCase,
                                   final CameraPhotoUpdaterUseCase cameraPhotoUpdaterUseCase,
                                   final SearchPhotoUpdaterUseCase searchPhotoUpdaterUseCase,
                                   final FavoritesListPresenterImpl listPresenter,
                                   final Scheduler uiScheduler) {
        this.getFavoritesUseCase = getFavoritesUseCase;
        this.cameraPhotoUpdaterUseCase = cameraPhotoUpdaterUseCase;
        this.searchPhotoUpdaterUseCase = searchPhotoUpdaterUseCase;
        this.listPresenter = listPresenter;
        this.uiScheduler = uiScheduler;
    }

    @Override public void create() {
        view.init(listPresenter);
        cameraPhotoUpdaterUseCase.subscribe(b -> wasPhotosLoad = false);
        searchPhotoUpdaterUseCase.subscribe(b -> wasPhotosLoad = false);
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

    @Override public void attachListView(final ListView listView) {
        listPresenter.attachView(view, listView);
    }

    private void uploadPhotos() {
        addDisposable(getFavoritesUseCase.execute()
                .observeOn(uiScheduler)
                .subscribe(photoModels -> {
                            listPresenter.setPhotoModels(photoModels);
                            wasPhotosLoad = true;
                        },
                        getDefaultErrorHandler()));
    }

    @HomeScope
    public static class FavoritesListPresenterImpl extends BaseListPresenterImpl<FavoritesView, RowView>
            implements FavoritesListPresenter {

        private final ChangePhotoFavoriteStatusUseCase changePhotoFavoriteStatusUseCase;
        private final DeletePhotoUseCase deletePhotoUseCase;
        private final CameraPhotoUpdaterUseCase cameraPhotoUpdaterUseCase;
        private final SearchPhotoUpdaterUseCase searchPhotoUpdaterUseCase;
        private final Scheduler uiScheduler;

        @Inject FavoritesListPresenterImpl(final ChangePhotoFavoriteStatusUseCase changePhotoFavoriteStatusUseCase,
                                           final DeletePhotoUseCase deletePhotoUseCase,
                                           final CameraPhotoUpdaterUseCase cameraPhotoUpdaterUseCase,
                                           final SearchPhotoUpdaterUseCase searchPhotoUpdaterUseCase,
                                           final Scheduler uiScheduler) {
            this.changePhotoFavoriteStatusUseCase = changePhotoFavoriteStatusUseCase;
            this.deletePhotoUseCase = deletePhotoUseCase;
            this.cameraPhotoUpdaterUseCase = cameraPhotoUpdaterUseCase;
            this.searchPhotoUpdaterUseCase = searchPhotoUpdaterUseCase;
            this.uiScheduler = uiScheduler;
        }


        @Override public void bind(final int position, final RowView view) {
            final PhotoModel photoModel = photoModels.get(position);
            view.loadImage(photoModel.getFilePath());
        }

        @Override
        public void onDeleteFromFavoritesClick(final int position) {
            changePhotoFavoriteState(photoModels.get(position));
        }

        @Override public void onDeleteFromDeviceClick(final int position) {
            deletePhotoFromDevice(photoModels.get(position));
        }

        private void changePhotoFavoriteState(final PhotoModel photoModel) {
            addDisposable(changePhotoFavoriteStatusUseCase
                    .execute(photoModel)
                    .observeOn(uiScheduler)
                    .subscribe(newPhotoModel -> {
                                deletePhotoModel(photoModel);
                                if (photoModel.isCameraPhoto()) {
                                    cameraPhotoUpdaterUseCase.execute();
                                } else {
                                    searchPhotoUpdaterUseCase.execute();
                                }
                                mainView.showSuccessDeletedFromFavoritesMessage();
                            },
                            throwable -> mainView.showErrorDeletingFromFavoritesMessage()));
        }

        private void deletePhotoFromDevice(final PhotoModel photoModel) {
            addDisposable(deletePhotoUseCase.execute(photoModel)
                    .observeOn(uiScheduler)
                    .subscribe(() -> {
                                deletePhotoModel(photoModel);
                                if (photoModel.isCameraPhoto()) {
                                    cameraPhotoUpdaterUseCase.execute();
                                } else {
                                    searchPhotoUpdaterUseCase.execute();
                                }
                                mainView.showSuccessDeletedFromDeviceMessage();
                            },
                            throwable -> mainView.showErrorDeletingFromDeviceMessage()));
        }
    }
}
