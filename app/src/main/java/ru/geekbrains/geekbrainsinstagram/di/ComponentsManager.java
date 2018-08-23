package ru.geekbrains.geekbrainsinstagram.di;

import android.content.Context;

import javax.inject.Singleton;

import ru.geekbrains.geekbrainsinstagram.di.activity.ActivityComponent;
import ru.geekbrains.geekbrainsinstagram.di.application.ApplicationComponent;
import ru.geekbrains.geekbrainsinstagram.di.application.DaggerApplicationComponent;
import ru.geekbrains.geekbrainsinstagram.di.application.module.ApplicationModule;
import ru.geekbrains.geekbrainsinstagram.di.fragment.FragmentComponent;
import ru.geekbrains.geekbrainsinstagram.di.fragment.module.FragmentModule;

@Singleton
public final class ComponentsManager {

    private ApplicationComponent applicationComponent;
    private ActivityComponent activityComponent;
    private FragmentComponent fragmentComponent;

    public ComponentsManager(Context context) {
        initApplicationComponent(context);
    }

    private void initApplicationComponent(Context context) {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(context))
                .build();
    }

    public ActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = applicationComponent
                    .getActivityComponent();
        }
        return activityComponent;
    }

    public FragmentComponent getFragmentComponent() {
        if (fragmentComponent == null) {
            fragmentComponent = activityComponent
                    .getFragmentComponent(new FragmentModule());
        }
        return fragmentComponent;
    }
}
