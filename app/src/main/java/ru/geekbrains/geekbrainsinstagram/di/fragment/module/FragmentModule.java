package ru.geekbrains.geekbrainsinstagram.di.fragment.module;

import dagger.Module;
import dagger.Provides;
import ru.geekbrains.geekbrainsinstagram.MainApplication;
import ru.geekbrains.geekbrainsinstagram.di.fragment.FragmentScope;
import ru.geekbrains.geekbrainsinstagram.ui.screens.personalphotos.IPersonalPhotosPresenter;
import ru.geekbrains.geekbrainsinstagram.ui.screens.personalphotos.PersonalPhotosAdapter;
import ru.geekbrains.geekbrainsinstagram.ui.screens.personalphotos.PersonalPhotosPresenter;
import ru.geekbrains.geekbrainsinstagram.ui.screens.theme.AppThemePresenter;
import ru.geekbrains.geekbrainsinstagram.ui.screens.theme.IAppThemePresenter;
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
    IPersonalPhotosPresenter providePersonalPhotosPresenter() {
        final PersonalPhotosPresenter presenter = new PersonalPhotosPresenter();
        MainApplication.getApp().getComponentsManager()
                .getFragmentComponent().inject(presenter);
        return presenter;
    }

    @FragmentScope
    @Provides
    PersonalPhotosAdapter provideCameraPhotoAdapter(PictureUtils pictureUtils) {
        return new PersonalPhotosAdapter(pictureUtils);
    }
}
