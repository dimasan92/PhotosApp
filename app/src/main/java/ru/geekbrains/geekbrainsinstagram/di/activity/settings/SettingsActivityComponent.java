package ru.geekbrains.geekbrainsinstagram.di.activity.settings;

import dagger.Subcomponent;
import ru.geekbrains.geekbrainsinstagram.di.activity.ActivityComponent;
import ru.geekbrains.geekbrainsinstagram.di.fragment.apptheme.AppThemeFragmentComponent;
import ru.geekbrains.geekbrainsinstagram.ui.containers.settings.SettingsActivity;

@SettingsActivityScope
@Subcomponent(modules = SettingsActivityModule.class)
public interface SettingsActivityComponent extends ActivityComponent<SettingsActivity> {

    AppThemeFragmentComponent getAppThemeFragmentComponent();
}
