package ru.geekbrains.geekbrainsinstagram.ui.containers.main;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;
import ru.geekbrains.geekbrainsinstagram.MainApplication;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.di.activity.main.MainActivityComponent;
import ru.geekbrains.geekbrainsinstagram.ui.containers.BaseContainerViewImpl;

public final class MainActivity extends BaseContainerViewImpl<MainPresenter.View, MainPresenter>
        implements MainPresenter.View {

    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void inject() {
        MainActivityComponent component = (MainActivityComponent) MainApplication.getApp()
                .getComponentsManager()
                .getActivityComponent(MainActivity.class);
        component.inject(this);
    }

    @Override
    protected void release() {
        MainApplication.getApp()
                .getComponentsManager()
                .releaseActivityComponent(MainActivity.class);
    }

    @Override
    protected void attachView() {
        presenter.attachView(this);
    }

    @Override
    protected void setupView() {
        setContentView(R.layout.activity_main);
        setupDrawer();
        setupBottomNavigation();
    }

    private void setupDrawer() {
        drawerLayout = findViewById(R.id.main_navigator_layout);
        NavigationView navigationView = findViewById(R.id.main_navigator);
        navigationView.setNavigationItemSelectedListener(getDrawerListener());
    }

    private NavigationView.OnNavigationItemSelectedListener getDrawerListener() {
        return menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.nav_search:
                    presenter.searchSelected(false);
                    break;
                case R.id.nav_camera:
                    presenter.cameraSelected(false);
                    break;
                case R.id.nav_favorites:
                    presenter.favoritesSelected(false);
                    break;
                case R.id.nav_app_theme:
                    presenter.appThemeSelected();
                    break;
            }
            return true;
        };
    }

    private void setupBottomNavigation() {
        bottomNavigationView = findViewById(R.id.main_bottom_navigator);
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.bottom_action_search:
                    presenter.searchSelected(true);
                    return true;
                case R.id.bottom_action_camera:
                    presenter.cameraSelected(true);
                    return true;
                case R.id.bottom_action_favorites:
                    presenter.favoritesSelected(true);
                    return true;
            }
            return false;
        });
    }
}
