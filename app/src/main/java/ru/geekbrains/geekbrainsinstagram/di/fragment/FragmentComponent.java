package ru.geekbrains.geekbrainsinstagram.di.fragment;

import dagger.Subcomponent;
import ru.geekbrains.geekbrainsinstagram.di.fragment.module.FragmentModule;
import ru.geekbrains.geekbrainsinstagram.ui.settings.theme.ColorThemeFragment;

@FragmentScope
@Subcomponent(modules = {FragmentModule.class})
public interface FragmentComponent {

    void inject(ColorThemeFragment view);
}
