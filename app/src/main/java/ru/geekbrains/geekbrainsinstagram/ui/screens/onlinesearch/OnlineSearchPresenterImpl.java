package ru.geekbrains.geekbrainsinstagram.ui.screens.onlinesearch;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import ru.geekbrains.data.exception.NoNetworkConnectionException;
import ru.geekbrains.domain.interactor.photos.DeletePhotoUseCase;
import ru.geekbrains.domain.interactor.photos.GetPhotosBySearchUseCase;
import ru.geekbrains.domain.interactor.photos.SaveSearchPhotoUseCase;
import ru.geekbrains.domain.interactor.photos.SearchPhotoUpdaterUseCase;
import ru.geekbrains.domain.interactor.photos.changeFavoritePhotoStatusUseCase;
import ru.geekbrains.domain.interactor.photos.UpdateSearchPhotosUseCase;
import ru.geekbrains.domain.model.PhotoModel;
import ru.geekbrains.geekbrainsinstagram.di.ui.home.HomeScope;
import ru.geekbrains.geekbrainsinstagram.ui.base.BasePresenterImpl;
import ru.geekbrains.geekbrainsinstagram.ui.base.photos.BaseListPresenter.ListView;
import ru.geekbrains.geekbrainsinstagram.ui.base.photos.BaseListPresenterImpl;
import ru.geekbrains.geekbrainsinstagram.ui.screens.onlinesearch.OnlineSearchListPresenter.OnlineSearchRowView;
import ru.geekbrains.geekbrainsinstagram.ui.screens.onlinesearch.OnlineSearchPresenter.OnlineSearchView;
import ru.geekbrains.geekbrainsinstagram.util.PictureUtils;

