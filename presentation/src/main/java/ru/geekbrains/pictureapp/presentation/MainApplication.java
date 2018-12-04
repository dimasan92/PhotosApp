package ru.geekbrains.pictureapp.presentation;

import android.app.Application;

import ru.geekbrains.pictureapp.presentation.di.application.ComponentsManager;

public final class MainApplication extends Application {

    private static MainApplication application;
    private ComponentsManager componentsManager;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        componentsManager = new ComponentsManager(getApplicationContext());
    }

    public static MainApplication getApp() {
        return application;
    }

    public ComponentsManager getComponentsManager() {
        return componentsManager;
    }
}
