package ru.geekbrains.geekbrainsinstagram.ui.settings.theme;

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

public final class ColorThemeFragment extends BaseFragment implements ColorThemeContract.View {

    @Inject
    ColorThemeContract.Presenter presenter;

    public static ColorThemeFragment newInstance() {
        return new ColorThemeFragment();
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
        View view = inflater.inflate(R.layout.fragment_color_theme, container, false);

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
    public void recreateActivity() {
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
