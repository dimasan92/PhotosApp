package ru.geekbrains.geekbrainsinstagram.di.fragment;

import dagger.Subcomponent;
import ru.geekbrains.geekbrainsinstagram.di.childfragment.ChildFragmentComponent;
import ru.geekbrains.geekbrainsinstagram.di.fragment.module.FragmentModule;
import ru.geekbrains.geekbrainsinstagram.ui.screens.favorites.FavoritesFragment;
import ru.geekbrains.geekbrainsinstagram.ui.screens.home.HomeFragment;
import ru.geekbrains.geekbrainsinstagram.ui.screens.personalphotos.PersonalPhotosFragment;
import ru.geekbrains.geekbrainsinstagram.ui.screens.profile.ProfileFragment;
import ru.geekbrains.geekbrainsinstagram.ui.screens.theme.AppThemeFragment;
import ru.geekbrains.geekbrainsinstagram.ui.screens.theme.AppThemePresenter;

@FragmentScope
@Subcomponent(modules = {FragmentModule.class})
public interface FragmentComponent {

    ChildFragmentComponent getChildFragmentComponent();

    void inject(HomeFragment view);

    void inject(FavoritesFragment view);

    void inject(ProfileFragment view);

    void inject(AppThemeFragment view);
}
