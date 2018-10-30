package ru.geekbrains.geekbrainsinstagram.ui.navigator;

import java.util.Deque;
import java.util.LinkedList;

import javax.inject.Inject;

import androidx.fragment.app.FragmentManager;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.di.ui.MainScope;
import ru.geekbrains.geekbrainsinstagram.ui.screens.home.HomeFragment;
import ru.geekbrains.geekbrainsinstagram.ui.screens.theme.AppThemeFragment;

@MainScope
public final class MainNavigatorImpl implements MainNavigator {

    private static final String HOME_TAG = "home_tag";
    private static final String APP_THEME_TAG = "color_chooser_tag";

    private final Deque<String> backStackScreens;
    private FragmentManager fragmentManager;
    private BackStackListener backStackListener;

    @Inject MainNavigatorImpl() {
        backStackScreens = new LinkedList<>();
    }

    @Override public void init(final FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override public void setupBackStackListener(final BackStackListener backStackListener) {
        this.backStackListener = backStackListener;
    }

    @Override public void navigateToHome() {
        open(HomeFragment::newInstance, HOME_TAG);
    }

    @Override public void navigateToAppTheme() {
        open(AppThemeFragment::newInstance, APP_THEME_TAG);
    }

    @Override public void navigateBack() {
        final String currentScreenTag = backStackScreens.pop();
        if (currentScreenTag == null) {
            backStackListener.backToScreen(null);
            return;
        }
        switch (currentScreenTag) {
            case APP_THEME_TAG:
                backStackListener.backToScreen(Screens.HOME_SCREEN);
                fragmentManager.popBackStack();
                break;
            case HOME_TAG:
            default:
                backStackListener.backToScreen(null);
                break;
        }
    }

    private void open(final FragmentSupplier fragmentSupplier, final String tag) {
        backStackScreens.push(tag);
        fragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, fragmentSupplier.supplyFragment())
                .addToBackStack(null)
                .commit();
    }
}
