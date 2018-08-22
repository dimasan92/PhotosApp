package ru.geekbrains.geekbrainsinstagram.ui;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;

import javax.inject.Inject;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import io.reactivex.functions.Consumer;
import ru.geekbrains.geekbrainsinstagram.MainApplication;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.di.activity.ActivityComponent;
import ru.geekbrains.geekbrainsinstagram.di.fragment.FragmentComponent;
import ru.geekbrains.geekbrainsinstagram.navigator.Navigator;
import ru.geekbrains.geekbrainsinstagram.utils.SettingsPrefsUtils;

public final class MainActivity extends AppCompatActivity implements MainContract.View {

    @Inject
    Navigator navigator;

    @Inject
    MainContract.Presenter presenter;

    private DrawerLayout drawerLayout;

    private NavigationView.OnNavigationItemSelectedListener drawerListener = menuItem -> {
        switch (menuItem.getItemId()) {
            case R.id.nav_main:
                break;
            case R.id.nav_theme_chooser:
                navigator.navigateToColorChooser();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupComponent();
        setTheme(presenter.setupTheme());
        super.onCreate(savedInstanceState);
        presenter.setView(this);
        navigator.setFragmentManager(getSupportFragmentManager());
        setContentView(R.layout.activity_main);

        setupActivityView();
        navigator.initializeView();

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

    private void setupComponent() {
        MainApplication.getApp()
                .getComponentsManager()
                .getActivityComponent().inject(this);
    }

    private void setupActivityView() {
        Toolbar toolbar = setupToolbar();
        setupDrawer(toolbar);

        NavigationView navigationView = findViewById(R.id.main_navigator);
        navigationView.setNavigationItemSelectedListener(drawerListener);
    }

    private Toolbar setupToolbar() {
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    private void setupDrawer(Toolbar toolbar) {
        drawerLayout = findViewById(R.id.main_navigator_layout);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) drawerLayout.getLayoutParams();
        params.width = metrics.widthPixels;
        drawerLayout.setLayoutParams(params);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }
}
