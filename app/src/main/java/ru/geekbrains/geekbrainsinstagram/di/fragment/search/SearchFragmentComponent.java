package ru.geekbrains.geekbrainsinstagram.di.fragment.search;

import dagger.Subcomponent;
import ru.geekbrains.geekbrainsinstagram.di.fragment.FragmentComponent;
import ru.geekbrains.geekbrainsinstagram.ui.screens.home.SearchFragment;

@SearchFragmentScope
@Subcomponent(modules = {SearchFragmentModule.class})
public interface SearchFragmentComponent extends FragmentComponent<SearchFragment> {
}
