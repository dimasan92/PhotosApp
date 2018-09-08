package ru.geekbrains.geekbrainsinstagram.di;

import android.content.Context;

import ru.geekbrains.geekbrainsinstagram.di.activity.ActivityComponent;
import ru.geekbrains.geekbrainsinstagram.di.application.ApplicationComponent;
import ru.geekbrains.geekbrainsinstagram.di.application.DaggerApplicationComponent;
import ru.geekbrains.geekbrainsinstagram.di.application.module.ApplicationModule;
import ru.geekbrains.geekbrainsinstagram.di.childfragment.ChildFragmentComponent;
import ru.geekbrains.geekbrainsinstagram.di.fragment.FragmentComponent;

public final class ComponentsManager {

    private ApplicationComponent applicationComponent;
    private ActivityComponent activityComponent;
    private FragmentComponent fragmentComponent;
    private ChildFragmentComponent childFragmentComponent;

    public ComponentsManager(Context context) {
        initApplicationComponent(context);
    }

    public ActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = applicationComponent
                    .getActivityComponent();
        }
        return activityComponent;
    }

    public void releaseActivityComponent() {
        activityComponent = null;
        releaseFragmentComponent();
    }

    public FragmentComponent getFragmentComponent() {
        if (fragmentComponent == null) {
            fragmentComponent = activityComponent.getFragmentComponent();
        }
        return fragmentComponent;
    }

    public ChildFragmentComponent getChildFragmentComponent() {
        if (childFragmentComponent == null) {
            childFragmentComponent = fragmentComponent.getChildFragmentComponent();
        }
        return childFragmentComponent;
    }

    public void releaseFragmentComponent() {
        fragmentComponent = null;
        releaseChildFragmentComponent();
    }

    public void releaseChildFragmentComponent() {
        childFragmentComponent = null;
    }

    private void initApplicationComponent(Context context) {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(context))
                .build();
    }
}
