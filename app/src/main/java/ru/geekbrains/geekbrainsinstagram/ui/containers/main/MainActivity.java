package ru.geekbrains.geekbrainsinstagram.ui.containers.main;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import javax.inject.Inject;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import ru.geekbrains.geekbrainsinstagram.MainApplication;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.di.activity.main.MainActivityComponent;
import ru.geekbrains.geekbrainsinstagram.ui.containers.BaseContainerViewImpl;
import ru.geekbrains.geekbrainsinstagram.ui.containers.main.mediator.MainContainerToContentMediator;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.androidxcicerone.SupportAppNavigator;
import ru.terrakok.cicerone.Navigator;

public final class MainActivity extends BaseContainerViewImpl<MainPresenter.View, MainPresenter>
        implements MainPresenter.View {

    @Inject
    MainContainerToContentMediator mediator;

    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mediator.init(this::initToolbar);
    }

    @Override
    protected void inject() {
        MainActivityComponent component = (MainActivityComponent) MainApplication.getApp()
                .getComponentsManager()
                .getActivityComponent(MainActivity.class);
        component.inject(this);
    }

    @Override
    public void setMainScreenNavigationState(MainScreenNavigationState state) {
        switch (state) {
            case SEARCH_STATE:
                bottomNavigationView.setSelectedItemId(R.id.bottom_action_search);
                break;
            case CAMERA_PHOTOS_STATE:
                bottomNavigationView.setSelectedItemId(R.id.bottom_action_camera);
                break;
            case FAVORITES_STATE:
                bottomNavigationView.setSelectedItemId(R.id.bottom_action_favorites);
                break;
            default:
                throw new IllegalArgumentException("Wrong navigation state argument");
        }
    }

    @Override
    public void closeDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
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
    protected Navigator getNavigator() {
        return new SupportAppNavigator(this, R.id.main_fragment_container);
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

    private void initToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }
}
