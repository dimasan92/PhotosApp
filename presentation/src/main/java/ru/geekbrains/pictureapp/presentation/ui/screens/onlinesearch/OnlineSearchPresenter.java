package ru.geekbrains.pictureapp.presentation.ui.screens.onlinesearch;

import ru.geekbrains.pictureapp.presentation.ui.base.BasePresenter;
import ru.geekbrains.pictureapp.presentation.ui.base.photos.BaseListPresenter.ListView;

public interface OnlineSearchPresenter extends BasePresenter<OnlineSearchPresenter.OnlineSearchView> {

    interface OnlineSearchView extends BasePresenter.BaseView {

        void init(final OnlineSearchListPresenter listPresenter);

        void showErrorNetworkMessage();

        void showErrorDownloadingPhotosMessage();

        void showErrorAddingToFavoritesMessage();

        void showErrorDeletingFromFavoritesMessage();

        void showErrorSavingPhoto();

        void showErrorDeletingPhoto();

    }

    void userVisibleHint();

    void create();

    void attachListView(final ListView listView);

    void onSearchClick(final String query);
}