package ru.geekbrains.geekbrainsinstagram.ui.navigator;

import androidx.fragment.app.FragmentManager;

public interface MainNavigator {

    void init(final FragmentManager fragmentManager);

    void setupBackStackListener(final BackStackListener backStackListener);

    void navigateToHome();

    void navigateToAppTheme();

    void navigateBack();
}
