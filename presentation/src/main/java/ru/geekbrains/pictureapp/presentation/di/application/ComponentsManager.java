package ru.geekbrains.pictureapp.presentation.di.application;

import android.content.Context;

import ru.geekbrains.pictureapp.presentation.di.ui.MainComponent;
import ru.geekbrains.pictureapp.presentation.di.ui.details.DetailsComponent;
import ru.geekbrains.pictureapp.presentation.di.ui.home.HomeComponent;
import ru.geekbrains.pictureapp.presentation.di.ui.settings.SettingsComponent;

public final class ComponentsManager {

    private ApplicationComponent applicationComponent;
    private MainComponent mainComponent;
    private HomeComponent homeComponent;
    private SettingsComponent settingsComponent;
    private DetailsComponent detailsComponent;

    public ComponentsManager(final Context context) {
        initApplicationComponent(context);
    }

    private void initApplicationComponent(final Context context) {
        applicationComponent = DaggerApplicationComponent.builder()
                .setContext(context)
                .build();
    }

    public MainComponent getMainComponent() {
        if (mainComponent == null) {
            mainComponent = applicationComponent.getMainComponent();
        }
        return mainComponent;
    }

    public HomeComponent getHomeComponent() {
        if (homeComponent == null) {
            homeComponent = getMainComponent().getHomeComponent();
        }
        return homeComponent;
    }

    public SettingsComponent getSettingsComponent() {
        if (settingsComponent == null) {
            settingsComponent = getMainComponent().getSettingsComponent();
        }
        return settingsComponent;
    }

    public DetailsComponent getDetailsComponent() {
        if (detailsComponent == null) {
            detailsComponent = getMainComponent().getDetailsComponent();
        }
        return detailsComponent;
    }

    public void releaseMainComponent() {
        mainComponent = null;
        releaseHomeComponent();
        releaseSettingsComponent();
        releaseDetailsComponent();
    }

    private void releaseHomeComponent() {
        homeComponent = null;
    }

    public void releaseSettingsComponent() {
        settingsComponent = null;
    }

    public void releaseDetailsComponent() {
        detailsComponent = null;
    }
}
