package ru.geekbrains.geekbrainsinstagram.di.fragment;

import dagger.Subcomponent;
import ru.geekbrains.geekbrainsinstagram.di.fragment.module.FragmentModule;
import ru.geekbrains.geekbrainsinstagram.ui.personalphotos.PersonalPhotosFragment;
import ru.geekbrains.geekbrainsinstagram.ui.personalphotos.PersonalPhotosPresenter;
import ru.geekbrains.geekbrainsinstagram.ui.settings.theme.AppThemePresenter;
import ru.geekbrains.geekbrainsinstagram.ui.settings.theme.AppThemeFragment;

@FragmentScope
@Subcomponent(modules = {FragmentModule.class})
public interface FragmentComponent {

    void inject(AppThemeFragment view);

    void inject(AppThemePresenter presenter);

    void inject(PersonalPhotosFragment view);

    void inject(PersonalPhotosPresenter presenter);
}
