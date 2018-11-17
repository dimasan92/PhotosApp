package ru.geekbrains.pictureapp.presentation.ui.screens.favorites;

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
import ru.geekbrains.pictureapp.presentation.MainApplication;
import ru.geekbrains.pictureapp.R;
import ru.geekbrains.pictureapp.presentation.ui.container.mediator.ContainerToContentMediator;
import ru.geekbrains.pictureapp.presentation.ui.navigator.Screens;
import ru.geekbrains.pictureapp.presentation.ui.screens.favorites.FavoritesPresenter.FavoritesView;
import ru.geekbrains.pictureapp.presentation.util.LayoutUtils;
import ru.geekbrains.pictureapp.presentation.util.PictureUtils;

public final class FavoritesFragment extends Fragment implements FavoritesView {

    @Inject LayoutUtils layoutUtils;
    @Inject PictureUtils pictureUtils;
    @Inject ContainerToContentMediator mediator;
    @Inject FavoritesPresenter presenter;

    private CoordinatorLayout mainLayout;
    private RecyclerView photosRecyclerView;
    private FavoritesAdapter adapter;
    private boolean isViewSet;

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        inject();
        super.onCreate(savedInstanceState);
    }

    @NonNull @Override public android.view.View onCreateView(@NonNull LayoutInflater inflater,
                                                             @Nullable ViewGroup container,
                                                             @Nullable Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.fragment_favorites, container, false);

        initView(layout);
        preparePresenter();

        return layout;
    }

    @Override public void onStart() {
        super.onStart();
        if (!isViewSet) {
            presenter.attachView(this);
            presenter.attachListView(adapter);
            isViewSet = true;
        }
        presenter.start();
    }

    @Override public void onStop() {
        super.onStop();
        presenter.stop();
        isViewSet = false;
    }

    @Override public void init(final FavoritesListPresenter presenter) {
        adapter = new FavoritesAdapter(presenter, pictureUtils);
        photosRecyclerView.setAdapter(adapter);
        this.presenter.attachListView(adapter);
    }

    @Override public void showSuccessDeletedFromFavoritesMessage() {
        Snackbar.make(mainLayout, R.string.photo_successfully_deleted_from_favorites_message, Snackbar.LENGTH_SHORT).show();
    }

    @Override public void showSuccessDeletedFromDeviceMessage() {
        Snackbar.make(mainLayout, R.string.photo_successfully_deleted_from_device_message, Snackbar.LENGTH_SHORT).show();
    }

    @Override public void showErrorDeletingFromFavoritesMessage() {
        Snackbar.make(mainLayout, R.string.error_delete_photo_from_favorites_message, Snackbar.LENGTH_SHORT).show();
    }

    @Override public void showErrorDeletingFromDeviceMessage() {
        Snackbar.make(mainLayout, R.string.error_delete_photo_from_device_message, Snackbar.LENGTH_SHORT).show();
    }

    private void inject() {
        MainApplication.getApp()
                .getComponentsManager()
                .getHomeComponent()
                .inject(this);
    }

    private void initView(final View layout) {
        mainLayout = layout.findViewById(R.id.cl_main_layout);
        photosRecyclerView = layout.findViewById(R.id.rv_photos);
        photosRecyclerView.setLayoutManager(layoutUtils.getAdjustedGridLayoutManager());
        final Toolbar toolbar = layout.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.toolbar_favorites_title);
        mediator.setupToolbar(toolbar, Screens.HOME_SCREEN);
    }

    private void preparePresenter() {
        presenter.attachView(this);
        isViewSet = true;
        presenter.create();
    }
}
