package ru.geekbrains.geekbrainsinstagram.ui.screens.theme;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import ru.geekbrains.geekbrainsinstagram.MainApplication;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.ui.container.mediator.ContainerToContentMediator;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.Screens;
import ru.geekbrains.geekbrainsinstagram.ui.screens.BackListener;
import ru.geekbrains.geekbrainsinstagram.ui.screens.theme.AppThemePresenter.ThemeView;

public final class AppThemeFragment extends Fragment implements ThemeView, BackListener {

    @Inject ContainerToContentMediator mediator;
    @Inject AppThemePresenter presenter;

    public static AppThemeFragment newInstance() {
        return new AppThemeFragment();
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        inject();
        super.onCreate(savedInstanceState);
    }

    @NonNull @Override public View onCreateView(@NonNull LayoutInflater inflater,
                                                @Nullable ViewGroup container,
                                                @Nullable Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.fragment_app_theme, container, false);

        final Toolbar toolbar = layout.findViewById(R.id.app_theme_toolbar);
        mediator.setupToolbar(toolbar, Screens.APP_THEME_SCREEN);
        setListeners(layout, toolbar);

        return layout;
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

    @Override public void applyTheme() {
        mediator.applyTheme();
    }

    @Override public void release() {
        MainApplication.getApp()
                .getComponentsManager()
                .releaseSettingsComponent();
    }

    private void inject() {
        MainApplication.getApp()
                .getComponentsManager()
                .getSettingsComponent()
                .inject(this);
    }

    private void setListeners(final View layout, final Toolbar toolbar) {
        toolbar.setNavigationOnClickListener(v -> presenter.back());
        layout.findViewById(R.id.btn_red_theme).setOnClickListener(v -> presenter.redThemeSelected());
        layout.findViewById(R.id.btn_blue_theme).setOnClickListener(v -> presenter.blueThemeSelected());
        layout.findViewById(R.id.btn_green_theme).setOnClickListener(v -> presenter.greenThemeSelected());
    }
}
