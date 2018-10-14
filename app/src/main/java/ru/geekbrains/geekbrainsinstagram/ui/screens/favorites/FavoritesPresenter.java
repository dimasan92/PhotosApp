package ru.geekbrains.geekbrainsinstagram.ui.screens.favorites;

import ru.geekbrains.geekbrainsinstagram.base.BasePresenter;
import ru.geekbrains.geekbrainsinstagram.ui.screens.favorites.FavoritesListPresenter.FavoritesListView;

import static ru.geekbrains.geekbrainsinstagram.ui.screens.favorites.FavoritesPresenter.FavoritesView;

public interface FavoritesPresenter extends BasePresenter<FavoritesView> {

    interface FavoritesView extends BasePresenter.BaseView {

        void init(FavoritesListPresenter presenter);

        void showSuccessDeleteFromFavoritesMessage();

        void showSuccessDeleteFromDeviceMessage();

        void showErrorDeleteFromFavoritesMessage();

        void showErrorDeleteFromDeviceMessage();
    }

    void create();

    void attachListView(final FavoritesListView listView);
}
