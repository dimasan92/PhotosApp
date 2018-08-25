package ru.geekbrains.geekbrainsinstagram.ui.navigator;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

import javax.inject.Inject;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.ui.screens.personalphotos.PersonalPhotosFragment;
import ru.geekbrains.geekbrainsinstagram.ui.screens.theme.AppThemeFragment;

public final class Navigator implements INavigator {

    private static final String PERSONAL_PHOTOS_TAG = "personal_photos_tag";
    private static final String APP_THEME_TAG = "color_chooser_tag";

    private final FragmentManager fragmentManager;

    @Inject
    public Navigator(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void navigateToPersonalPhotos() {
        Fragment fragment = getFragment(PERSONAL_PHOTOS_TAG, PersonalPhotosFragment::newInstance);
        if (fragment == null) {
            return;
        }
        replaceFragmentWithoutBackStack(fragment, PERSONAL_PHOTOS_TAG);
    }

    @Override
    public void navigateToAppTheme() {
        Fragment fragment = getFragment(APP_THEME_TAG, AppThemeFragment::newInstance);
        if (fragment == null) {
            return;
        }
        addFragmentWithBackStack(fragment, APP_THEME_TAG);
    }

    private Fragment getFragment(String tag, FragmentSupplier fragmentSupplier) {
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            return fragmentSupplier.supplyFragment();
        }
        if (fragment.isVisible()) {
            return null;
        }
        return fragment;
    }

    private void replaceFragmentWithoutBackStack(Fragment fragment, String tag) {
        fragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, fragment, tag)
                .commit();
    }

    private void addFragmentWithBackStack(Fragment fragment, String tag) {
        fragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, fragment, tag)
                .addToBackStack(null)
                .commit();
    }

    @FunctionalInterface
    private interface FragmentSupplier {

        Fragment supplyFragment();
    }
}
