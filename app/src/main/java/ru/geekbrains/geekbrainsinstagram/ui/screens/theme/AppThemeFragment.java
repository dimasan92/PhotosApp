package ru.geekbrains.geekbrainsinstagram.ui.screens.theme;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import ru.geekbrains.geekbrainsinstagram.MainApplication;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.base.BaseFragment;
import ru.geekbrains.geekbrainsinstagram.ui.mediator.IActivityToFragmentMediator;

public final class AppThemeFragment extends BaseFragment implements IAppThemePresenter.IView {

    @Inject
    IActivityToFragmentMediator activityToFragmentMediator;

    @Inject
    IAppThemePresenter presenter;

    public static AppThemeFragment newInstance() {
        return new AppThemeFragment();
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app_theme, container, false);

        inject();
        setListeners(view);

        activityToFragmentMediator.setupToolbar(view.findViewById(R.id.app_theme_toolbar));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.setView(this);
        presenter.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stop();
    }

    @Override
    public void applyTheme() {
        activityToFragmentMediator.themeChanged();
    }

    private void inject() {
        MainApplication.getApp().getComponentsManager().getFragmentComponent().inject(this);
    }

    private void setListeners(View view) {
        Button redThemeButton = view.findViewById(R.id.btn_red_theme);
        redThemeButton.setOnClickListener(v -> presenter.redThemeSelected());

        Button blueThemeButton = view.findViewById(R.id.btn_blue_theme);
        blueThemeButton.setOnClickListener(v -> presenter.blueThemeSelected());

        Button greenThemeButton = view.findViewById(R.id.btn_green_theme);
        greenThemeButton.setOnClickListener(v -> presenter.greenThemeSelected());
    }
}