@HomeScope
public final class OnlineSearchPresenterImpl extends BasePresenterImpl<OnlineSearchView>
        implements OnlineSearchPresenter {

    private static final int PHOTOS_COUNT = 30;

    private final GetPhotosBySearchUseCase getPhotosBySearchUseCase;
    private final changeFavoritePhotoStatusUseCase changeFavoritePhotoStatusUseCase;
    private final SaveSearchPhotoUseCase saveSearchPhotoUseCase;
    private final DeletePhotoUseCase deletePhotoUseCase;
    private final SearchPhotoUpdaterUseCase searchPhotoUpdaterUseCase;
    private final UpdateSearchPhotosUseCase updateSearchPhotosUseCase;

    private final PictureUtils pictureUtils;
    private final Scheduler uiScheduler;
    private final OnlineSearchListPresenterImpl listPresenter;
    private boolean wasPhotosUpdated;

    @Inject OnlineSearchPresenterImpl(final GetPhotosBySearchUseCase getPhotosBySearchUseCase,
                                      final changeFavoritePhotoStatusUseCase changeFavoritePhotoStatusUseCase,
                                      final SaveSearchPhotoUseCase saveSearchPhotoUseCase,
                                      final DeletePhotoUseCase deletePhotoUseCase,
                                      final SearchPhotoUpdaterUseCase searchPhotoUpdaterUseCase,
                                      final UpdateSearchPhotosUseCase updateSearchPhotosUseCase,
                                      final PictureUtils pictureUtils, final Scheduler uiScheduler) {
        this.getPhotosBySearchUseCase = getPhotosBySearchUseCase;
        this.changeFavoritePhotoStatusUseCase = changeFavoritePhotoStatusUseCase;
        this.saveSearchPhotoUseCase = saveSearchPhotoUseCase;
        this.deletePhotoUseCase = deletePhotoUseCase;
        this.searchPhotoUpdaterUseCase = searchPhotoUpdaterUseCase;
        this.updateSearchPhotosUseCase = updateSearchPhotosUseCase;
        this.pictureUtils = pictureUtils;
        this.uiScheduler = uiScheduler;
        listPresenter = new OnlineSearchListPresenterImpl();
    }

    @Override public void create() {
        view.init(listPresenter);
        searchPhotoUpdaterUseCase.subscribe(b -> wasPhotosUpdated = true);
    }

    @Override public void userVisibleHint() {
        checkPhotosUpdates();
    }

    @Override public void start() {
        checkPhotosUpdates();
    }

    @Override public void stop() {
        super.stop();
        listPresenter.detachView();
    }

    @Override public void attachListView(final ListView listView) {
        listPresenter.attachView(listView);
    }

    @Override public void onSearchClick(final String query) {
        addDisposable(getPhotosBySearchUseCase.execute(query, PHOTOS_COUNT)
                .observeOn(uiScheduler)
                .subscribe(listPresenter::setPhotoModels,
                        throwable -> {
                            if (throwable instanceof NoNetworkConnectionException) {
                                view.showErrorNetworkMessage();
                            } else {
                                view.showErrorDownloadingPhotosMessage();
                            }
                        }));
    }

    private void checkPhotosUpdates() {
        if (wasPhotosUpdated) {
            wasPhotosUpdated = false;
            listPresenter.photosWasUpdated();
        }
    }

    private void updatePhotoModels(final List<PhotoModel> photoModels) {
        addDisposable(updateSearchPhotosUseCase.execute(photoModels)
                .observeOn(uiScheduler)
                .subscribe(listPresenter::setPhotoModels,
                        throwable -> wasPhotosUpdated = true));
    }

    private void savePhoto(final PhotoModel photoModel) {
        addDisposable(pictureUtils.getImageArray(photoModel.getRegularPhotoUrl())
                .subscribe(bytes -> addDisposable(saveSearchPhotoUseCase
                                .execute(photoModel, bytes)
                                .observeOn(uiScheduler)
                                .subscribe(this::updatePhotoView,
                                        throwable -> view.showErrorSavingPhoto())),
                        throwable -> {
                            view.showErrorSavingPhoto();
                            throwable.printStackTrace();
                        }));
    }

    private void deletePhoto(final PhotoModel photoModel) {
        addDisposable(deletePhotoUseCase.execute(photoModel)
                .observeOn(uiScheduler)
                .subscribe(() -> updatePhotoView(new PhotoModel(photoModel, "")),
                        throwable -> view.showErrorDeletingPhoto()));
    }

    private void setPhotoFavoriteState(final PhotoModel photoModel) {
        addDisposable(changeFavoritePhotoStatusUseCase
                .execute(photoModel)
                .observeOn(uiScheduler)
                .subscribe(this::updatePhotoView,
                        throwable -> {
                            if (photoModel.isFavorite()) {
                                view.showErrorDeletingFromFavoritesMessage();
                            } else {
                                view.showErrorAddingToFavoritesMessage();
                            }
                        }));
    }

    private void updatePhotoView(final PhotoModel photoModel) {
        listPresenter.updatePhotoModel(photoModel);
        searchPhotoUpdaterUseCase.execute();
    }

    class OnlineSearchListPresenterImpl extends BaseListPresenterImpl<OnlineSearchRowView> implements OnlineSearchListPresenter {

        @Override public void bind(final int position, final OnlineSearchRowView rowView) {
            final PhotoModel photoModel = photoModels.get(position);
            rowView.loadImage(photoModel.getSmallPhotoUrl());
            final boolean isSaved = photoModel.isSaved();
            rowView.setSaving(isSaved);
            rowView.favoriteVisibility(isSaved);
            if (isSaved) {
                rowView.setFavorite(photoModel.isFavorite());
            }
        }

        @Override public void onFavoriteClick(final int position) {
            setPhotoFavoriteState(photoModels.get(position));
        }

        @Override public void onIoActionClick(final int position) {
            final PhotoModel photoModel = photoModels.get(position);
            if (photoModel.isSaved()) {
                deletePhoto(photoModel);
            } else {
                savePhoto(photoModel);
            }
        }

        void photosWasUpdated() {
            updatePhotoModels(photoModels);
        }
    }
}
