package ru.geekbrains.geekbrainsinstagram.ui.settings;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.functions.Consumer;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.utils.SettingsPrefsUtils;
import ru.geekbrains.geekbrainsinstagram.ui.settings.colortheme.SettingsFragment;

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
