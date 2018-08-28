package ru.geekbrains.geekbrainsinstagram.ui.navigator;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.ui.screens.personalphotos.PersonalPhotosFragment;
import ru.geekbrains.geekbrainsinstagram.ui.screens.theme.AppThemeFragment;

public final class Navigator implements INavigator {

    private static final String PERSONAL_PHOTOS_TAG = "personal_photos_tag";
    private static final String APP_THEME_TAG = "color_chooser_tag";

    private FragmentManager fragmentManager;

    @Override
    public void init(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void navigateToPersonalPhotos() {
        Fragment fragment = getFragment(PERSONAL_PHOTOS_TAG, PersonalPhotosFragment::newInstance);
        openAsRoot(fragment, PERSONAL_PHOTOS_TAG);
    }

    @Override
    public void navigateToAppTheme() {
        Fragment fragment = getFragment(APP_THEME_TAG, AppThemeFragment::newInstance);
        openAsLeaf(fragment, APP_THEME_TAG);
    }

    private Fragment getFragment(String tag, FragmentSupplier fragmentSupplier) {
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            return fragmentSupplier.supplyFragment();
        }
        return fragment;
    }


    private void openAsRoot(Fragment fragment, String tag) {
        openWithoutBackStack(fragment, tag);
    }

    private void openAsLeaf(Fragment fragment, String tag) {
        if (fragment.isVisible()) {
            return;
        }
        openWithBackStack(fragment, tag);
    }

    private void openWithBackStack(Fragment fragment, String tag) {
        fragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, fragment, tag)
                .addToBackStack(null)
                .commit();
    }

    private void openWithoutBackStack(Fragment fragment, String tag) {
        fragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, fragment, tag)
                .commit();
    }

    @FunctionalInterface
    private interface FragmentSupplier {

        Fragment supplyFragment();
    }
}
