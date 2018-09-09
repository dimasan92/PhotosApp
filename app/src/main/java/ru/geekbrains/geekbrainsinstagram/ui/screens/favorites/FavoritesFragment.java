package ru.geekbrains.geekbrainsinstagram.ui.screens.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import ru.geekbrains.geekbrainsinstagram.MainApplication;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.base.BaseFragment;
import ru.geekbrains.geekbrainsinstagram.ui.mediator.IActivityToFragmentMediator;

public final class FavoritesFragment extends BaseFragment {

    @Inject
    IActivityToFragmentMediator activityToFragmentMediator;

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        inject();
        activityToFragmentMediator.setupToolbar(view.findViewById(R.id.favorites_toolbar));

        return view;
    }

    private void inject() {
        MainApplication.getApp().getComponentsManager().getFragmentComponent().inject(this);
    }
}
