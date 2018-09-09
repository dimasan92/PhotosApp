package ru.geekbrains.geekbrainsinstagram.ui.screens.photodetails;

import javax.inject.Inject;

import ru.geekbrains.geekbrainsinstagram.base.BasePresenter;
import ru.geekbrains.geekbrainsinstagram.di.fragment.FragmentScope;

@FragmentScope
public class PhotoDetailsPresenter extends BasePresenter<IPhotoDetailsPresenter.IView>
        implements IPhotoDetailsPresenter {

    @Inject
    public PhotoDetailsPresenter() {
    }
}
