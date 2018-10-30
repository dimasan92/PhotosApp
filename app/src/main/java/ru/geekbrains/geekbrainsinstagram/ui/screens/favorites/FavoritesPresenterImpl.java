package ru.geekbrains.geekbrainsinstagram.ui.screens.favorites;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import ru.geekbrains.domain.interactor.photos.CameraPhotoUpdaterUseCase;
import ru.geekbrains.domain.interactor.photos.DeletePhotoUseCase;
import ru.geekbrains.domain.interactor.photos.GetFavoritesUseCase;
import ru.geekbrains.domain.interactor.photos.SearchPhotoUpdaterUseCase;
import ru.geekbrains.domain.interactor.photos.SetFavoritePhotoStatusUseCase;
import ru.geekbrains.domain.model.PhotoModel;
import ru.geekbrains.geekbrainsinstagram.di.ui.home.HomeScope;
import ru.geekbrains.geekbrainsinstagram.ui.base.BasePresenterImpl;
import ru.geekbrains.geekbrainsinstagram.ui.base.photos.BaseListPresenter.ListView;
import ru.geekbrains.geekbrainsinstagram.ui.base.photos.BaseListPresenter.RowView;
import ru.geekbrains.geekbrainsinstagram.ui.base.photos.BaseListPresenterImpl;

import static ru.geekbrains.geekbrainsinstagram.ui.screens.favorites.FavoritesPresenter.FavoritesView;

@HomeScope
public final class FavoritesPresenterImpl extends BasePresenterImpl<FavoritesView>
        implements FavoritesPresenter {

    private final GetFavoritesUseCase getFavoritesUseCase;
    private final SetFavoritePhotoStatusUseCase setFavoritePhotoStatusUseCase;
    private final DeletePhotoUseCase deletePhotoUseCase;
    private final CameraPhotoUpdaterUseCase cameraPhotoUpdaterUseCase;
    private final SearchPhotoUpdaterUseCase searchPhotoUpdaterUseCase;

    private final Scheduler uiScheduler;
    private final FavoritesListPresenterImpl listPresenter;
    private boolean wasPhotosLoad;

    @Inject FavoritesPresenterImpl(final GetFavoritesUseCase getFavoritesUseCase,
                                   final SetFavoritePhotoStatusUseCase setFavoritePhotoStatusUseCase,
                                   final DeletePhotoUseCase deletePhotoUseCase,
                                   final CameraPhotoUpdaterUseCase cameraPhotoUpdaterUseCase,
                                   final SearchPhotoUpdaterUseCase searchPhotoUpdaterUseCase,
                                   final Scheduler uiScheduler) {
        this.getFavoritesUseCase = getFavoritesUseCase;
        this.setFavoritePhotoStatusUseCase = setFavoritePhotoStatusUseCase;
        this.deletePhotoUseCase = deletePhotoUseCase;
        this.cameraPhotoUpdaterUseCase = cameraPhotoUpdaterUseCase;
        this.searchPhotoUpdaterUseCase = searchPhotoUpdaterUseCase;
        this.uiScheduler = uiScheduler;
        listPresenter = new FavoritesListPresenterImpl();
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
        listPresenter.attachView(listView);
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

    private void setPhotoFavoriteState(final PhotoModel photoModel) {
        final PhotoModel newPhotoModel = new PhotoModel(photoModel, false);
        addDisposable(setFavoritePhotoStatusUseCase
                .execute(newPhotoModel)
                .observeOn(uiScheduler)
                .subscribe(() -> {
                            listPresenter.deletePhotoModel(photoModel);
                            if (photoModel.isCameraPhoto()) {
                                cameraPhotoUpdaterUseCase.execute();
                            } else {
                                searchPhotoUpdaterUseCase.execute();
                            }
                            view.showSuccessDeletedFromFavoritesMessage();
                        },
                        throwable -> view.showErrorDeletingFromFavoritesMessage()));
    }

    private void deletePhotoFromDevice(final PhotoModel photoModel) {
        addDisposable(deletePhotoUseCase.execute(photoModel)
                .observeOn(uiScheduler)
                .subscribe(() -> {
                            listPresenter.deletePhotoModel(photoModel);
                            if (photoModel.isCameraPhoto()) {
                                cameraPhotoUpdaterUseCase.execute();
                            } else {
                                searchPhotoUpdaterUseCase.execute();
                            }
                            view.showSuccessDeletedFromDeviceMessage();
                        },
                        throwable -> view.showErrorDeletingFromDeviceMessage()));
    }

    class FavoritesListPresenterImpl extends BaseListPresenterImpl<RowView> implements FavoritesListPresenter {

        @Override public void bind(final int position, final RowView view) {
            final PhotoModel photoModel = photoModels.get(position);
            view.loadImage(photoModel.getFilePath());
        }

        @Override
        public void onDeleteFromFavoritesClick(final int position) {
            setPhotoFavoriteState(photoModels.get(position));
        }

        @Override public void onDeleteFromDeviceClick(final int position) {
            deletePhotoFromDevice(photoModels.get(position));
        }
    }
}
