package ru.geekbrains.pictureapp.presentation.ui.screens.favorites;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import ru.geekbrains.pictureapp.domain.interactor.photos.common.DeleteImageUseCase;
import ru.geekbrains.pictureapp.domain.interactor.photos.favorites.ChangeFavoriteStatusUseCase;
import ru.geekbrains.pictureapp.domain.interactor.photos.favorites.GetFavoritesUseCase;
import ru.geekbrains.pictureapp.domain.model.ImageModel;
import ru.geekbrains.pictureapp.presentation.di.ui.home.HomeScope;
import ru.geekbrains.pictureapp.presentation.ui.base.BasePresenterImpl;
import ru.geekbrains.pictureapp.presentation.ui.base.photos.BaseListPresenter.ListView;
import ru.geekbrains.pictureapp.presentation.ui.base.photos.BaseListPresenter.RowView;
import ru.geekbrains.pictureapp.presentation.ui.base.photos.BaseListPresenterImpl;
import ru.geekbrains.pictureapp.presentation.ui.navigator.MainNavigator;
import ru.geekbrains.pictureapp.presentation.ui.updater.Updater;
import ru.geekbrains.pictureapp.presentation.util.ParseUtils;

import static ru.geekbrains.pictureapp.presentation.ui.screens.favorites.FavoritesPresenter.FavoritesView;

@HomeScope
public final class FavoritesPresenterImpl extends BasePresenterImpl<FavoritesView>
        implements FavoritesPresenter {

    private final GetFavoritesUseCase getFavoritesUseCase;
    private final Updater updater;

    private final Scheduler uiScheduler;
    private final FavoritesListPresenterImpl listPresenter;
    private boolean wasPhotosLoad;

    @Inject
    FavoritesPresenterImpl(final GetFavoritesUseCase getFavoritesUseCase,
                           final Updater updater, final FavoritesListPresenterImpl listPresenter,
                           final Scheduler uiScheduler) {
        this.getFavoritesUseCase = getFavoritesUseCase;
        this.updater = updater;
        this.listPresenter = listPresenter;
        this.uiScheduler = uiScheduler;
    }

    @Override
    public void create() {
        view.init(listPresenter);
        updater.subscribe(update -> wasPhotosLoad = false);
    }

    @Override
    public void start() {
        if (!wasPhotosLoad) {
            uploadPhotos();
        }
    }

    private void uploadPhotos() {
        addDisposable(getFavoritesUseCase.execute()
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
    public void attachListView(final ListView listView) {
        listPresenter.attachView(view, listView);
    }

    @HomeScope
    public static class FavoritesListPresenterImpl extends BaseListPresenterImpl<FavoritesView, RowView>
            implements FavoritesListPresenter {

        private final ChangeFavoriteStatusUseCase changeFavoriteStatusUseCase;
        private final DeleteImageUseCase deleteImageUseCase;
        private final Updater updater;
        private final Scheduler uiScheduler;
        private final MainNavigator mainNavigator;
        private final ParseUtils parseUtils;

        @Inject
        FavoritesListPresenterImpl(final ChangeFavoriteStatusUseCase changeFavoriteStatusUseCase,
                                   final DeleteImageUseCase deleteImageUseCase,
                                   final Updater updater, final Scheduler uiScheduler, final
                                   MainNavigator mainNavigator, final ParseUtils parseUtils) {
            this.changeFavoriteStatusUseCase = changeFavoriteStatusUseCase;
            this.deleteImageUseCase = deleteImageUseCase;
            this.updater = updater;
            this.uiScheduler = uiScheduler;
            this.mainNavigator = mainNavigator;
            this.parseUtils = parseUtils;
        }


        @Override
        public void bind(final int position, final RowView view) {
            final ImageModel imageModel = imageModels.get(position);
            view.loadImage(imageModel);
        }

        @Override
        public void onDeleteFromFavoritesClick(final int position) {
            changePhotoFavoriteState(imageModels.get(position));
        }

        private void changePhotoFavoriteState(final ImageModel imageModel) {
            addDisposable(changeFavoriteStatusUseCase
                    .execute(imageModel)
                    .observeOn(uiScheduler)
                    .subscribe(newPhotoModel -> {
                                deleteImageModel(imageModel);
                                updater.update(imageModel);
                            },
                            throwable -> mainView.showErrorDeletingFromFavoritesMessage()));
        }

        @Override
        public void onDeleteFromDeviceClick(final int position) {
            deletePhotoFromDevice(imageModels.get(position));
        }

        private void deletePhotoFromDevice(final ImageModel imageModel) {
            addDisposable(deleteImageUseCase.execute(imageModel)
                    .observeOn(uiScheduler)
                    .subscribe(() -> {
                                deleteImageModel(imageModel);
                                updater.update(imageModel);
                            },
                            throwable -> mainView.showErrorDeletingFromDeviceMessage()));
        }

        @Override
        public void onFullClick(final int position) {
            final String[] jsons = parseUtils.parseObjects(imageModels);
            mainNavigator.navigateToDetails(jsons, position);
        }
    }
}
