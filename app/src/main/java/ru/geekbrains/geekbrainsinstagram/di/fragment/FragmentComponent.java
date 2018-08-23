package ru.geekbrains.geekbrainsinstagram.di.fragment;

import dagger.Subcomponent;
import ru.geekbrains.geekbrainsinstagram.di.fragment.module.FragmentModule;
import ru.geekbrains.geekbrainsinstagram.ui.cameragallery.CameraGalleryFragment;
import ru.geekbrains.geekbrainsinstagram.ui.cameragallery.CameraGalleryPresenter;
import ru.geekbrains.geekbrainsinstagram.ui.settings.theme.ColorThemeFragment;
import ru.geekbrains.geekbrainsinstagram.ui.settings.theme.ColorThemePresenter;

@FragmentScope
@Subcomponent(modules = {FragmentModule.class})
public interface FragmentComponent {

    void inject(ColorThemeFragment view);

    void inject(ColorThemePresenter presenter);

    void inject(CameraGalleryFragment view);

    void inject(CameraGalleryPresenter presenter);
}
