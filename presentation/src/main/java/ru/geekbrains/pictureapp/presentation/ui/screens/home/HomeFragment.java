package ru.geekbrains.pictureapp.presentation.ui.screens.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import ru.geekbrains.pictureapp.presentation.MainApplication;
import ru.geekbrains.pictureapp.R;
import ru.geekbrains.pictureapp.presentation.ui.container.mediator.ContainerToContentMediator;
import ru.geekbrains.pictureapp.presentation.ui.navigator.HomeNavigator;
import ru.geekbrains.pictureapp.presentation.ui.navigator.Screens;
import ru.geekbrains.pictureapp.presentation.ui.screens.BackListener;
import ru.geekbrains.pictureapp.presentation.ui.screens.home.HomePresenter.HomeView;

public final class HomeFragment extends Fragment implements HomeView, BackListener {

    @Inject ContainerToContentMediator mediator;
    @Inject HomePresenter presenter;
    @Inject HomeNavigator navigator;

    private boolean firstCreated = true;
    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        inject();
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @NonNull @Override public View onCreateView(@NonNull LayoutInflater inflater,
                                                @Nullable ViewGroup container,
                                                @Nullable Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.fragment_container_home, container, false);

        setupView(layout);

        navigator.init(getChildFragmentManager());
        presenter.setNavigator(navigator);
        if (savedInstanceState == null && firstCreated) {
            presenter.firstCreated();
            firstCreated = false;
        }

        return layout;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override public void onResume() {
        super.onResume();
    }

    @Override public void onStart() {
        super.onStart();
        presenter.attachView(this);
        presenter.start();
    }

    @Override public void onStop() {
        super.onStop();
        presenter.stop();
    }

    @Override public void onBackPressed() {
        presenter.back();
    }

    @Override public void setNavigationState(final Screens screen) {
        switch (screen) {
            case SEARCH_SCREEN:
                bottomNavigationView.setSelectedItemId(R.id.bottom_action_search);
                break;
            case CAMERA_PHOTOS_SCREEN:
                bottomNavigationView.setSelectedItemId(R.id.bottom_action_camera);
                break;
            case FAVORITES_SCREEN:
                bottomNavigationView.setSelectedItemId(R.id.bottom_action_favorites);
                break;
            default:
                throw new IllegalArgumentException("Wrong screen parameter");
        }
    }

    @Override public void closeDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    private void inject() {
        MainApplication.getApp()
                .getComponentsManager()
                .getHomeComponent()
                .inject(this);
    }

    private void setupView(final View layout) {
        setupDrawer(layout);
        setupBottomNavigation(layout);
    }

    private void setupDrawer(final View layout) {
        drawerLayout = layout.findViewById(R.id.home_drawer_navigation_layout);
        NavigationView navigationView = layout.findViewById(R.id.home_drawer_navigation);
        navigationView.setNavigationItemSelectedListener(getDrawerListener());
    }

    private NavigationView.OnNavigationItemSelectedListener getDrawerListener() {
        return menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.drawer_action_search:
                    presenter.searchSelected(false);
                    break;
                case R.id.drawer_action_camera:
                    presenter.cameraSelected(false);
                    break;
                case R.id.drawer_action_favorites:
                    presenter.favoritesSelected(false);
                    break;
                case R.id.drawer_action_app_theme:
                    presenter.appThemeSelected();
                    break;
            }
            return true;
        };
    }

    private void setupBottomNavigation(final View layout) {
        bottomNavigationView = layout.findViewById(R.id.home_bottom_navigation);
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
