package ru.geekbrains.pictureapp.presentation.ui.screens.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;
import com.google.android.material.navigation.NavigationView;

import javax.inject.Inject;

import ru.geekbrains.pictureapp.R;
import ru.geekbrains.pictureapp.presentation.MainApplication;
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

    //region overrides Fragment methods
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        inject();
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void inject() {
        MainApplication.getApp()
                .getComponentsManager()
                .getHomeComponent()
                .inject(this);
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.fragment_container_home, container, false);

        setupView(layout);
        preparePresenter(savedInstanceState == null && firstCreated);

        return layout;
    }

    private void setupView(final View layout) {
        drawerLayout = layout.findViewById(R.id.home_drawer_navigation_layout);
        final NavigationView navigationView = layout.findViewById(R.id.home_drawer_navigation);
        navigationView.setNavigationItemSelectedListener(getDrawerListener());

        bottomNavigationView = layout.findViewById(R.id.home_bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(getBottomNavigationListener());
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

    private OnNavigationItemSelectedListener getBottomNavigationListener() {
        return menuItem -> {
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
        };
    }

    private void preparePresenter(final boolean isFirstCreated) {
        navigator.init(getChildFragmentManager());
        presenter.setNavigator(navigator);
        if (isFirstCreated) {
            presenter.firstCreated();
            firstCreated = false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.attachView(this);
        presenter.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stop();
    }
    //endregion

    //region implements BackListener
    @Override
    public void onBackPressed() {
        presenter.back();
    }
    //endregion

    //region implements HomeView
    @Override
    public void setNavigationState(final Screens screen) {
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
        }
    }

    @Override
    public void closeDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    //endregion
}
