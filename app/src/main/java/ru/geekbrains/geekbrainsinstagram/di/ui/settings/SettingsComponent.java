package ru.geekbrains.geekbrainsinstagram.di.ui.settings;

import dagger.Subcomponent;
import ru.geekbrains.geekbrainsinstagram.di.ui.settings.modules.AppThemeModule;
import ru.geekbrains.geekbrainsinstagram.ui.screens.theme.AppThemeFragment;

@SettingsScope
@Subcomponent(modules = AppThemeModule.class)
public interface SettingsComponent {

    void inject(final AppThemeFragment appThemeFragment);
}
