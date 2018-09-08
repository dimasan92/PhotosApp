package ru.geekbrains.geekbrainsinstagram.ui.maincontainer;

import android.os.Bundle;
import android.view.View;

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

    private static final String CURRENT_STATE_OF_BOTTOM_NAVIGATION = "current_state_of_bottom_navigation";
    private int currentState;

    @Inject
    INavigator navigator;

    @Inject
    IActivityUtils activityUtils;

    @Inject
    IMainPresenter presenter;

    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;

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
        } else {
            currentState = savedInstanceState.getInt(CURRENT_STATE_OF_BOTTOM_NAVIGATION);
            adjustBottomNavigation(currentState);
        }
        presenter.viewIsReady();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(CURRENT_STATE_OF_BOTTOM_NAVIGATION, currentState);
        super.onSaveInstanceState(outState);
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
                case R.id.nav_home:
                    presenter.homeSelected();
                    adjustBottomNavigation(R.id.bottom_action_home);
                    break;
                case R.id.nav_favorites:
                    presenter.favoritesSelected();
                    adjustBottomNavigation(R.id.bottom_action_favorites);
                    break;
                case R.id.nav_profile:
                    presenter.profileSelected();
                    adjustBottomNavigation(R.id.bottom_action_profile);
                    break;
                case R.id.nav_app_theme:
                    adjustBottomNavigation(-1);
                    presenter.appThemeSelected();
                    break;
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        };
    }

    private void adjustBottomNavigation(int state) {
        currentState = state;
        if (state == -1) {
            bottomNavigationView.setVisibility(View.GONE);
        } else {
            bottomNavigationView.setSelectedItemId(state);
            bottomNavigationView.setVisibility(View.VISIBLE);
        }
    }

    private void setupBottomNavigation() {
        bottomNavigationView = findViewById(R.id.main_bottom_navigator);
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
