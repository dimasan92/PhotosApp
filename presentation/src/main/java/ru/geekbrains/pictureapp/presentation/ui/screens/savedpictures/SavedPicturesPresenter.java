package ru.geekbrains.pictureapp.presentation.ui.screens.savedpictures;

import ru.geekbrains.pictureapp.presentation.ui.base.BasePresenter;
import ru.geekbrains.pictureapp.presentation.ui.base.photos.BaseListPresenter.ListView;

import static ru.geekbrains.pictureapp.presentation.ui.screens.savedpictures.SavedPicturesPresenter.SavedPicturesView;

public interface SavedPicturesPresenter extends BasePresenter<SavedPicturesView> {

    interface SavedPicturesView extends BasePresenter.BaseView {

        void init(final SavedPicturesListPresenter presenter);

        void showErrorAddingToFavoritesMessage();

        void showErrorDeletingFromFavoritesMessage();

        void showErrorDeletingPhoto();
    }

    void userVisibleHint();

    void create();

    void attachListView(final ListView listView);
}
