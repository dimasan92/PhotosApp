package ru.geekbrains.geekbrainsinstagram.di.activity;

import dagger.Subcomponent;
import ru.geekbrains.geekbrainsinstagram.di.activity.module.ActivityModule;
import ru.geekbrains.geekbrainsinstagram.di.fragment.FragmentComponent;
import ru.geekbrains.geekbrainsinstagram.ui.containers.main.MainActivity;

@ActivityScope
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    FragmentComponent getFragmentComponent();

    void inject(MainActivity activity);
}

