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
import ru.geekbrains.geekbrainsinstagram.model.AppTheme;
import ru.geekbrains.geekbrainsinstagram.ui.mediator.IActivityToFragmentMediator;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.INavigator;

public final class MainActivity extends AppCompatActivity implements IMainPresenter.IView {

    @Inject
    INavigator navigator;

    @Inject
    IActivityToFragmentMediator activityToFragmentMediator;

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

        setupView();

        activityToFragmentMediator.init(toolbar -> {
            setSupportActionBar(toolbar);
            setupDrawerListener(toolbar);
        });
        navigator.init(getSupportFragmentManager(),
                () -> MainApplication.getApp().getComponentsManager().releaseFragmentComponent());
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
            presenter.backPressed();
        }
    }

    @Override
    public void closeApp() {
        finish();
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
    public void setMainScreenNavigationState(MainScreenNavigationState state) {
        switch (state) {
            case HOME_PAGE_STATE:
                bottomNavigationView.setSelectedItemId(R.id.bottom_action_home);
                break;
            case FAVORITES_PAGE_STATE:
                bottomNavigationView.setSelectedItemId(R.id.bottom_action_favorites);
                break;
            case PROFILE_PAGE_STATE:
                bottomNavigationView.setSelectedItemId(R.id.bottom_action_profile);
                break;
            case INVISIBLE_STATE:
                bottomNavigationView.setVisibility(View.GONE);
                return;
        }
        bottomNavigationView.setVisibility(View.VISIBLE);
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
        setContentView(R.layout.activity_main);
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
                    presenter.homeSelected(false);
                    break;
                case R.id.nav_favorites:
                    presenter.favoritesSelected(false);
                    break;
                case R.id.nav_profile:
                    presenter.profileSelected(false);
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
        bottomNavigationView = findViewById(R.id.main_bottom_navigator);
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.bottom_action_home:
                    presenter.homeSelected(true);
                    return true;
                case R.id.bottom_action_favorites:
                    presenter.favoritesSelected(true);
                    return true;
                case R.id.bottom_action_profile:
                    presenter.profileSelected(true);
                    return true;
            }
            return false;
        });
    }
}
