package ru.geekbrains.pictureapp.presentation.ui.navigator;

import androidx.fragment.app.FragmentManager;

import java.util.Deque;
import java.util.LinkedList;

import javax.inject.Inject;

import ru.geekbrains.pictureapp.R;
import ru.geekbrains.pictureapp.presentation.di.ui.MainScope;
import ru.geekbrains.pictureapp.presentation.ui.screens.details.DetailsFragment;
import ru.geekbrains.pictureapp.presentation.ui.screens.home.HomeFragment;
import ru.geekbrains.pictureapp.presentation.ui.screens.theme.AppThemeFragment;

@MainScope
public final class MainNavigatorImpl implements MainNavigator {

    private static final String HOME_TAG = "home_tag";
    private static final String APP_THEME_TAG = "color_chooser_tag";
    private static final String DETAILS_TAG = "details_tag";

    private final Deque<String> backStackScreens;
    private FragmentManager fragmentManager;
    private BackStackListener backStackListener;

    @Inject
    MainNavigatorImpl() {
        backStackScreens = new LinkedList<>();
    }

    @Override
    public void init(final FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void setupBackStackListener(final BackStackListener backStackListener) {
        this.backStackListener = backStackListener;
    }

    @Override
    public void navigateToHome() {
        goTo(HomeFragment::newInstance, HOME_TAG);
    }

    @Override
    public void navigateToAppTheme() {
        goTo(AppThemeFragment::newInstance, APP_THEME_TAG);
    }

    @Override
    public void navigateToDetails(final String[] jsons, final int initPosition) {
        goTo(() -> DetailsFragment.newInstance(jsons, initPosition), DETAILS_TAG);
    }

    private void goTo(final FragmentSupplier fragmentSupplier, final String tag) {
        backStackScreens.push(tag);
        fragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, fragmentSupplier.supplyFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void navigateBack() {
        final String currentScreenTag = backStackScreens.pop();
        if (currentScreenTag == null) {
            backStackListener.backToScreen(null);
            return;
        }
        switch (currentScreenTag) {
            case APP_THEME_TAG:
            case DETAILS_TAG:
                backStackListener.backToScreen(Screens.HOME_SCREEN);
                fragmentManager.popBackStack();
                break;
            case HOME_TAG:
            default:
                backStackListener.backToScreen(null);
                break;
        }
    }
}
