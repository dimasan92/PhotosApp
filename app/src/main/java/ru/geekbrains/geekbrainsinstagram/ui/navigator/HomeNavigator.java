package ru.geekbrains.geekbrainsinstagram.ui.navigator;

import androidx.fragment.app.FragmentManager;
import ru.geekbrains.geekbrainsinstagram.di.ContentDisposer;

public interface HomeNavigator {

    void init(final FragmentManager fragmentManager);

    void setupBackStackListener(final BackStackListener backStackListener);

    void navigateToSearch();

    void navigateToCamera();

    void navigateToFavorites();

    void navigateBack();
}
