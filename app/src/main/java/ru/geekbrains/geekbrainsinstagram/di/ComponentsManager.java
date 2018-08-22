package ru.geekbrains.geekbrainsinstagram.di;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.fragment.app.FragmentActivity;
import ru.geekbrains.geekbrainsinstagram.di.activity.ActivityComponent;
import ru.geekbrains.geekbrainsinstagram.di.activity.module.ActivityModule;
import ru.geekbrains.geekbrainsinstagram.di.application.ApplicationComponent;
import ru.geekbrains.geekbrainsinstagram.di.application.DaggerApplicationComponent;
import ru.geekbrains.geekbrainsinstagram.di.fragment.FragmentComponent;
import ru.geekbrains.geekbrainsinstagram.di.fragment.module.FragmentModule;

@Singleton
public final class ComponentsManager {

    private ApplicationComponent applicationComponent;
    private ActivityComponent activityComponent;
    private FragmentComponent fragmentComponent;

    public ComponentsManager(){
        initApplicationComponent();
    }

    private void initApplicationComponent() {
        applicationComponent = DaggerApplicationComponent.create();
    }

    public ActivityComponent getActivityComponent(FragmentActivity activity) {
        if (activityComponent == null) {
            activityComponent = applicationComponent
                    .getActivityComponent(new ActivityModule(activity));
        }
        return activityComponent;
    }

    public FragmentComponent getFragmentComponent(FragmentActivity activity) {
        if (fragmentComponent == null){
            fragmentComponent = getActivityComponent(activity)
                    .getFragmentComponent(new FragmentModule());
        }
        return fragmentComponent;
    }
}
