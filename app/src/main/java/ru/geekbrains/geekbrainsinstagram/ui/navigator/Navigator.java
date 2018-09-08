package ru.geekbrains.geekbrainsinstagram.ui.navigator;

import java.util.Deque;
import java.util.LinkedList;

import javax.inject.Inject;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.di.activity.ActivityScope;
import ru.geekbrains.geekbrainsinstagram.di.fragment.ContentDisposer;
import ru.geekbrains.geekbrainsinstagram.ui.screens.favorites.FavoritesFragment;
import ru.geekbrains.geekbrainsinstagram.ui.screens.home.HomeFragment;
import ru.geekbrains.geekbrainsinstagram.ui.screens.profile.ProfileFragment;
import ru.geekbrains.geekbrainsinstagram.ui.screens.theme.AppThemeFragment;

@ActivityScope
public final class Navigator implements INavigator {

    private static final String HOME_TAG = "home_tag";
    private static final String FAVORITES_TAG = "favorites_tag";
    private static final String PROFILE_TAG = "profile_tag";
    private static final String APP_THEME_TAG = "color_chooser_tag";

    private FragmentManager fragmentManager;
    private ContentDisposer disposer;
    private Deque<String> backStackScreens;
    private BackStackListener backStackListener;

    @Inject
    Navigator() {
        backStackScreens = new LinkedList<>();
    }

    @Override
    public void init(FragmentManager fragmentManager, ContentDisposer disposer) {
        this.fragmentManager = fragmentManager;
        this.disposer = disposer;
    }

    @Override
    public void setupBackStackListener(BackStackListener backStackListener) {
        this.backStackListener = backStackListener;
    }

    @Override
    public void navigateToHome() {
        open(HOME_TAG, HomeFragment::newInstance);
    }

    @Override
    public void navigateToFavorites() {
        open(FAVORITES_TAG, FavoritesFragment::newInstance);
    }

    @Override
    public void navigateToProfile() {
        open(PROFILE_TAG, ProfileFragment::newInstance);
    }

    @Override
    public void navigateToAppTheme() {
        open(APP_THEME_TAG, AppThemeFragment::newInstance);
    }

    @Override
    public void navigateBack() {
        backStackScreens.pop();
        String currentScreenTag = backStackScreens.peek();
        if (currentScreenTag == null) {
            backStackListener.backToScreen(null);
        } else {
            backStackListener.backToScreen(mapTagToScreen(currentScreenTag));
            fragmentManager.popBackStack(currentScreenTag, 0);
        }
    }

    private void open(String tag, FragmentSupplier fragmentSupplier) {
        Fragment fragment = getFragment(tag, fragmentSupplier);
        if (fragment.isVisible()) {
            return;
        }
        addToBackStack(tag);
        disposer.disposeContent();
        fragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, fragment, tag)
                .addToBackStack(tag)
                .commit();
    }

    private Fragment getFragment(String tag, FragmentSupplier fragmentSupplier) {
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            return fragmentSupplier.supplyFragment();
        }
        return fragment;
    }

    private void addToBackStack(String tag) {
        backStackScreens.remove(tag);
        backStackScreens.push(tag);
    }

    private Screen mapTagToScreen(String tag) {
        switch (tag) {
            case HOME_TAG:
                return Screen.HOME_SCREEN;
            case FAVORITES_TAG:
                return Screen.FAVORITES_SCREEN;
            case PROFILE_TAG:
                return Screen.PROFILE_SCREEN;
            case APP_THEME_TAG:
                return Screen.APP_THEME_SCREEN;
            default:
                throw new IllegalArgumentException("Wrong tag");
        }
    }

    @FunctionalInterface
    private interface FragmentSupplier {

        Fragment supplyFragment();
    }
}
