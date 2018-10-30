package ru.geekbrains.geekbrainsinstagram.ui.screens.savedsearch;

import ru.geekbrains.geekbrainsinstagram.ui.base.BasePresenter;
import ru.geekbrains.geekbrainsinstagram.ui.base.photos.BaseListPresenter.ListView;

public interface SavedSearchPresenter extends BasePresenter<SavedSearchPresenter.SavedSearchView> {

    interface SavedSearchView extends BasePresenter.BaseView {

        void init(final SavedSearchListPresenter presenter);

        void showErrorAddingToFavoritesMessage();

        void showErrorDeletingFromFavoritesMessage();

        void showErrorDeletingPhoto();

    }

    void userVisibleHint();

    void create();

    void attachListView(final ListView listView);
}
