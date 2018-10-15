package ru.geekbrains.geekbrainsinstagram.ui.screens.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import ru.geekbrains.geekbrainsinstagram.MainApplication;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.di.fragment.favorites.FavoritesFragmentComponent;
import ru.geekbrains.geekbrainsinstagram.ui.containers.main.mediator.MainContainerToContentMediator;
import ru.geekbrains.geekbrainsinstagram.ui.screens.favorites.FavoritesPresenter.FavoritesView;
import ru.geekbrains.geekbrainsinstagram.util.LayoutUtils;
import ru.geekbrains.geekbrainsinstagram.util.PictureUtils;

public final class FavoritesFragment extends Fragment implements FavoritesView {

    @Inject
    LayoutUtils layoutUtils;

    @Inject
    PictureUtils pictureUtils;

    @Inject
    MainContainerToContentMediator mediator;

    @Inject
    FavoritesPresenter presenter;

    private CoordinatorLayout mainLayout;
    private RecyclerView photosRecyclerView;
    private FavoritesAdapter adapter;
    private boolean isViewSet;

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }

    @NonNull
    @Override
    public android.view.View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                          @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        inject();
        initView(view);

        presenter.attachView(this);
        isViewSet = true;
        presenter.create();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!isViewSet) {
            presenter.attachView(this);
            presenter.attachListView(adapter);
            isViewSet = true;
        }
        presenter.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stop();
        isViewSet = false;
    }

    @Override
    public void init(final FavoritesListPresenter presenter) {
        adapter = new FavoritesAdapter(presenter, pictureUtils);
        photosRecyclerView.setAdapter(adapter);
        this.presenter.attachListView(adapter);
    }

    @Override
    public void showSuccessDeleteFromFavoritesMessage() {
        Snackbar.make(mainLayout, R.string.photo_successfully_deleted_from_favorites_message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccessDeleteFromDeviceMessage() {
        Snackbar.make(mainLayout, R.string.photo_successfully_deleted_from_device_message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorDeleteFromFavoritesMessage() {
        Snackbar.make(mainLayout, R.string.error_delete_photo_from_favorites_message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorDeleteFromDeviceMessage() {
        Snackbar.make(mainLayout, R.string.error_delete_photo_from_device_message, Snackbar.LENGTH_SHORT).show();
    }

    private void inject() {
        FavoritesFragmentComponent component = (FavoritesFragmentComponent) MainApplication.getApp()
                .getComponentsManager().getFragmentComponent(FavoritesFragment.class);
        component.inject(this);
    }

    private void initView(android.view.View layout) {
        mainLayout = layout.findViewById(R.id.cl_main_layout);
        photosRecyclerView = layout.findViewById(R.id.rv_photos);
        photosRecyclerView.setLayoutManager(layoutUtils.getAdjustedGridLayoutManager());
        Toolbar toolbar = layout.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.appbar_favorites_title);
        mediator.setupToolbar(toolbar);
    }
}
