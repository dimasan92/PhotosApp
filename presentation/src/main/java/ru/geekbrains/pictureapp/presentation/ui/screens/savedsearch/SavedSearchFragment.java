package ru.geekbrains.pictureapp.presentation.ui.screens.savedsearch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import ru.geekbrains.pictureapp.presentation.MainApplication;
import ru.geekbrains.pictureapp.R;
import ru.geekbrains.pictureapp.presentation.ui.screens.savedsearch.SavedSearchPresenter.SavedSearchView;
import ru.geekbrains.pictureapp.presentation.util.LayoutUtils;
import ru.geekbrains.pictureapp.presentation.util.PictureUtils;

public final class SavedSearchFragment extends Fragment implements SavedSearchView {

    @Inject LayoutUtils layoutUtils;
    @Inject PictureUtils pictureUtils;
    @Inject SavedSearchPresenter presenter;

    private RecyclerView photosRecyclerView;
    private SavedSearchAdapter adapter;
    private boolean isViewSet;

    public static SavedSearchFragment newInstance() {
        return new SavedSearchFragment();
    }

    @Override public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isViewSet && isVisibleToUser){
            presenter.userVisibleHint();
        }
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        inject();
        super.onCreate(savedInstanceState);
    }

    @NonNull @Override public View onCreateView(@NonNull LayoutInflater inflater,
                                                @Nullable ViewGroup container,
                                                @Nullable Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.fragment_search_saved_list, container, false);

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

    private void inject() {
        MainApplication.getApp()
                .getComponentsManager()
                .getHomeComponent()
                .inject(this);
    }

    @Override public void init(final SavedSearchListPresenter listPresenter) {
        adapter = new SavedSearchAdapter(listPresenter, pictureUtils);
        photosRecyclerView.setAdapter(adapter);
        presenter.attachListView(adapter);
    }

    @Override public void showErrorAddingToFavoritesMessage() {
        Snackbar.make(Objects.requireNonNull(getView()),
                R.string.error_add_photo_to_favorites_message, Snackbar.LENGTH_SHORT).show();
    }

    @Override public void showErrorDeletingFromFavoritesMessage() {
        Snackbar.make(Objects.requireNonNull(getView()),
                R.string.error_delete_photo_from_favorites_message, Snackbar.LENGTH_SHORT).show();
    }

    @Override public void showErrorDeletingPhoto() {
        Snackbar.make(Objects.requireNonNull(getView()),
                R.string.error_delete_photo_message, Snackbar.LENGTH_SHORT).show();
    }

    private void initView(final View layout) {
        photosRecyclerView = layout.findViewById(R.id.rv_photos);
        photosRecyclerView.setLayoutManager(layoutUtils.getAdjustedGridLayoutManager());
        if (photosRecyclerView.getItemAnimator() != null) {
            photosRecyclerView.getItemAnimator().setChangeDuration(0);
        }
    }

    private void preparePresenter() {
        presenter.attachView(this);
        isViewSet = true;
        presenter.create();
    }
}
