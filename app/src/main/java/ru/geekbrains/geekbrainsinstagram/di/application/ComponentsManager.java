package ru.geekbrains.geekbrainsinstagram.di.application;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import ru.geekbrains.geekbrainsinstagram.di.activity.ActivityComponent;
import ru.geekbrains.geekbrainsinstagram.ui.containers.main.MainActivity;

public final class ComponentsManager {

    private ApplicationComponent applicationComponent;
    private Map<Class<?>, ActivityComponent> activityComponents;

    public ComponentsManager(Context context) {
        initApplicationComponent(context);
        activityComponents = new HashMap<>();
    }

    public ActivityComponent getActivityComponent(Class<?> clazz) {
        ActivityComponent component = activityComponents.get(clazz);
        if (component == null) {
            if (clazz.getName().equals(MainActivity.class.getName())) {
                component = applicationComponent.getMainActivityComponent();
            } else {
                throw new IllegalArgumentException("Illegal class");
            }
            activityComponents.put(clazz, component);
        }
        return component;
    }

    public void releaseActivityComponent(Class<?> clazz) {
        activityComponents.remove(clazz);
    }

    private void initApplicationComponent(Context context) {
        applicationComponent = DaggerApplicationComponent.builder()
                .setContext(context)
                .build();
    }
}
