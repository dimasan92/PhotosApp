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

public final class AppThemeFragment extends BaseFragment implements IAppThemePresenter.IView {

    @Inject
    IAppThemePresenter presenter;

    public static AppThemeFragment newInstance() {
        return new AppThemeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApplication.getApp().getComponentsManager().getFragmentComponent()
                .inject(this);
        presenter.setView(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app_theme, container, false);

        setListeners(view);
        presenter.viewIsReady();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }

    @Override
    public void changeTheme() {
        if (getActivity() == null) {
            return;
        }
        getActivity().recreate();
    }

    private void setListeners(View view) {
        Button redThemeButton = view.findViewById(R.id.btn_red_theme);
        redThemeButton.setOnClickListener(v -> presenter.chooseRedTheme());

        Button blueThemeButton = view.findViewById(R.id.btn_blue_theme);
        blueThemeButton.setOnClickListener(v -> presenter.chooseBlueTheme());

        Button greenThemeButton = view.findViewById(R.id.btn_green_theme);
        greenThemeButton.setOnClickListener(v -> presenter.chooseGreenTheme());
    }
}
