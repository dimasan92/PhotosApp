package ru.geekbrains.geekbrainsinstagram.ui.screens.personalphotos;

import java.util.List;

import androidx.annotation.StringRes;
import ru.geekbrains.geekbrainsinstagram.base.IBasePresenter;
import ru.geekbrains.geekbrainsinstagram.model.PresentPhotoModel;

public interface IPersonalPhotosPresenter extends IBasePresenter<IPersonalPhotosPresenter.IView> {

    interface IView extends IBasePresenter.IView {

        void startCamera(PresentPhotoModel photo);

        void addPhotos(List<PresentPhotoModel> photos);

        void addNewPhoto(PresentPhotoModel photo);

        void updatePhoto(PresentPhotoModel photo);

        void deletePhoto(PresentPhotoModel photo);

        void showNotifyingMessage(int message);

        void showDeletePhotoDialog(PresentPhotoModel photo);
    }

    void takeAPhotoRequest();

    void cameraCannotLaunch();

    void photoHasTaken();

    void photoHasCanceled();

    void changePhotoFavoriteState(PresentPhotoModel photo);

    void deletePhoto(PresentPhotoModel photo);

    void deleteRequest(PresentPhotoModel photo);
}
