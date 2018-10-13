package ru.geekbrains.geekbrainsinstagram.di.fragment.cameraphotos;

import dagger.Subcomponent;
import ru.geekbrains.geekbrainsinstagram.di.fragment.FragmentComponent;
import ru.geekbrains.geekbrainsinstagram.ui.screens.cameraphotos.CameraPhotosFragment;

@CameraPhotosFragmentScope
@Subcomponent(modules = {CameraPhotosFragmentModule.class})
public interface CameraPhotosFragmentComponent extends FragmentComponent<CameraPhotosFragment> {
}
