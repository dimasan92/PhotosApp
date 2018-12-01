package ru.geekbrains.pictureapp.presentation.ui.screens.favorites;

import ru.geekbrains.pictureapp.presentation.ui.base.BasePresenter;
import ru.geekbrains.pictureapp.presentation.ui.base.photos.BaseListPresenter.ListView;

import static ru.geekbrains.pictureapp.presentation.ui.screens.favorites.FavoritesPresenter.FavoritesView;

public interface FavoritesPresenter extends BasePresenter<FavoritesView> {

    interface FavoritesView extends BasePresenter.BaseView {

        void init(final FavoritesListPresenter listPresenter);

        void showErrorDeletingFromFavoritesMessage();

        void showErrorDeletingFromDeviceMessage();
    }

    void create();

    void attachListView(final ListView listView);
}
