package ru.geekbrains.geekbrainsinstagram.navigator;

import javax.inject.Inject;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.di.activity.ActivityScope;
import ru.geekbrains.geekbrainsinstagram.ui.settings.theme.ColorThemeFragment;

@ActivityScope
public final class Navigator {

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
//            fragment = ColorThemeFragment.newInstance();
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .add(R.id.fl_settings_fragment_container, fragment)
//                    .commit();
        }
//        fragment.addThemeObserver(mThemeObserver);
    }

    public void navigateToColorChooser() {
        initFragment(ColorThemeFragment.newInstance());
    }

    private void initFragment(Fragment fragment) {
        fragmentManager.beginTransaction()
                .add(R.id.main_fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
