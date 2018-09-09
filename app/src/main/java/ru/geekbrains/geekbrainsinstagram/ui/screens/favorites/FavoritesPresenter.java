package ru.geekbrains.geekbrainsinstagram.ui.screens.favorites;

import javax.inject.Inject;

import ru.geekbrains.geekbrainsinstagram.base.BasePresenter;
import ru.geekbrains.geekbrainsinstagram.di.fragment.FragmentScope;
import ru.geekbrains.geekbrainsinstagram.model.PresentPhotoModel;

@FragmentScope
public final class FavoritesPresenter extends BasePresenter<IFavoritesPresenter.IView>
        implements IFavoritesPresenter {

    @Inject
    FavoritesPresenter() {
    }

    @Override
    public void deletePhotoFromFavorites(PresentPhotoModel photo) {

    }

    @Override
    public void deletePhotoFromDevice(PresentPhotoModel photo) {

    }
}
