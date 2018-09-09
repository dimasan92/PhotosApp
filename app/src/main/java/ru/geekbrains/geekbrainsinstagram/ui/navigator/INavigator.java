package ru.geekbrains.geekbrainsinstagram.ui.navigator;

import androidx.fragment.app.FragmentManager;
import ru.geekbrains.geekbrainsinstagram.di.ContentDisposer;

public interface INavigator {

    interface BackStackListener {

        void backToScreen(Screen screen);
    }

    void init(FragmentManager fragmentManager, ContentDisposer disposer);

    void setupBackStackListener(INavigator.BackStackListener backStackListener);

    void navigateToHome();

    void navigateToFavorites();

    void navigateToProfile();

    void navigateToAppTheme();

    void navigateBack();
}
