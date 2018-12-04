package ru.geekbrains.pictureapp.presentation.ui.screens.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import ru.geekbrains.pictureapp.R;
import ru.geekbrains.pictureapp.presentation.MainApplication;
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
    private RecyclerView imagesView;
    private FavoritesAdapter adapter;
    private boolean isViewSet;

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }

    //region overrides Fragment methods
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        inject();
        super.onCreate(savedInstanceState);
    }

    private void inject() {
        MainApplication.getApp()
                .getComponentsManager()
                .getHomeComponent()
                .inject(this);
    }

    @NonNull
    @Override
    public android.view.View onCreateView(@NonNull LayoutInflater inflater,
                                          @Nullable ViewGroup container,
                                          @Nullable Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.fragment_favorites, container, false);

        initView(layout);
        initToolbar(layout);
        preparePresenter();

        return layout;
    }

    private void initView(final View layout) {
        mainLayout = layout.findViewById(R.id.cl_main_layout);
        imagesView = layout.findViewById(R.id.rv_photos);
        imagesView.setLayoutManager(layoutUtils.getAdjustedGridLayoutManager());
    }

    private void initToolbar(final View layout) {
        final Toolbar toolbar = layout.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.toolbar_favorites_title);
        mediator.setupToolbar(toolbar, Screens.HOME_SCREEN);
    }

    private void preparePresenter() {
        presenter.attachView(this);
        isViewSet = true;
        presenter.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        setViewToPresenter();
        presenter.start();
    }

    private void setViewToPresenter() {
        if (!isViewSet) {
            presenter.attachView(this);
            presenter.attachListView(adapter);
            isViewSet = true;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stop();
        isViewSet = false;
    }
    //endregion

    //region implements FavoritesView
    @Override
    public void init(final FavoritesListPresenter listPresenter) {
        adapter = new FavoritesAdapter(listPresenter, pictureUtils);
        imagesView.setAdapter(adapter);
        this.presenter.attachListView(adapter);
    }

    @Override
    public void showErrorDeletingFromFavoritesMessage() {
        showMessage(R.string.error_delete_photo_from_favorites_message);
    }

    @Override
    public void showErrorDeletingFromDeviceMessage() {
        showMessage(R.string.error_delete_photo_from_device_message);
    }

    private void showMessage(@StringRes int message) {
        Snackbar.make(mainLayout, message, Snackbar.LENGTH_SHORT).show();
    }
    //endregion
}
