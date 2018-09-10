package ru.geekbrains.geekbrainsinstagram.ui.navigator;

import androidx.fragment.app.FragmentManager;
import ru.geekbrains.geekbrainsinstagram.di.ContentDisposer;

public interface INavigator {

    interface BackStackListener {

        void backToScreen(Screen screen);
    }

    interface DrawerUnlockListener {

        void drawerUnlock();
    }

    void init(FragmentManager fragmentManager, ContentDisposer disposer);

    void setupBackStackListener(INavigator.BackStackListener backStackListener);

    void setupDrawerUnlockListener(DrawerUnlockListener drawerUnlockListener);

    void navigateToHome();

    void navigateToFavorites();

    void navigateToProfile();

    void navigateToAppTheme();

    void navigateToPhotoDetails(String photoId);

    void navigateBack();
}
