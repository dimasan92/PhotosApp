package ru.geekbrains.geekbrainsinstagram.ui.screens.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import ru.geekbrains.geekbrainsinstagram.MainApplication;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.base.BaseFragment;
import ru.geekbrains.geekbrainsinstagram.model.ViewPhotoModel;
import ru.geekbrains.geekbrainsinstagram.ui.common.NotifyingMessage;
import ru.geekbrains.geekbrainsinstagram.ui.mediator.IActivityToFragmentMediator;
import ru.geekbrains.geekbrainsinstagram.util.LayoutUtils;
import ru.geekbrains.geekbrainsinstagram.util.PictureUtils;

public final class FavoritesFragment extends BaseFragment implements IFavoritesPresenter.IView {

    @Inject
    LayoutUtils layoutUtils;

    @Inject
    PictureUtils pictureUtils;

    @Inject
    IActivityToFragmentMediator activityToFragmentMediator;

    @Inject
    IFavoritesPresenter presenter;

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
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.appbar_favorites_title);
        activityToFragmentMediator.setupToolbar(toolbar);

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
    public void addPhotos(List<ViewPhotoModel> photos) {
        adapter.updatePhotos(photos);
    }

    @Override
    public void deletePhoto(ViewPhotoModel photo) {
        adapter.deletePhoto(photo);
    }

    @Override
    public void showNotifyingMessage(NotifyingMessage message) {
        switch (message){
            case PHOTO_SUCCESSFULLY_DELETED_FROM_FAVORITES:
                showNotifyingMessage(R.string.photo_successfully_deleted_from_favorites_message);
                break;
            case PHOTO_SUCCESSFULLY_DELETED_FROM_DEVICE:
                showNotifyingMessage(R.string.photo_successfully_deleted_from_device_message);
                break;
            case ERROR_DELETE_PHOTO_FROM_FAVORITES:
                showNotifyingMessage(R.string.error_delete_photo_from_favorites_message);
                break;
            case ERROR_DELETE_PHOTO_FROM_DEVICE:
                showNotifyingMessage(R.string.error_delete_photo_from_device_message);
                break;
        }
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
            public void onDeleteFromFavoritesClick(ViewPhotoModel photo) {
                presenter.deletePhotoFromFavorites(photo);
            }

            @Override
            public void onDeleteFromDeviceClick(ViewPhotoModel photo) {
                presenter.deletePhotoFromDevice(photo);
            }

            @Override
            public void onDetailsClick(ViewPhotoModel photo) {
//                activityToFragmentMediator.openFullSizePhoto(photo);
            }
        };
    }

    private void showNotifyingMessage(@StringRes int messageId) {
        if (getView() == null) {
            return;
        }
        Snackbar.make(getView(), messageId, Snackbar.LENGTH_SHORT).show();
    }
}
