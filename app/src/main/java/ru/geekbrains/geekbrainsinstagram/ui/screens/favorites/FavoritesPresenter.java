package ru.geekbrains.geekbrainsinstagram.ui.screens.favorites;

import ru.geekbrains.geekbrainsinstagram.ui.base.BasePresenter;
import ru.geekbrains.geekbrainsinstagram.ui.base.photos.BaseListPresenter.ListView;

import static ru.geekbrains.geekbrainsinstagram.ui.screens.favorites.FavoritesPresenter.FavoritesView;

public interface FavoritesPresenter extends BasePresenter<FavoritesView> {

    interface FavoritesView extends BasePresenter.BaseView {

        void init(final FavoritesListPresenter listPresenter);

        void showSuccessDeletedFromFavoritesMessage();

        void showSuccessDeletedFromDeviceMessage();

        void showErrorDeletingFromFavoritesMessage();

        void showErrorDeletingFromDeviceMessage();
    }

    void create();

    void attachListView(final ListView listView);
}
