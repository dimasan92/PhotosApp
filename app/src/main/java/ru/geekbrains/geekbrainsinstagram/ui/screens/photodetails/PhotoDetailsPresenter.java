package ru.geekbrains.geekbrainsinstagram.ui.screens.photodetails;

import javax.inject.Inject;

import ru.geekbrains.geekbrainsinstagram.base.BasePresenter;
import ru.geekbrains.geekbrainsinstagram.di.fragment.FragmentScope;
import ru.geekbrains.geekbrainsinstagram.model.ViewPhotoModel;
import ru.geekbrains.geekbrainsinstagram.ui.common.NotifyingMessage;

@FragmentScope
public final class PhotoDetailsPresenter extends BasePresenter<IPhotoDetailsPresenter.IView>
        implements IPhotoDetailsPresenter {

    @Inject
    PhotoDetailsPresenter() {
    }

    @Override
    public void start() {
        view.showNotifyingMessage(NotifyingMessage.ERROR_LOAD_PHOTO);
    }

    @Override
    public void start(String photoId) {
        ViewPhotoModel photo = new ViewPhotoModel(photoId);
        view.loadPhoto(photo);
    }
}
