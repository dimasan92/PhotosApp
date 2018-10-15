package ru.geekbrains.geekbrainsinstagram.di.fragment.search;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.geekbrainsinstagram.ui.screens.home.SearchPagePresenterImpl;
import ru.geekbrains.geekbrainsinstagram.ui.screens.home.SearchPagerPresenter;

@Module
public interface SearchFragmentModule {

    @SearchFragmentScope
    @Binds
    SearchPagerPresenter provideAppThemePresenter(SearchPagePresenterImpl presenter);
}
