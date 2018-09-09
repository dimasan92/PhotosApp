package ru.geekbrains.geekbrainsinstagram.ui.screens.favorites;

import java.util.List;

import androidx.annotation.StringRes;
import ru.geekbrains.geekbrainsinstagram.base.IBasePresenter;
import ru.geekbrains.geekbrainsinstagram.model.PresentPhotoModel;

public interface IFavoritesPresenter extends IBasePresenter<IFavoritesPresenter.IView> {

    interface IView extends IBasePresenter.IView {

        void addPhotos(List<PresentPhotoModel> photos);

        void deletePhoto(PresentPhotoModel photo);

        void showNotifyingMessage(@StringRes int message);
    }

    void deletePhotoFromFavorites(PresentPhotoModel photo);

    void deletePhotoFromDevice(PresentPhotoModel photo);
}
