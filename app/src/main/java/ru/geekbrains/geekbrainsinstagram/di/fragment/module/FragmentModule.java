package ru.geekbrains.geekbrainsinstagram.di.fragment.module;

import dagger.Module;
import dagger.Provides;
import ru.geekbrains.geekbrainsinstagram.MainApplication;
import ru.geekbrains.geekbrainsinstagram.di.fragment.FragmentScope;
import ru.geekbrains.geekbrainsinstagram.ui.cameragallery.CameraGalleryContract;
import ru.geekbrains.geekbrainsinstagram.ui.cameragallery.CameraGalleryPresenter;
import ru.geekbrains.geekbrainsinstagram.ui.cameragallery.CameraPhotoAdapter;
import ru.geekbrains.geekbrainsinstagram.ui.settings.theme.ColorThemeContract;
import ru.geekbrains.geekbrainsinstagram.ui.settings.theme.ColorThemeFragment;
import ru.geekbrains.geekbrainsinstagram.ui.settings.theme.ColorThemePresenter;
import ru.geekbrains.geekbrainsinstagram.utils.PictureUtils;

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
    CameraGalleryContract.Presenter provideCameraGalleryPresenter() {
        final CameraGalleryPresenter presenter = new CameraGalleryPresenter();
        MainApplication.getApp().getComponentsManager()
                .getFragmentComponent().inject(presenter);
        return presenter;
    }

    @FragmentScope
    @Provides
    CameraPhotoAdapter provideCameraPhotoAdapter(PictureUtils pictureUtils) {
        return new CameraPhotoAdapter(pictureUtils);
    }
}
