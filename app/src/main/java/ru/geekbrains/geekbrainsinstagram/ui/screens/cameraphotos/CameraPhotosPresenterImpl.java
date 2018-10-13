package ru.geekbrains.geekbrainsinstagram.ui.screens.cameraphotos;

import javax.inject.Inject;

import ru.geekbrains.geekbrainsinstagram.base.BasePresenterImpl;
import ru.geekbrains.geekbrainsinstagram.di.fragment.cameraphotos.CameraPhotosFragmentScope;

@CameraPhotosFragmentScope
public final class CameraPhotosPresenterImpl extends BasePresenterImpl<CameraPhotosPresenter.View>
        implements CameraPhotosPresenter {

    @Inject
    CameraPhotosPresenterImpl() {
    }
}
