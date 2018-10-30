package ru.geekbrains.geekbrainsinstagram.ui.container;

import android.os.Bundle;

import javax.inject.Inject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import ru.geekbrains.domain.interactor.photos.CameraPhotoUpdaterUseCase;
import ru.geekbrains.domain.interactor.photos.SearchPhotoUpdaterUseCase;
import ru.geekbrains.domain.model.AppThemeModel;
import ru.geekbrains.geekbrainsinstagram.MainApplication;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.ui.container.MainPresenter.MainView;
import ru.geekbrains.geekbrainsinstagram.ui.container.mediator.ContainerToContentMediator;
import ru.geekbrains.geekbrainsinstagram.ui.container.mediator.ContainerToContentMediator.EventHandler;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.MainNavigator;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.Screens;
import ru.geekbrains.geekbrainsinstagram.ui.screens.BackListener;

public final class MainActivity extends AppCompatActivity implements MainView {

    @Inject CameraPhotoUpdaterUseCase cameraPhotoUpdaterUseCase;
    @Inject SearchPhotoUpdaterUseCase searchPhotoUpdaterUseCase;
    @Inject ContainerToContentMediator mediator;
    @Inject MainNavigator navigator;
    @Inject MainPresenter presenter;

    private boolean isViewSet;

    @Override protected void onCreate(Bundle savedInstanceState) {
        inject();
        presenter.attachView(this);
        isViewSet = true;
        presenter.beforeOnCreate();

        super.onCreate(savedInstanceState);

        mediator.init(getEventHandler());
        navigator.init(getSupportFragmentManager());
        presenter.setNavigator(navigator);

        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            presenter.viewFirstCreated();
        }
    }

    @Override protected void onStart() {
        super.onStart();
        if (!isViewSet) {
            presenter.attachView(this);
            isViewSet = true;
        }
        presenter.start();
    }

    @Override protected void onStop() {
        super.onStop();
        presenter.stop();
        isViewSet = false;
    }

    @Override protected void onDestroy() {
        if (isFinishing()) {
            release();
        }
        super.onDestroy();
    }

    @Override public void onBackPressed() {
        final Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_fragment_container);
        if (fragment instanceof BackListener) {
            ((BackListener) fragment).onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    @Override public void setTheme(final AppThemeModel theme) {
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

    @Override public void exit() {
        finish();
    }

    private void inject() {
        MainApplication.getApp()
                .getComponentsManager()
                .getMainComponent()
                .inject(this);
    }

    private void release() {
        searchPhotoUpdaterUseCase.dispose();
        cameraPhotoUpdaterUseCase.dispose();
        MainApplication.getApp()
                .getComponentsManager()
                .releaseMainComponent();
    }

    private EventHandler getEventHandler() {
        return new EventHandler() {
            @Override public void setToolbar(final Toolbar toolbar, final Screens screen) {
                MainActivity.this.setSupportActionBar(toolbar);
                final ActionBar actionBar = MainActivity.this.getSupportActionBar();
                if (actionBar != null) {
                    if (Screens.HOME_SCREEN == screen) {
                        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
                    } else {
                        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
                    }
                    actionBar.setDisplayHomeAsUpEnabled(true);
                }
            }

            @Override public void recreate() {
                MainActivity.this.recreate();
            }
        };
    }
}
