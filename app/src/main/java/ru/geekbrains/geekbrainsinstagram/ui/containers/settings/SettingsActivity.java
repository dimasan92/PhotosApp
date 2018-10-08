package ru.geekbrains.geekbrainsinstagram.ui.containers.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import ru.geekbrains.domain.model.AppThemeModel;
import ru.geekbrains.geekbrainsinstagram.MainApplication;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.INavigator;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.Screen;

public final class SettingsActivity extends AppCompatActivity implements SettingsPresenter.View {

    private static final String PREVIOUS_START_INTENT = "previous_start_intent";
    private static final String CURRENT_SCREEN = "current_screen";

    @Inject
    INavigator navigator;

    @Inject
    SettingsPresenter presenter;

    @Inject
    Screen.Mapper screenMapper;

    private boolean isViewSet;

    public static Intent getStartIntent(Context packageContext, Intent ownStartIntent, String settingsScreen) {
        Intent startIntent = new Intent(packageContext, SettingsActivity.class);
        startIntent.putExtra(PREVIOUS_START_INTENT, ownStartIntent);
        startIntent.putExtra(CURRENT_SCREEN, settingsScreen);
        return startIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        inject();
        presenter.setView(this);
        isViewSet = true;
        presenter.beforeOnCreate();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        navigator.init(getSupportFragmentManager(),
                () -> MainApplication.getApp().getComponentsManager().releaseFragmentComponent());

        presenter.afterOnCreate();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!isViewSet) {
            presenter.setView(this);
            isViewSet = true;
        }
        presenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            release();
        }
    }

    @Override
    public void setTheme(AppThemeModel theme) {
        switch (theme) {
            case RED_THEME:
                setTheme(R.style.RedAppTheme);
                break;
            case BLUE_THEME:
                setTheme(R.style.BlueAppTheme);
                break;
            case GREEN_THEME:
                setTheme(R.style.GreenAppTheme);
                break;
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
