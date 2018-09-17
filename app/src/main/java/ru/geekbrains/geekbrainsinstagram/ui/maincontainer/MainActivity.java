package ru.geekbrains.geekbrainsinstagram.ui.maincontainer;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import javax.inject.Inject;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import ru.geekbrains.domain.model.AppThemeModel;
import ru.geekbrains.geekbrainsinstagram.MainApplication;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.model.ViewPhotoModel;
import ru.geekbrains.geekbrainsinstagram.ui.mediator.ActivityToFragmentMediator;
import ru.geekbrains.geekbrainsinstagram.ui.mediator.IActivityToFragmentMediator;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.INavigator;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.Screen;

public final class MainActivity extends AppCompatActivity implements IMainPresenter.IView {

    private static final String CURRENT_SCREEN = "current_screen";

    @Inject
    INavigator navigator;

    @Inject
    IActivityToFragmentMediator activityToFragmentMediator;

    @Inject
    IMainPresenter presenter;

    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;
    private boolean isViewSet;
    private Screen currentScreen;
    private Screen.Mapper screenMapper = new Screen.Mapper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        inject();
        presenter.setView(this);
        isViewSet = true;
        presenter.readyToSetupTheme();

        super.onCreate(savedInstanceState);

        setupView();

        activityToFragmentMediator.init(getMediatorHandler());
        navigator.init(getSupportFragmentManager(),
                () -> MainApplication.getApp().getComponentsManager().releaseFragmentComponent());
        presenter.setNavigator(navigator);

        if (savedInstanceState == null) {
            presenter.viewFirstCreated();
        } else {
            currentScreen = screenMapper.getScreen(savedInstanceState.getString(CURRENT_SCREEN));
            presenter.viewRecreated(currentScreen);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!isViewSet) {
            presenter.setView(this);
            isViewSet = true;
        }
        presenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.stop();
        isViewSet = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            release();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(CURRENT_SCREEN, screenMapper.mapScreen(currentScreen));
    }

    @Override
    public void onBackPressed() {
        presenter.backPressed(drawerLayout.isDrawerOpen(GravityCompat.START));
    }

    @Override
    public void closeApp() {
        finish();
    }

    @Override
    public void setTheme(AppThemeModel theme) {
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
    public void setCurrentScreen(Screen screen) {
        currentScreen = screen;
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

    @Override
    public void lockDrawer(boolean isLock) {
        drawerLayout.setDrawerLockMode(isLock ?
                DrawerLayout.LOCK_MODE_LOCKED_CLOSED :
                DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    public void closeDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
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
        setContentView(R.layout.activity_main);
        setupDrawer();
        setupBottomNavigation();
    }

    private void setupDrawer() {
        drawerLayout = findViewById(R.id.main_navigator_layout);
        AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setFillAfter(true);
        animation.setDuration(500);
        drawerLayout.startAnimation(animation);
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

    private ActivityToFragmentMediator.EventHandler getMediatorHandler() {
        return new ActivityToFragmentMediator.EventHandler() {
            @Override
            public void setToolbar(Toolbar toolbar) {
                setSupportActionBar(toolbar);
                setupDrawerListener(toolbar);
            }

            @Override
            public void recreate() {
                MainActivity.this.recreate();
            }

            @Override
            public void openFullSizePhoto(ViewPhotoModel photo) {
                //temporary
//                presenter.openFullSizePhoto(photo);
            }
        };
    }
}
