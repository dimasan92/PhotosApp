package ru.geekbrains.geekbrainsinstagram.presentation.view.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.functions.Consumer;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.presentation.utils.SettingsPrefsUtils;
import ru.geekbrains.geekbrainsinstagram.presentation.view.fragment.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {

    private Consumer<Integer> mThemeObserver = (theme) -> {
        if (theme != SettingsPrefsUtils.getCurrentTheme(getApplicationContext())) {
            SettingsPrefsUtils.saveCurrentTheme(getApplicationContext(), theme);
            recreate();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(SettingsPrefsUtils.getCurrentTheme(getApplicationContext()));
        setContentView(R.layout.activity_settings);
        initFragment();
    }

    private void initFragment() {
        SettingsFragment fragment = (SettingsFragment)
                getSupportFragmentManager().findFragmentById(R.id.fl_settings_fragment_container);

        if (fragment == null) {
            fragment = SettingsFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_settings_fragment_container, fragment)
                    .commit();
        }
        fragment.addThemeObserver(mThemeObserver);
    }
}
