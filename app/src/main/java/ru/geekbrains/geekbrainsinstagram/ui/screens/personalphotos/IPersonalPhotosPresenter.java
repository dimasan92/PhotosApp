package ru.geekbrains.geekbrainsinstagram.ui.screens.personalphotos;

import java.util.List;

import ru.geekbrains.geekbrainsinstagram.base.IBasePresenter;
import ru.geekbrains.geekbrainsinstagram.model.ViewPhotoModel;
import ru.geekbrains.geekbrainsinstagram.ui.common.NotifyingMessage;

public interface IPersonalPhotosPresenter extends IBasePresenter<IPersonalPhotosPresenter.IView> {

    interface IView extends IBasePresenter.IView {

        void startCamera(ViewPhotoModel photo);

        void addPhotos(List<ViewPhotoModel> photos);

        void addNewPhoto(ViewPhotoModel photo);

        void updatePhoto(ViewPhotoModel photo);

        void deletePhoto(ViewPhotoModel photo);

        void showNotifyingMessage(NotifyingMessage message);

        void showDeletePhotoDialog(ViewPhotoModel photo);
    }

    void takeAPhotoRequest();

    void cameraCannotLaunch();

    void cameraHasClosed(int resultCode);

    void changePhotoFavoriteState(ViewPhotoModel photo);

    void deletePhoto(ViewPhotoModel photo);

    void deleteRequest(ViewPhotoModel photo);
}
