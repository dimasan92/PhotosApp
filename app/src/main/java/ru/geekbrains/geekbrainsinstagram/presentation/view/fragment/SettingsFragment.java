package ru.geekbrains.geekbrainsinstagram.presentation.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import ru.geekbrains.geekbrainsinstagram.R;

public class SettingsFragment extends Fragment {

    @BindView(R.id.btn_red_theme)
    Button mBtnRedTheme;
    @BindView(R.id.btn_blue_theme)
    Button mBtnBlueTheme;
    @BindView(R.id.btn_green_theme)
    Button mBtnGreenTheme;

    private Consumer<Integer> mThemeObserver;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void addThemeObserver(Consumer<Integer> observer) {
        mThemeObserver = observer;
    }

    @OnClick(R.id.btn_red_theme)
    void changeToRedTheme() {
        Single.just(R.style.RedAppTheme).subscribe(mThemeObserver).dispose();
    }

    @OnClick(R.id.btn_blue_theme)
    void changeToBlueTheme() {
        Single.just(R.style.BlueAppTheme).subscribe(mThemeObserver).dispose();
    }

    @OnClick(R.id.btn_green_theme)
    void changeToGreenTheme() {
        Single.just(R.style.GreenAppTheme).subscribe(mThemeObserver).dispose();
    }
}
