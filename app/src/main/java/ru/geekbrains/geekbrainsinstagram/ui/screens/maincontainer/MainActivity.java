package ru.geekbrains.geekbrainsinstagram.ui.screens.maincontainer;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import javax.inject.Inject;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import ru.geekbrains.geekbrainsinstagram.MainApplication;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.di.fragment.ContentDisposer;
import ru.geekbrains.geekbrainsinstagram.model.AppTheme;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.INavigator;
import ru.geekbrains.geekbrainsinstagram.util.IActivityUtils;

public final class MainActivity extends AppCompatActivity implements IMainPresenter.IView,
        ContentDisposer, IActivityUtils.EventHandler {

    @Inject
    INavigator navigator;

    @Inject
    IActivityUtils activityUtils;

    @Inject
    IMainPresenter presenter;

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        inject();
        presenter.setView(this);
        presenter.readyToSetupTheme();

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setupView();

        activityUtils.init(this);
        navigator.init(getSupportFragmentManager(), this);
        presenter.setNavigator(navigator);

        if (savedInstanceState == null) {
            presenter.viewFirstCreated();
        }
        presenter.viewIsReady();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy();
        if (isFinishing()) {
            release();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void setTheme(AppTheme theme) {
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

    @Override
    public void disposeContent() {
        MainApplication.getApp()
                .getComponentsManager()
                .releaseFragmentComponent();
    }

    @Override
    public void setToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        setupDrawerListener(toolbar);
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

    private void setupView() {
        setupDrawer();
        setupBottomNavigation();
    }

    private void setupDrawer() {
        drawerLayout = findViewById(R.id.main_navigator_layout);
        NavigationView navigationView = findViewById(R.id.main_navigator);
        navigationView.setNavigationItemSelectedListener(getDrawerListener());
    }

    private void setupDrawerListener(Toolbar toolbar) {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private NavigationView.OnNavigationItemSelectedListener getDrawerListener() {
        return menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.nav_personal_photos:
                    presenter.personalPhotosSelected();
                    break;
                case R.id.nav_app_theme:
                    presenter.appThemeSelected();
                    break;
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        };
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.main_bottom_navigator);
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.bottom_action_home:
                    presenter.homeSelected();
                    return true;
                case R.id.bottom_action_favorites:
                    presenter.favoritesSelected();
                    return true;
                case R.id.bottom_action_profile:
                    presenter.profileSelected();
                    return true;
            }
            return false;
        });
    }
}
