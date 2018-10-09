package ru.geekbrains.geekbrainsinstagram.ui.containers.settings;

import android.os.Bundle;

import javax.inject.Inject;

import androidx.appcompat.widget.Toolbar;
import ru.geekbrains.geekbrainsinstagram.MainApplication;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.di.activity.settings.SettingsActivityComponent;
import ru.geekbrains.geekbrainsinstagram.ui.containers.BaseContainerViewImpl;
import ru.geekbrains.geekbrainsinstagram.ui.containers.settings.mediator.SettingsContainerToContentMediator;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.Screens;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.androidxcicerone.SupportAppNavigator;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;

import static ru.geekbrains.geekbrainsinstagram.ui.navigator.Screens.SettingsContainer.SCREEN_TO_OPEN;

public final class SettingsActivity extends BaseContainerViewImpl<SettingsPresenter.View, SettingsPresenter>
        implements SettingsPresenter.View {

    @Inject
    SettingsContainerToContentMediator mediator;

    @Inject
    Screens.Mapper mapper;

    @Inject
    NavigatorHolder navigatorHolder;

    private final Navigator navigator = new SupportAppNavigator(this, R.id.settings_fragment_container);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mediator.init(this::recreateActivity);
        presenter.viewIsReady(mapper.getScreen(getIntent().getStringExtra(SCREEN_TO_OPEN)));
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        navigatorHolder.setNavigator(navigator);
    }

    @Override
    protected void onPause() {
        navigatorHolder.removeNavigator();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        presenter.back();
    }

    @Override
    public void close() {
        finish();
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
        Toolbar toolbar = findViewById(R.id.settings_toolbar);
        Screens.Screen screenToOpen = mapper.getScreen(getIntent().getStringExtra(SCREEN_TO_OPEN));
        switch (screenToOpen) {
            case APP_THEME_SCREEN:
                toolbar.setTitle(R.string.appbar_app_theme_title);
                break;
            default:
                throw new IllegalArgumentException("Wrong screen type");
        }
    }

    private void recreateActivity() {
        recreate();
    }
}
