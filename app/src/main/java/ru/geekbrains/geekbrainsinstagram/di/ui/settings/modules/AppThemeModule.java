package ru.geekbrains.geekbrainsinstagram.di.ui.settings.modules;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.geekbrainsinstagram.di.ui.settings.SettingsScope;
import ru.geekbrains.geekbrainsinstagram.ui.screens.theme.AppThemePresenter;
import ru.geekbrains.geekbrainsinstagram.ui.screens.theme.AppThemePresenterImpl;

@Module
public interface AppThemeModule {

    @SettingsScope @Binds AppThemePresenter provideAppThemePresenter(final AppThemePresenterImpl presenter);
}
