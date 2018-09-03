package ru.geekbrains.geekbrainsinstagram.di.fragment;

import dagger.Subcomponent;
import ru.geekbrains.geekbrainsinstagram.di.fragment.module.FragmentModule;
import ru.geekbrains.geekbrainsinstagram.ui.screens.personalphotos.PersonalPhotosFragment;
import ru.geekbrains.geekbrainsinstagram.ui.screens.theme.AppThemeFragment;
import ru.geekbrains.geekbrainsinstagram.ui.screens.theme.AppThemePresenter;

@FragmentScope
@Subcomponent(modules = {FragmentModule.class})
public interface FragmentComponent {

    void inject(AppThemeFragment view);

    void inject(PersonalPhotosFragment view);
}
