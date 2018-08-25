package ru.geekbrains.geekbrainsinstagram.ui.navigator;

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
    public void initializeView() {
        if (fragmentManager.findFragmentById(R.id.main_fragment_container) == null) {
            addFragmentWithoutBackStack(PersonalPhotosFragment.newInstance(), PERSONAL_PHOTOS_TAG);
        }
    }

    @Override
    public void navigateToPersonalPhotos() {
        Fragment fragment = fragmentManager.findFragmentByTag(PERSONAL_PHOTOS_TAG);
        if (fragment == null) {
            fragment = PersonalPhotosFragment.newInstance();
        }
        if (fragment.isVisible()) {
            return;
        }
        replaceFragmentWithoutBackStack(fragment, PERSONAL_PHOTOS_TAG);
    }

    @Override
    public void navigateToAppTheme() {
        Fragment fragment = fragmentManager.findFragmentByTag(APP_THEME_TAG);
        if (fragment == null) {
            fragment = AppThemeFragment.newInstance();
        }
        if (fragment.isVisible()) {
            return;
        }
        addFragmentWithBackStack(fragment, APP_THEME_TAG);
    }

    private void addFragmentWithoutBackStack(Fragment fragment, String tag) {
        fragmentManager.beginTransaction()
                .add(R.id.main_fragment_container, fragment, tag)
                .commit();
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
}
