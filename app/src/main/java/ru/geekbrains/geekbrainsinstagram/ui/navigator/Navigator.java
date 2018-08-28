package ru.geekbrains.geekbrainsinstagram.ui.navigator;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import ru.geekbrains.geekbrainsinstagram.MainApplication;
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
        open(PERSONAL_PHOTOS_TAG, PersonalPhotosFragment::newInstance, false);
    }

    @Override
    public void navigateToAppTheme() {
        open(APP_THEME_TAG, AppThemeFragment::newInstance, true);
    }

    private void open(String tag, FragmentSupplier fragmentSupplier, boolean isBackStack) {
        Fragment fragment = getFragment(tag, fragmentSupplier);
        if (fragment.isVisible()) {
            return;
        }
        MainApplication.getApp().getComponentsManager().releaseFragmentComponent();
        if (isBackStack) {
            openWithBackStack(fragment, tag);
        } else {
            openWithoutBackStack(fragment, tag);
        }
    }

    private Fragment getFragment(String tag, FragmentSupplier fragmentSupplier) {
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            return fragmentSupplier.supplyFragment();
        }
        return fragment;
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
