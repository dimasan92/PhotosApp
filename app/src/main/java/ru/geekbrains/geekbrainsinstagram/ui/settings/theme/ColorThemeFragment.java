package ru.geekbrains.geekbrainsinstagram.ui.settings.theme;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.base.BaseFragment;
import ru.geekbrains.geekbrainsinstagram.di.fragment.FragmentComponent;

public final class ColorThemeFragment extends BaseFragment {

    @Inject
    ColorThemeContract.Presenter presenter;

    private Consumer<Integer> mThemeObserver;

    public static ColorThemeFragment newInstance() {
        return new ColorThemeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_color_theme, container, false);

        setListeners(view);

        return view;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    private void setListeners(View view) {
        Button redThemeButton = view.findViewById(R.id.btn_red_theme);
        redThemeButton.setOnClickListener(v ->
                Single.just(R.style.RedAppTheme).subscribe(mThemeObserver).dispose());

        Button blueThemeButton = view.findViewById(R.id.btn_blue_theme);
        blueThemeButton.setOnClickListener(v ->
                Single.just(R.style.BlueAppTheme).subscribe(mThemeObserver).dispose());

        Button greenThemeButton = view.findViewById(R.id.btn_green_theme);
        greenThemeButton.setOnClickListener(v ->
                Single.just(R.style.GreenAppTheme).subscribe(mThemeObserver).dispose());
    }

    public void addThemeObserver(Consumer<Integer> observer) {
        mThemeObserver = observer;
    }
}
