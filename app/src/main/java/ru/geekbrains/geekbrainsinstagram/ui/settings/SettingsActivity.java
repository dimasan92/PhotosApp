package ru.geekbrains.geekbrainsinstagram.ui.settings;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.functions.Consumer;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.ui.settings.theme.ColorThemeFragment;
import ru.geekbrains.geekbrainsinstagram.utils.SettingsPrefsUtils;

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
        ColorThemeFragment fragment = (ColorThemeFragment)
                getSupportFragmentManager().findFragmentById(R.id.fl_settings_fragment_container);

        if (fragment == null) {
            fragment = ColorThemeFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_settings_fragment_container, fragment)
                    .commit();
        }
        fragment.addThemeObserver(mThemeObserver);
    }
}
