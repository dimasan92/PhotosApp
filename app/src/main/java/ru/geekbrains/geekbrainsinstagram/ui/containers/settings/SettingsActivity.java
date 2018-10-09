package ru.geekbrains.geekbrainsinstagram.ui.containers.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import ru.geekbrains.geekbrainsinstagram.MainApplication;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.di.activity.settings.SettingsActivityComponent;
import ru.geekbrains.geekbrainsinstagram.ui.containers.BaseContainerViewImpl;

public final class SettingsActivity extends BaseContainerViewImpl<SettingsPresenter.View, SettingsPresenter>
        implements SettingsPresenter.View {

    private static final String PREVIOUS_START_INTENT = "previous_start_intent";
    private static final String CURRENT_SCREEN = "current_screen";


    public static Intent getStartIntent(Context packageContext, Intent ownStartIntent, String settingsScreen) {
        Intent startIntent = new Intent(packageContext, SettingsActivity.class);
        startIntent.putExtra(PREVIOUS_START_INTENT, ownStartIntent);
        startIntent.putExtra(CURRENT_SCREEN, settingsScreen);
        return startIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void inject() {
        SettingsActivityComponent component = (SettingsActivityComponent) MainApplication.getApp()
                .getComponentsManager()
                .getActivityComponent(SettingsActivity.class);
        component.inject(this);
    }

    @Override
    protected void release() {
        MainApplication.getApp()
                .getComponentsManager()
                .releaseActivityComponent(SettingsActivity.class);
    }

    @Override
    protected void attachView() {
        presenter.attachView(this);
    }

    @Override
    protected void setupView() {
        setContentView(R.layout.activity_settings);
    }
}
