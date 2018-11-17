package ru.geekbrains.pictureapp.presentation.di.ui.settings;

import dagger.Subcomponent;
import ru.geekbrains.pictureapp.presentation.di.ui.settings.modules.AppThemeModule;
import ru.geekbrains.pictureapp.presentation.ui.screens.theme.AppThemeFragment;

@SettingsScope
@Subcomponent(modules = AppThemeModule.class)
public interface SettingsComponent {

    void inject(final AppThemeFragment appThemeFragment);
}
