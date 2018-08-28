package ru.geekbrains.geekbrainsinstagram.ui.screens.maincontainer;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;

import com.google.android.material.navigation.NavigationView;

import javax.inject.Inject;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import ru.geekbrains.geekbrainsinstagram.MainApplication;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.INavigator;

public final class MainActivity extends AppCompatActivity implements IMainPresenter.IView {

    @Inject
    INavigator navigator;

    @Inject
    IMainPresenter presenter;

    private DrawerLayout drawerLayout;

    private NavigationView.OnNavigationItemSelectedListener drawerListener = menuItem -> {
        switch (menuItem.getItemId()) {
            case R.id.nav_personal_photos:
                navigator.navigateToPersonalPhotos();
                break;
            case R.id.nav_app_theme:
                navigator.navigateToAppTheme();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        inject();
        presenter.readyToSetupTheme(this::setTheme);

        super.onCreate(savedInstanceState);
        presenter.setView(this);

        setContentView(R.layout.activity_main);
        setupView();

        navigator.init(getSupportFragmentManager());
        if (savedInstanceState == null) {
            navigator.navigateToPersonalPhotos();
        }
        presenter.viewIsReady();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void inject() {
        MainApplication.getApp()
                .getComponentsManager()
                .getActivityComponent()
                .inject(this);
    }

    private void setupView() {
        Toolbar toolbar = setupAndGetToolbar();
        setupDrawer(toolbar);
    }

    private Toolbar setupAndGetToolbar() {
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    private void setupDrawer(Toolbar toolbar) {
        setupNavigationLayout(toolbar);
        setupNavigationView();
    }

    private void setupNavigationLayout(Toolbar toolbar) {
        drawerLayout = findViewById(R.id.main_navigator_layout);
        drawerLayout.setLayoutParams(adjustParamsForNavigationLayout(drawerLayout.getLayoutParams()));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setupNavigationView() {
        NavigationView navigationView = findViewById(R.id.main_navigator);
        navigationView.setNavigationItemSelectedListener(drawerListener);
    }

    private ViewGroup.LayoutParams adjustParamsForNavigationLayout(ViewGroup.LayoutParams params) {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        params.width = metrics.widthPixels;
        return params;
    }
}
