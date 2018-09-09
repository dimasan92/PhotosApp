package ru.geekbrains.geekbrainsinstagram.ui.screens.photodetails;

import javax.inject.Inject;

import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.base.BasePresenter;
import ru.geekbrains.geekbrainsinstagram.di.fragment.FragmentScope;

@FragmentScope
public final class PhotoDetailsPresenter extends BasePresenter<IPhotoDetailsPresenter.IView>
        implements IPhotoDetailsPresenter {

    @Inject
    PhotoDetailsPresenter() {
    }

    @Override
    public void start() {
        view.showNotifyingMessage(R.string.error_load_photo_message);
    }

    @Override
    public void start(String photoId) {

    }
}
