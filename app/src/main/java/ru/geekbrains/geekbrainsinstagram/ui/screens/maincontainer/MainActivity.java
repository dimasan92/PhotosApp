package ru.geekbrains.geekbrainsinstagram.ui.screens.maincontainer;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import ru.geekbrains.geekbrainsinstagram.model.AppTheme;

public final class MainActivity extends AppCompatActivity implements IMainPresenter.IView {

    @Inject
    INavigator navigator;

    @Inject
    IMainPresenter presenter;

    private DrawerLayout drawerLayout;
    private FloatingActionButton fab;

    private NavigationView.OnNavigationItemSelectedListener drawerListener = menuItem -> {
        switch (menuItem.getItemId()) {
            case R.id.nav_personal_photos:
                fab.show();
                navigator.navigateToPersonalPhotos();
                break;
            case R.id.nav_app_theme:
                fab.hide();
                navigator.navigateToAppTheme();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        inject();
        presenter.setView(this);
        presenter.readyToSetupTheme();

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setupView();

        navigator.init(getSupportFragmentManager());
        if (savedInstanceState == null) {
            navigator.navigateToPersonalPhotos();
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
        fab = findViewById(R.id.main_fab);
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

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setupNavigationView() {
        NavigationView navigationView = findViewById(R.id.main_navigator);
        navigationView.setNavigationItemSelectedListener(drawerListener);
    }
}
