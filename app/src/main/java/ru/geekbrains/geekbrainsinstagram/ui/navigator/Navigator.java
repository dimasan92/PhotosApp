package ru.geekbrains.geekbrainsinstagram.ui.navigator;

import javax.inject.Inject;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.di.activity.ActivityScope;
import ru.geekbrains.geekbrainsinstagram.ui.screens.personalphotos.PersonalPhotosFragment;
import ru.geekbrains.geekbrainsinstagram.ui.screens.theme.AppThemeFragment;

@ActivityScope
public final class Navigator {

    private static final String COLOR_CHOOSER_TAG = "color_chooser_tag";
    private static final String CAMERA_GALLERY_TAG = "camera_gallery_tag";

    private FragmentManager fragmentManager;

    @Inject
    public Navigator() {
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void initializeView() {
        Fragment fragment = fragmentManager.findFragmentById(R.id.main_fragment_container);

        if (fragment == null) {
            addFragmentWithoutBackStack(PersonalPhotosFragment.newInstance(), CAMERA_GALLERY_TAG);
        }
    }

    public void navigateToCameraGallery() {
        Fragment fragment = fragmentManager.findFragmentByTag(CAMERA_GALLERY_TAG);
        if (fragment == null) {
            fragment = PersonalPhotosFragment.newInstance();
        }
        addFragmentWithoutBackStack(fragment, CAMERA_GALLERY_TAG);
    }

    public void navigateToColorChooser() {
        Fragment fragment = fragmentManager.findFragmentByTag(COLOR_CHOOSER_TAG);
        if (fragment == null) {
            fragment = AppThemeFragment.newInstance();
        }
        if (fragment.isVisible()) {
            return;
        }
        addFragmentWithBackStack(fragment, COLOR_CHOOSER_TAG);
    }

    private void addFragmentWithBackStack(Fragment fragment, String tag) {
        fragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, fragment, tag)
                .addToBackStack(null)
                .commit();
    }

    private void addFragmentWithoutBackStack(Fragment fragment, String tag) {
        fragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, fragment, tag)
                .commit();
    }
}
