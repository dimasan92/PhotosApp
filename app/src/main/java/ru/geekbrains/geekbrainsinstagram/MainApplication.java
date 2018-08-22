package ru.geekbrains.geekbrainsinstagram;

import android.app.Application;

import ru.geekbrains.geekbrainsinstagram.di.ComponentsManager;

public final class MainApplication extends Application {

    private static MainApplication application;
    private ComponentsManager componentsManager;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        componentsManager = new ComponentsManager();
    }

    public static MainApplication getApp() {
        return application;
    }

    public ComponentsManager getComponentsManager() {
        return componentsManager;
    }
}
