package ru.geekbrains.pictureapp.presentation.di.ui;

import dagger.Subcomponent;
import ru.geekbrains.pictureapp.presentation.di.ui.details.DetailsComponent;
import ru.geekbrains.pictureapp.presentation.di.ui.home.HomeComponent;
import ru.geekbrains.pictureapp.presentation.di.ui.settings.SettingsComponent;
import ru.geekbrains.pictureapp.presentation.ui.container.MainActivity;

@MainScope
@Subcomponent(modules = MainModule.class)
public interface MainComponent {

    HomeComponent getHomeComponent();

    SettingsComponent getSettingsComponent();

    DetailsComponent getDetailsComponent();

    void inject(final MainActivity mainActivity);
}
