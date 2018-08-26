package ru.geekbrains.geekbrainsinstagram.ui.screens.personalphotos;

import android.content.Intent;

import java.util.List;

import androidx.annotation.StringRes;
import io.reactivex.Observable;
import ru.geekbrains.geekbrainsinstagram.base.IBasePresenter;
import ru.geekbrains.geekbrainsinstagram.model.PhotoModel;

public interface IPersonalPhotosPresenter extends IBasePresenter<IPersonalPhotosPresenter.IView> {

    interface IView extends IBasePresenter.IView {

        void startCamera(Intent cameraIntent);

        void addPhotos(List<PhotoModel> photos);

        void addNewPhoto(PhotoModel photoModel);

        void updatePhoto(PhotoModel photoModel);

        void deletePhoto(PhotoModel photoModel);

        void showNotifyingMessage(@StringRes int message);

        void showDeletePhotoDialog(PhotoModel photoModel);
    }

    void takeAPhoto();

    void photoHasTaken();

    void photoHasCanceled();

    void changePhotoFavoriteState(Observable<PhotoModel> favoritesObservable);

    void deletePhoto(PhotoModel photoModel);

    void deleteRequest(Observable<PhotoModel> deleteObservable);
}
