package ru.geekbrains.geekbrainsinstagram.ui.screens.personalphotos;

import android.content.Intent;

import java.util.List;

import androidx.annotation.StringRes;
import ru.geekbrains.geekbrainsinstagram.base.IBasePresenter;
import ru.geekbrains.geekbrainsinstagram.model.PhotoModel;

public interface IPersonalPhotosPresenter extends IBasePresenter<IPersonalPhotosPresenter.IView> {

    interface IView extends IBasePresenter.IView {

        void startCamera(Intent cameraIntent);

        void updatePhotos(List<PhotoModel> photos);

        void showNotifyingMessage(@StringRes int message);
    }

    void takeAPhoto();

    void photoHasTaken();

    void photoHasCanceled();

    void changePhotoFavorite(PhotoModel photoModel);

    void deletePhoto(PhotoModel photoModel);
}
