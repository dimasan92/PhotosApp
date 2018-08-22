package ru.geekbrains.geekbrainsinstagram.di.activity;

import dagger.Subcomponent;
import ru.geekbrains.geekbrainsinstagram.di.activity.module.ActivityModule;
import ru.geekbrains.geekbrainsinstagram.di.fragment.FragmentComponent;
import ru.geekbrains.geekbrainsinstagram.di.fragment.module.FragmentModule;
import ru.geekbrains.geekbrainsinstagram.ui.MainActivity;
import ru.geekbrains.geekbrainsinstagram.ui.MainPresenter;

@ActivityScope
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    FragmentComponent getFragmentComponent(FragmentModule fragmentModule);

    void inject(MainActivity activity);

    void inject(MainPresenter presenter);
}

