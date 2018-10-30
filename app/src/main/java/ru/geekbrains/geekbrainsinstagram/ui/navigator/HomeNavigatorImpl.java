package ru.geekbrains.geekbrainsinstagram.ui.navigator;

import java.util.Deque;
import java.util.LinkedList;

import javax.inject.Inject;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.di.ui.home.HomeScope;
import ru.geekbrains.geekbrainsinstagram.ui.screens.cameraphotos.CameraPhotosFragment;
import ru.geekbrains.geekbrainsinstagram.ui.screens.favorites.FavoritesFragment;
import ru.geekbrains.geekbrainsinstagram.ui.screens.search.SearchFragment;

@HomeScope
public final class HomeNavigatorImpl implements HomeNavigator {

    private static final String SEARCH_TAG = "search_tag";
    private static final String CAMERA_TAG = "camera_tag";
    private static final String FAVORITES_TAG = "favorites_tag";

    private FragmentManager fragmentManager;
    private Deque<String> backStackScreens;
    private BackStackListener backStackListener;

    @Inject HomeNavigatorImpl() {
        backStackScreens = new LinkedList<>();
    }

    @Override public void init(final FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override public void setupBackStackListener(BackStackListener backStackListener) {
        this.backStackListener = backStackListener;
    }

    @Override public void navigateToSearch() {
        open(SEARCH_TAG, SearchFragment::newInstance);
    }

    @Override public void navigateToCamera() {
        open(CAMERA_TAG, CameraPhotosFragment::newInstance);
    }

    @Override public void navigateToFavorites() {
        open(FAVORITES_TAG, FavoritesFragment::newInstance);
    }

    @Override public void navigateBack() {
        backStackScreens.pop();
        final String currentScreenTag = backStackScreens.peek();
        if (currentScreenTag == null) {
            backStackListener.backToScreen(null);
        } else {
            backStackListener.backToScreen(mapTagToScreen(currentScreenTag));
            fragmentManager.popBackStack(currentScreenTag, 0);
        }
    }

    private void open(final String tag, final FragmentSupplier fragmentSupplier) {
        final Fragment fragment = getFragment(tag, fragmentSupplier);
        if (fragment.isVisible()) {
            return;
        }
        addToBackStack(tag);
        fragmentManager.beginTransaction()
                .replace(R.id.home_fragment_container, fragment, tag)
                .addToBackStack(tag)
                .commit();
    }

    private Fragment getFragment(final String tag, final FragmentSupplier fragmentSupplier) {
        final Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            return fragmentSupplier.supplyFragment();
        }
        return fragment;
    }

    private void addToBackStack(final String tag) {
        backStackScreens.remove(tag);
        backStackScreens.push(tag);
    }

    private Screens mapTagToScreen(final String tag) {
        switch (tag) {
            case SEARCH_TAG:
                return Screens.SEARCH_SCREEN;
            case CAMERA_TAG:
                return Screens.CAMERA_PHOTOS_SCREEN;
            case FAVORITES_TAG:
                return Screens.FAVORITES_SCREEN;
            default:
                throw new IllegalArgumentException("Wrong tag");
        }
    }

}
