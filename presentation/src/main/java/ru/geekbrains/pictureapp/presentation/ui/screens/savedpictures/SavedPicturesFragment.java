package ru.geekbrains.pictureapp.presentation.ui.screens.savedpictures;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import ru.geekbrains.pictureapp.R;
import ru.geekbrains.pictureapp.presentation.MainApplication;
import ru.geekbrains.pictureapp.presentation.ui.screens.savedpictures.SavedPicturesPresenter.SavedPicturesView;
import ru.geekbrains.pictureapp.presentation.util.LayoutUtils;
import ru.geekbrains.pictureapp.presentation.util.PictureUtils;

public final class SavedPicturesFragment extends Fragment implements SavedPicturesView {

    @Inject LayoutUtils layoutUtils;
    @Inject PictureUtils pictureUtils;
    @Inject SavedPicturesPresenter presenter;

    private RecyclerView photosView;
    private SavedPicturesAdapter adapter;
    private boolean isViewSet;

    public static SavedPicturesFragment newInstance() {
        return new SavedPicturesFragment();
    }

    //region overrides Fragment methods
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isViewSet && isVisibleToUser) {
            presenter.userVisibleHint();
        }
    }

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
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.fragment_search_saved_list, container, false);

        initView(layout);
        preparePresenter();

        return layout;
    }

    private void initView(final View layout) {
        photosView = layout.findViewById(R.id.rv_photos);
        photosView.setLayoutManager(layoutUtils.getAdjustedGridLayoutManager());
        if (photosView.getItemAnimator() != null) {
            photosView.getItemAnimator().setChangeDuration(0);
        }
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

    //region implements SavedPicturesView
    @Override
    public void init(final SavedPicturesListPresenter listPresenter) {
        adapter = new SavedPicturesAdapter(listPresenter, pictureUtils);
        photosView.setAdapter(adapter);
        presenter.attachListView(adapter);
    }

    @Override
    public void showErrorAddingToFavoritesMessage() {
        showMessage(R.string.error_add_photo_to_favorites_message);
    }

    @Override
    public void showErrorDeletingFromFavoritesMessage() {
        showMessage(R.string.error_delete_photo_from_favorites_message);
    }

    @Override
    public void showErrorDeletingPhoto() {
        showMessage(R.string.error_delete_photo_message);
    }

    private void showMessage(@StringRes int message) {
        if (getView() == null) {
            return;
        }
        Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
    }
    //endregion
}
