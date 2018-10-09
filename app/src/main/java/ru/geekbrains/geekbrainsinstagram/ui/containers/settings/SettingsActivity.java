package ru.geekbrains.geekbrainsinstagram.ui.containers.settings;

import android.content.Intent;
import android.os.Bundle;

import javax.inject.Inject;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.TaskStackBuilder;
import ru.geekbrains.geekbrainsinstagram.MainApplication;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.di.activity.settings.SettingsActivityComponent;
import ru.geekbrains.geekbrainsinstagram.ui.containers.BaseContainerViewImpl;
import ru.geekbrains.geekbrainsinstagram.ui.containers.settings.mediator.SettingsContainerToContentMediator;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.Screens;

import static ru.geekbrains.geekbrainsinstagram.ui.navigator.Screens.SettingsContainer.PREVIOUS_START_INTENT;
import static ru.geekbrains.geekbrainsinstagram.ui.navigator.Screens.SettingsContainer.SCREEN_TO_OPEN;

public final class SettingsActivity extends BaseContainerViewImpl<SettingsPresenter.View, SettingsPresenter>
        implements SettingsPresenter.View {

    @Inject
    SettingsContainerToContentMediator mediator;

    @Inject
    Screens.Mapper mapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mediator.init(this::recreateActivity);
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

    }

    private void recreateActivity() {
        TaskStackBuilder.create(this)
                .addNextIntent(getIntent().getParcelableExtra(PREVIOUS_START_INTENT))
                .addNextIntent(new Intent(this, SettingsActivity.class))
                .startActivities();
        finish();
    }
}
