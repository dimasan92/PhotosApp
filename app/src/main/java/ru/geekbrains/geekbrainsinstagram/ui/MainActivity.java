package ru.geekbrains.geekbrainsinstagram.ui;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import ru.geekbrains.geekbrainsinstagram.R;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupView();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setupView() {
        Toolbar toolbar = setupToolbar();
        setupDrawer(toolbar);

        NavigationView navigationView = findViewById(R.id.main_navigator);
        navigationView.setNavigationItemSelectedListener(drawerListener());
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

    private NavigationView.OnNavigationItemSelectedListener drawerListener() {
        return menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.nav_main:
                    break;
                case R.id.nav_theme_chooser:
                    break;
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        };
    }
}
