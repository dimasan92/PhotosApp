package ru.geekbrains.geekbrainsinstagram.ui.screens.favorites;

import java.util.List;

import ru.geekbrains.geekbrainsinstagram.base.IBasePresenter;
import ru.geekbrains.geekbrainsinstagram.model.ViewPhotoModel;
import ru.geekbrains.geekbrainsinstagram.ui.common.NotifyingMessage;

public interface IFavoritesPresenter extends IBasePresenter<IFavoritesPresenter.IView> {

    interface IView extends IBasePresenter.IView {

        void addPhotos(List<ViewPhotoModel> photos);

        void deletePhoto(ViewPhotoModel photo);

        void showNotifyingMessage(NotifyingMessage message);
    }

    void deletePhotoFromFavorites(ViewPhotoModel photo);

    void deletePhotoFromDevice(ViewPhotoModel photo);
}
