package ru.geekbrains.geekbrainsinstagram.ui.screens.personalphotos;

import java.util.List;

import ru.geekbrains.geekbrainsinstagram.base.IBasePresenter;
import ru.geekbrains.geekbrainsinstagram.model.PresentPhotoModel;
import ru.geekbrains.geekbrainsinstagram.ui.common.NotifyingMessage;

public interface IPersonalPhotosPresenter extends IBasePresenter<IPersonalPhotosPresenter.IView> {

    interface IView extends IBasePresenter.IView {

        void startCamera(PresentPhotoModel photo);

        void addPhotos(List<PresentPhotoModel> photos);

        void addNewPhoto(PresentPhotoModel photo);

        void updatePhoto(PresentPhotoModel photo);

        void deletePhoto(PresentPhotoModel photo);

        void showNotifyingMessage(NotifyingMessage message);

        void showDeletePhotoDialog(PresentPhotoModel photo);
    }

    void takeAPhotoRequest();

    void cameraCannotLaunch();

    void cameraHasClosed(int resultCode);

    void changePhotoFavoriteState(PresentPhotoModel photo);

    void deletePhoto(PresentPhotoModel photo);

    void deleteRequest(PresentPhotoModel photo);
}
