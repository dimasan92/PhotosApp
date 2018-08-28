package ru.geekbrains.geekbrainsinstagram.di;

import android.content.Context;

import javax.inject.Singleton;

import androidx.fragment.app.FragmentActivity;
import ru.geekbrains.geekbrainsinstagram.di.activity.ActivityComponent;
import ru.geekbrains.geekbrainsinstagram.di.activity.module.ActivityModule;
import ru.geekbrains.geekbrainsinstagram.di.application.ApplicationComponent;
import ru.geekbrains.geekbrainsinstagram.di.application.DaggerApplicationComponent;
import ru.geekbrains.geekbrainsinstagram.di.application.module.ApplicationModule;
import ru.geekbrains.geekbrainsinstagram.di.application.module.DataModule;
import ru.geekbrains.geekbrainsinstagram.di.fragment.FragmentComponent;
import ru.geekbrains.geekbrainsinstagram.di.fragment.module.FragmentModule;

@Singleton
public final class ComponentsManager {

    private ApplicationComponent applicationComponent;
    private ActivityComponent activityComponent;

    public ComponentsManager(Context context) {
        initApplicationComponent(context);
    }

    public ActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = applicationComponent
                    .getActivityComponent(new ActivityModule());
        }
        return activityComponent;
    }

    public void releaseActivityComponent() {
        activityComponent = null;
    }

    public FragmentComponent getFragmentComponent() {
        return activityComponent.getFragmentComponent(new FragmentModule());
    }

    private void initApplicationComponent(Context context) {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(context))
                .dataModule(new DataModule(context))
                .build();
    }
}
