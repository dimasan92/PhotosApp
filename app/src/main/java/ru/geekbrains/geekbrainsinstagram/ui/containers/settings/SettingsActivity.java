package ru.geekbrains.geekbrainsinstagram.ui.containers.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import ru.geekbrains.geekbrainsinstagram.MainApplication;
import ru.geekbrains.geekbrainsinstagram.R;

public class SettingsActivity extends AppCompatActivity {

    private static final String PREVIOUS_START_INTENT = "previous_start_intent";
    private static final String CURRENT_SCREEN = "current_screen";

    @Inject
    SettingsPresenter presenter;

    public static Intent getStartIntent(Context packageContext, Intent ownStartIntent, String settingsScreen) {
        Intent startIntent = new Intent(packageContext, SettingsActivity.class);
        startIntent.putExtra(PREVIOUS_START_INTENT, ownStartIntent);
        startIntent.putExtra(CURRENT_SCREEN, settingsScreen);
        return startIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        inject();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.settings_toolbar);
        toolbar.setTitle("Title");
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            release();
        }
    }

    private void inject() {
        MainApplication.getApp()
                .getComponentsManager()
                .getActivityComponent()
                .inject(this);
    }

    private void release() {
        MainApplication.getApp()
                .getComponentsManager()
                .releaseActivityComponent();
    }
}
