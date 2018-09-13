package ru.geekbrains.geekbrainsinstagram.ui.screens.favorites;

import java.util.List;

import ru.geekbrains.geekbrainsinstagram.base.IBasePresenter;
import ru.geekbrains.geekbrainsinstagram.model.PresentPhotoModel;
import ru.geekbrains.geekbrainsinstagram.ui.common.NotifyingMessage;

public interface IFavoritesPresenter extends IBasePresenter<IFavoritesPresenter.IView> {

    interface IView extends IBasePresenter.IView {

        void addPhotos(List<PresentPhotoModel> photos);

        void deletePhoto(PresentPhotoModel photo);

        void showNotifyingMessage(NotifyingMessage message);
    }

    void deletePhotoFromFavorites(PresentPhotoModel photo);

    void deletePhotoFromDevice(PresentPhotoModel photo);
}
