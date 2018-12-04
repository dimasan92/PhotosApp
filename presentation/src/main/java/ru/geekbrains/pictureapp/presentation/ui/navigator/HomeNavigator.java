package ru.geekbrains.pictureapp.presentation.ui.navigator;

import androidx.fragment.app.FragmentManager;

public interface HomeNavigator {

    void init(final FragmentManager fragmentManager);

    void setupBackStackListener(final BackStackListener backStackListener);

    void navigateToSearch();

    void navigateToCamera();

    void navigateToFavorites();

    void navigateBack();
}
