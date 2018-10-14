package ru.geekbrains.geekbrainsinstagram.di.activity.main;

import dagger.Subcomponent;
import ru.geekbrains.geekbrainsinstagram.di.activity.ActivityComponent;
import ru.geekbrains.geekbrainsinstagram.di.fragment.cameraphotos.CameraPhotosFragmentComponent;
import ru.geekbrains.geekbrainsinstagram.ui.containers.main.MainActivity;

@MainActivityScope
@Subcomponent(modules = MainActivityModule.class)
public interface MainActivityComponent extends ActivityComponent<MainActivity> {

    CameraPhotosFragmentComponent getCameraPhotosFragmentComponent();
}
