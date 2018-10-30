package ru.geekbrains.geekbrainsinstagram.ui.screens.savedsearch;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import ru.geekbrains.domain.interactor.photos.ChangePhotoFavoriteStatusUseCase;
import ru.geekbrains.domain.interactor.photos.DeletePhotoUseCase;
import ru.geekbrains.domain.interactor.photos.GetSavedSearchPhotosUseCase;
import ru.geekbrains.domain.interactor.photos.SearchPhotoUpdaterUseCase;
import ru.geekbrains.domain.model.PhotoModel;
import ru.geekbrains.geekbrainsinstagram.di.ui.home.HomeScope;
import ru.geekbrains.geekbrainsinstagram.ui.base.BasePresenterImpl;
import ru.geekbrains.geekbrainsinstagram.ui.base.photos.BaseListPresenter.ListView;
import ru.geekbrains.geekbrainsinstagram.ui.base.photos.BaseListPresenterImpl;
import ru.geekbrains.geekbrainsinstagram.ui.screens.savedsearch.SavedSearchListPresenter.SavedSearchRowView;
import ru.geekbrains.geekbrainsinstagram.ui.screens.savedsearch.SavedSearchPresenter.SavedSearchView;

@HomeScope
public final class SavedSearchPresenterImpl extends BasePresenterImpl<SavedSearchView>
        implements SavedSearchPresenter {

    private final GetSavedSearchPhotosUseCase getSavedSearchPhotosUseCase;
    private final SearchPhotoUpdaterUseCase searchPhotoUpdaterUseCase;

    private final Scheduler uiScheduler;
    private final SavedSearchListPresenterImpl listPresenter;

    private boolean wasPhotosLoad;

    @Inject SavedSearchPresenterImpl(final GetSavedSearchPhotosUseCase getSavedSearchPhotosUseCase,
                                     final SearchPhotoUpdaterUseCase searchPhotoUpdaterUseCase,
                                     final SavedSearchListPresenterImpl listPresenter,
                                     final Scheduler uiScheduler) {
        this.getSavedSearchPhotosUseCase = getSavedSearchPhotosUseCase;
        this.searchPhotoUpdaterUseCase = searchPhotoUpdaterUseCase;
        this.uiScheduler = uiScheduler;
        this.listPresenter = listPresenter;
    }

    @Override public void create() {
        view.init(listPresenter);
        searchPhotoUpdaterUseCase.subscribe(b -> wasPhotosLoad = false);
    }

    @Override public void userVisibleHint() {
        checkPhotosLoading();
    }

    @Override public void start() {
        checkPhotosLoading();
    }

    @Override public void stop() {
        super.stop();
        listPresenter.detachView();
    }

    @Override public void attachListView(final ListView listView) {
        listPresenter.attachView(view, listView);
    }

    private void checkPhotosLoading() {
        if (!wasPhotosLoad) {
            wasPhotosLoad = true;
            uploadPhotos();
        }
    }

    private void uploadPhotos() {
        addDisposable(getSavedSearchPhotosUseCase.execute()
                .observeOn(uiScheduler)
                .subscribe(listPresenter::setPhotoModels,
                        throwable -> wasPhotosLoad = false));
    }

    @HomeScope
    static class SavedSearchListPresenterImpl extends BaseListPresenterImpl<SavedSearchView, SavedSearchRowView>
            implements SavedSearchListPresenter {

        private final ChangePhotoFavoriteStatusUseCase changePhotoFavoriteStatusUseCase;
        private final DeletePhotoUseCase deletePhotoUseCase;
        private final SearchPhotoUpdaterUseCase searchPhotoUpdaterUseCase;
        private final Scheduler uiScheduler;

        @Inject SavedSearchListPresenterImpl(final ChangePhotoFavoriteStatusUseCase changePhotoFavoriteStatusUseCase,
                                             final DeletePhotoUseCase deletePhotoUseCase,
                                             final SearchPhotoUpdaterUseCase searchPhotoUpdaterUseCase,
                                             final Scheduler uiScheduler) {
            this.changePhotoFavoriteStatusUseCase = changePhotoFavoriteStatusUseCase;
            this.deletePhotoUseCase = deletePhotoUseCase;
            this.searchPhotoUpdaterUseCase = searchPhotoUpdaterUseCase;
            this.uiScheduler = uiScheduler;
        }


        @Override public void bind(final int position, final SavedSearchRowView rowView) {
            final PhotoModel photoModel = photoModels.get(position);
            rowView.loadImage(photoModel.getFilePath());
            rowView.setFavorite(photoModel.isFavorite());
        }

        @Override public void onFavoriteClick(final int position) {
            changePhotoFavoriteState(photoModels.get(position));
        }

        @Override public void onIoActionClick(final int position) {
            final PhotoModel photoModel = photoModels.get(position);
            deletePhoto(photoModel);
        }

        private void changePhotoFavoriteState(final PhotoModel photoModel) {
            addDisposable(changePhotoFavoriteStatusUseCase
                    .execute(photoModel)
                    .observeOn(uiScheduler)
                    .subscribe(newPhotoModel -> {
                                updatePhotoModel(newPhotoModel);
                                searchPhotoUpdaterUseCase.execute();
                            },
                            throwable -> {
                                if (photoModel.isFavorite()) {
                                    mainView.showErrorDeletingFromFavoritesMessage();
                                } else {
                                    mainView.showErrorAddingToFavoritesMessage();
                                }
                            }));
        }

        private void deletePhoto(final PhotoModel photoModel) {
            addDisposable(deletePhotoUseCase.execute(photoModel)
                    .observeOn(uiScheduler)
                    .subscribe(() -> {
                                deletePhotoModel(photoModel);
                                searchPhotoUpdaterUseCase.execute();
                            },
                            throwable -> mainView.showErrorDeletingPhoto()));
        }
    }
}
