package ru.geekbrains.pictureapp.presentation.di.ui.settings.modules;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.pictureapp.presentation.di.ui.settings.SettingsScope;
import ru.geekbrains.pictureapp.presentation.ui.screens.theme.AppThemePresenter;
import ru.geekbrains.pictureapp.presentation.ui.screens.theme.AppThemePresenterImpl;

@Module
public interface AppThemeModule {

    @SettingsScope @Binds AppThemePresenter provideAppThemePresenter(final AppThemePresenterImpl presenter);
}
