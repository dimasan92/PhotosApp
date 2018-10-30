package ru.geekbrains.geekbrainsinstagram.di.application;

import android.content.Context;

import ru.geekbrains.geekbrainsinstagram.di.ui.MainComponent;
import ru.geekbrains.geekbrainsinstagram.di.ui.home.HomeComponent;
import ru.geekbrains.geekbrainsinstagram.di.ui.settings.SettingsComponent;

public final class ComponentsManager {

    private ApplicationComponent applicationComponent;
    private MainComponent mainComponent;
    private HomeComponent homeComponent;
    private SettingsComponent settingsComponent;

    public ComponentsManager(Context context) {
        initApplicationComponent(context);
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

    public void releaseMainComponent() {
        mainComponent = null;
        releaseHomeComponent();
        releaseSettingsComponent();
    }

    private void releaseHomeComponent() {
        homeComponent = null;
    }

    public void releaseSettingsComponent() {
        settingsComponent = null;
    }

    private void initApplicationComponent(Context context) {
        applicationComponent = DaggerApplicationComponent.builder()
                .setContext(context)
                .build();
    }
}
