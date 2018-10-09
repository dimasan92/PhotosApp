package ru.geekbrains.geekbrainsinstagram.di.fragment.apptheme;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.geekbrainsinstagram.ui.screens.theme.AppThemePresenter;
import ru.geekbrains.geekbrainsinstagram.ui.screens.theme.AppThemePresenterImpl;

@Module
public interface AppThemeFragmentModule {

    @AppThemeFragmentScope
    @Binds
    AppThemePresenter provideAppThemePresenter(AppThemePresenterImpl presenter);
}
