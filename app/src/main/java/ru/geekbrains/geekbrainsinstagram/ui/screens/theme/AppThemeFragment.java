package ru.geekbrains.geekbrainsinstagram.ui.screens.theme;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import ru.geekbrains.geekbrainsinstagram.MainApplication;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.di.fragment.apptheme.AppThemeFragmentComponent;
import ru.geekbrains.geekbrainsinstagram.ui.containers.settings.mediator.SettingsContainerToContentMediator;

public final class AppThemeFragment extends Fragment implements AppThemePresenter.View {

    @Inject
    SettingsContainerToContentMediator mediator;

    @Inject
    AppThemePresenter presenter;

    public static AppThemeFragment newInstance() {
        return new AppThemeFragment();
    }

    @NonNull
    @Override
    public android.view.View onCreateView(@NonNull LayoutInflater inflater,
                                          @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_app_theme, container, false);

        inject();
        setListeners(layout);

        return layout;
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

    @Override
    public void applyTheme() {
        mediator.themeChanged();
    }

    private void inject() {
        AppThemeFragmentComponent component = (AppThemeFragmentComponent) MainApplication.getApp()
                .getComponentsManager().getFragmentComponent(AppThemeFragment.class);
        component.inject(this);
    }

    private void setListeners(View layout) {
        layout.findViewById(R.id.btn_red_theme).setOnClickListener(v -> presenter.redThemeSelected());
        layout.findViewById(R.id.btn_blue_theme).setOnClickListener(v -> presenter.blueThemeSelected());
        layout.findViewById(R.id.btn_green_theme).setOnClickListener(v -> presenter.greenThemeSelected());
    }
}
