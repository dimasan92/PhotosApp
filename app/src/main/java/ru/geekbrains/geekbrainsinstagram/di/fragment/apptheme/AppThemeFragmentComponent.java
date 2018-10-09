package ru.geekbrains.geekbrainsinstagram.di.fragment.apptheme;

import dagger.Subcomponent;
import ru.geekbrains.geekbrainsinstagram.di.fragment.FragmentComponent;
import ru.geekbrains.geekbrainsinstagram.ui.screens.theme.AppThemeFragment;

@AppThemeFragmentScope
@Subcomponent(modules = {AppThemeFragmentModule.class})
public interface AppThemeFragmentComponent extends FragmentComponent<AppThemeFragment> {
}
