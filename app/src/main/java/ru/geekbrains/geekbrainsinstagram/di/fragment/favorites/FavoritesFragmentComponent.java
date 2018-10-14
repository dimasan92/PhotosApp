package ru.geekbrains.geekbrainsinstagram.di.fragment.favorites;

import dagger.Subcomponent;
import ru.geekbrains.geekbrainsinstagram.di.fragment.FragmentComponent;
import ru.geekbrains.geekbrainsinstagram.ui.screens.favorites.FavoritesFragment;

@FavoritesFragmentScope
@Subcomponent(modules = {FavoritesFragmentModule.class})
public interface FavoritesFragmentComponent extends FragmentComponent<FavoritesFragment> {
}
