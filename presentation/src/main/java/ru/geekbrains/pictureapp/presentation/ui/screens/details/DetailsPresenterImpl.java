package ru.geekbrains.pictureapp.presentation.ui.screens.details;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import ru.geekbrains.pictureapp.domain.interactor.photos.common.DeleteImageUseCase;
import ru.geekbrains.pictureapp.domain.interactor.photos.favorites.ChangeFavoriteStatusUseCase;
import ru.geekbrains.pictureapp.domain.model.ImageModel;
import ru.geekbrains.pictureapp.presentation.di.ui.details.DetailsScope;
import ru.geekbrains.pictureapp.presentation.ui.base.BasePresenterImpl;
import ru.geekbrains.pictureapp.presentation.ui.base.photos.BaseListPresenterImpl;
import ru.geekbrains.pictureapp.presentation.ui.navigator.MainNavigator;
import ru.geekbrains.pictureapp.presentation.ui.updater.Updater;
import ru.geekbrains.pictureapp.presentation.util.ParseUtils;

import static ru.geekbrains.pictureapp.presentation.ui.base.photos.BaseListPresenter.ListView;
import static ru.geekbrains.pictureapp.presentation.ui.base.photos.BaseListPresenter.RowView;

@DetailsScope
public final class DetailsPresenterImpl extends BasePresenterImpl<DetailsPresenter.DetailsView>
        implements DetailsPresenter {

    private final MainNavigator mainNavigator;
    private final DetailsListPresenterImpl listPresenter;
    private final ParseUtils parseUtils;

    @Inject
    DetailsPresenterImpl(final MainNavigator mainNavigator,
                         final DetailsListPresenterImpl listPresenter, final ParseUtils parseUtils) {
        this.mainNavigator = mainNavigator;
        this.listPresenter = listPresenter;
        this.parseUtils = parseUtils;
    }

    @Override
    public void create(final String[] photos, final int initPosition) {
        view.init(listPresenter);
        listPresenter.setImageModels(parseUtils.parseToObjects(photos));
    }

    @Override
    public void attachListView(final ListView listView) {
        listPresenter.attachView(view, listView);
    }

    @Override
    public void stop() {
        super.stop();
        listPresenter.detachView();
    }

    @Override
    public void back() {
        view.release();
        mainNavigator.navigateBack();
    }

    @Override
    public void onFavoriteClick(final int position) {
        listPresenter.changeImageFavoriteState(position);
    }

    @Override
    public void onDeleteClick(final int position) {
        view.showDeleteDialog(position);
    }

    @Override
    public void deleteConfirm(int position) {
        listPresenter.deleteImage(position);
    }

    @DetailsScope
    static class DetailsListPresenterImpl extends BaseListPresenterImpl<DetailsView, RowView>
            implements DetailsListPresenter {

        private final ChangeFavoriteStatusUseCase changeFavoriteStatusUseCase;
        private final DeleteImageUseCase deleteImageUseCase;
        private final Updater updater;
        private final Scheduler uiScheduler;

        @Inject
        DetailsListPresenterImpl(final ChangeFavoriteStatusUseCase changeFavoriteStatusUseCase,
                                 final DeleteImageUseCase deleteImageUseCase,
                                 final Updater updater, final Scheduler uiScheduler) {
            this.changeFavoriteStatusUseCase = changeFavoriteStatusUseCase;
            this.deleteImageUseCase = deleteImageUseCase;
            this.updater = updater;
            this.uiScheduler = uiScheduler;
        }

        @Override
        public void bind(final int position, final RowView view) {
            final ImageModel imageModel = imageModels.get(position);
            view.loadImage(imageModel);
            mainView.setFavorite(imageModel.isFavorite());
        }

        void changeImageFavoriteState(final int position) {
            final ImageModel imageModel = imageModels.get(position);
            addDisposable(changeFavoriteStatusUseCase.execute(imageModel)
                    .observeOn(uiScheduler)
                    .subscribe(newImageModel -> {
                                updateImageModel(newImageModel);
                                updater.update(newImageModel);
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

        void deleteImage(final int position) {
            final ImageModel imageModel = imageModels.get(position);
            addDisposable(deleteImageUseCase.execute(imageModel)
                    .observeOn(uiScheduler)
                    .subscribe(() -> {
                                deleteImageModel(imageModel);
                                updater.update(imageModel);
                            },
                            throwable -> mainView.showErrorDeletingMessage()));
        }
    }
}
