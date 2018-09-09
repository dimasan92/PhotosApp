package ru.geekbrains.geekbrainsinstagram.ui.screens.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import ru.geekbrains.geekbrainsinstagram.MainApplication;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.base.BaseFragment;
import ru.geekbrains.geekbrainsinstagram.model.PresentPhotoModel;
import ru.geekbrains.geekbrainsinstagram.ui.mediator.IActivityToFragmentMediator;
import ru.geekbrains.geekbrainsinstagram.util.ILayoutUtils;
import ru.geekbrains.geekbrainsinstagram.util.IPictureUtils;

public final class FavoritesFragment extends BaseFragment {

    @Inject
    ILayoutUtils layoutUtils;

    @Inject
    IPictureUtils pictureUtils;

    @Inject
    IActivityToFragmentMediator activityToFragmentMediator;

    private FavoritesAdapter adapter;

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        inject();
        initRecyclerView(view);
        activityToFragmentMediator.setupToolbar(view.findViewById(R.id.favorites_toolbar));

        return view;
    }

    private void inject() {
        MainApplication.getApp().getComponentsManager().getFragmentComponent().inject(this);
    }

    private void initRecyclerView(View layout) {
        RecyclerView favoritesRecyclerView = layout.findViewById(R.id.photos_recycler_view);
        favoritesRecyclerView.setLayoutManager(layoutUtils.getAdjustedGridLayoutManager());
        adapter = new FavoritesAdapter(pictureUtils, adapterListener());
        favoritesRecyclerView.setAdapter(adapter);
    }

    private FavoritesAdapter.IFavoriteListener adapterListener() {
        return new FavoritesAdapter.IFavoriteListener() {
            @Override
            public void onDeleteFromFavoritesClick(PresentPhotoModel photo) {

            }

            @Override
            public void onDeleteFromDeviceClick(PresentPhotoModel photo) {

            }
        };
    }
}
