package ru.geekbrains.geekbrainsinstagram.di.application;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import ru.geekbrains.geekbrainsinstagram.di.activity.ActivityComponent;
import ru.geekbrains.geekbrainsinstagram.di.activity.main.MainActivityComponent;
import ru.geekbrains.geekbrainsinstagram.di.activity.settings.SettingsActivityComponent;
import ru.geekbrains.geekbrainsinstagram.di.fragment.FragmentComponent;
import ru.geekbrains.geekbrainsinstagram.ui.containers.main.MainActivity;
import ru.geekbrains.geekbrainsinstagram.ui.containers.settings.SettingsActivity;
import ru.geekbrains.geekbrainsinstagram.ui.screens.cameraphotos.CameraPhotosFragment;
import ru.geekbrains.geekbrainsinstagram.ui.screens.favorites.FavoritesFragment;
import ru.geekbrains.geekbrainsinstagram.ui.screens.theme.AppThemeFragment;

public final class ComponentsManager {

    private ApplicationComponent applicationComponent;
    private Map<Class<?>, ActivityComponent> activityComponents;
    private Map<Class<?>, FragmentComponent> fragmentComponents;

    public ComponentsManager(Context context) {
        initApplicationComponent(context);
        activityComponents = new HashMap<>();
        fragmentComponents = new HashMap<>();
    }

    public ActivityComponent getActivityComponent(Class<?> clazz) {
        ActivityComponent component = activityComponents.get(clazz);
        if (component == null) {
            if (clazz.getName().equals(MainActivity.class.getName())) {
                component = applicationComponent.getMainActivityComponent();
            } else if (clazz.getName().equals(SettingsActivity.class.getName())) {
                component = applicationComponent.getSettingsActivityComponent();
            } else {
                throw new IllegalArgumentException("Illegal class");
            }
            activityComponents.put(clazz, component);
        }
        return component;
    }

    public FragmentComponent getFragmentComponent(Class<?> clazz) {
        FragmentComponent component = fragmentComponents.get(clazz);
        if (component == null) {
            if (clazz.getName().equals(AppThemeFragment.class.getName())) {
                component = ((SettingsActivityComponent) Objects.
                        requireNonNull(activityComponents.get(SettingsActivity.class)))
                        .getAppThemeFragmentComponent();
            } else if (clazz.getName().equals(CameraPhotosFragment.class.getName())) {
                component = ((MainActivityComponent) Objects.
                        requireNonNull(activityComponents.get(MainActivity.class)))
                        .getCameraPhotosFragmentComponent();
            } else if (clazz.getName().equals(FavoritesFragment.class.getName())) {
                component = ((MainActivityComponent) Objects.
                        requireNonNull(activityComponents.get(MainActivity.class)))
                        .getFavoritesFragmentComponent();
            } else {
                throw new IllegalArgumentException("Illegal class");
            }
            fragmentComponents.put(clazz, component);
        }
        return component;
    }

    public void releaseActivityComponent(Class<?> clazz) {
        if (clazz.getName().equals(SettingsActivity.class.getName())) {
            fragmentComponents.remove(AppThemeFragment.class);
        } else if (clazz.getName().equals(MainActivity.class.getName())) {
            fragmentComponents.remove(CameraPhotosFragment.class);
            fragmentComponents.remove(FavoritesFragment.class);
        }
        activityComponents.remove(clazz);
    }

    private void initApplicationComponent(Context context) {
        applicationComponent = DaggerApplicationComponent.builder()
                .setContext(context)
                .build();
    }
}
