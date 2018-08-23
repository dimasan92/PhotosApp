package ru.geekbrains.geekbrainsinstagram.di.fragment.module;

import dagger.Module;
import dagger.Provides;
import ru.geekbrains.geekbrainsinstagram.MainApplication;
import ru.geekbrains.geekbrainsinstagram.di.fragment.FragmentScope;
import ru.geekbrains.geekbrainsinstagram.ui.cameragallery.CameraGalleryContract;
import ru.geekbrains.geekbrainsinstagram.ui.cameragallery.CameraGalleryPresenter;
import ru.geekbrains.geekbrainsinstagram.ui.cameragallery.CameraPhotoAdapter;
import ru.geekbrains.geekbrainsinstagram.ui.settings.theme.AppThemePresenter;
import ru.geekbrains.geekbrainsinstagram.ui.settings.theme.IAppThemePresenter;
import ru.geekbrains.geekbrainsinstagram.utils.PictureUtils;

@Module
public final class FragmentModule {

    @FragmentScope
    @Provides
    IAppThemePresenter provideAppThemePresenter() {
        final AppThemePresenter appThemePresenter = new AppThemePresenter();
        MainApplication.getApp().getComponentsManager()
                .getFragmentComponent().inject(appThemePresenter);
        return appThemePresenter;
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
