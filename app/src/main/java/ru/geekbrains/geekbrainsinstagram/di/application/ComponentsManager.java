package ru.geekbrains.geekbrainsinstagram.di.application;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import ru.geekbrains.geekbrainsinstagram.di.activity.ActivityComponent;
import ru.geekbrains.geekbrainsinstagram.ui.containers.main.MainActivity;

public final class ComponentsManager {

    private ApplicationComponent applicationComponent;
    private Map<Object, ActivityComponent> activityComponents;

    public ComponentsManager(Context context) {
        initApplicationComponent(context);
        activityComponents = new HashMap<>();
    }

    public ActivityComponent getActivityComponent(Object activity) {
        ActivityComponent component = activityComponents.get(activity);
        if (component == null) {
            if (activity instanceof MainActivity) {
                component = applicationComponent.getMainActivityComponent();
            } else {
                throw new IllegalArgumentException("Illegal class");
            }
            activityComponents.put(activity, component);
        }
        return component;
    }

    public void releaseActivityComponent(Object activity) {
        activityComponents.remove(activity);
    }

    private void initApplicationComponent(Context context) {
        applicationComponent = DaggerApplicationComponent.builder()
                .setContext(context)
                .build();
    }
}
