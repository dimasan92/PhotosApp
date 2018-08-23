package ru.geekbrains.geekbrainsinstagram.di.fragment.module;

import dagger.Module;
import dagger.Provides;
import ru.geekbrains.geekbrainsinstagram.MainApplication;
import ru.geekbrains.geekbrainsinstagram.di.fragment.FragmentScope;
import ru.geekbrains.geekbrainsinstagram.ui.cameragallery.CameraPhotoAdapter;
import ru.geekbrains.geekbrainsinstagram.ui.settings.theme.ColorThemeContract;
import ru.geekbrains.geekbrainsinstagram.ui.settings.theme.ColorThemeFragment;
import ru.geekbrains.geekbrainsinstagram.ui.settings.theme.ColorThemePresenter;

@Module
public final class FragmentModule {

    @FragmentScope
    @Provides
    ColorThemeContract.Presenter provideColorThemePresenter() {
        final ColorThemePresenter colorThemePresenter = new ColorThemePresenter();
        MainApplication.getApp().getComponentsManager()
                .getFragmentComponent().inject(colorThemePresenter);
        return colorThemePresenter;
    }

    @FragmentScope
    @Provides
    CameraPhotoAdapter provideCameraPhotoAdapter() {
        return new CameraPhotoAdapter();
    }
}
