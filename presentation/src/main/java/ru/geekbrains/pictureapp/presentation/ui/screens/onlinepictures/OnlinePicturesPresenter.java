package ru.geekbrains.pictureapp.presentation.ui.screens.onlinepictures;

import ru.geekbrains.pictureapp.presentation.ui.base.BasePresenter;
import ru.geekbrains.pictureapp.presentation.ui.base.photos.BaseListPresenter.ListView;

public interface OnlinePicturesPresenter extends BasePresenter<OnlinePicturesPresenter.OnlinePicturesView> {

    interface OnlinePicturesView extends BasePresenter.BaseView {

        void init(final OnlinePicturesListPresenter listPresenter);

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
