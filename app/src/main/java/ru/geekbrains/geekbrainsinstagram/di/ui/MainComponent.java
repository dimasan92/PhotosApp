package ru.geekbrains.geekbrainsinstagram.di.ui;

import dagger.Subcomponent;
import ru.geekbrains.geekbrainsinstagram.di.ui.home.HomeComponent;
import ru.geekbrains.geekbrainsinstagram.di.ui.settings.SettingsComponent;
import ru.geekbrains.geekbrainsinstagram.ui.container.MainActivity;

@MainScope
@Subcomponent(modules = MainModule.class)
public interface MainComponent {

    HomeComponent getHomeComponent();

    SettingsComponent getSettingsComponent();

    void inject(MainActivity mainActivity);
}
