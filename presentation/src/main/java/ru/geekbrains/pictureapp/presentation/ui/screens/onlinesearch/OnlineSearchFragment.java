package ru.geekbrains.pictureapp.presentation.ui.screens.onlinesearch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import ru.geekbrains.pictureapp.presentation.MainApplication;
import ru.geekbrains.pictureapp.R;
import ru.geekbrains.pictureapp.presentation.ui.screens.onlinesearch.OnlineSearchPresenter.OnlineSearchView;
import ru.geekbrains.pictureapp.presentation.util.LayoutUtils;
import ru.geekbrains.pictureapp.presentation.util.PictureUtils;

public final class OnlineSearchFragment extends Fragment implements OnlineSearchView {

    @Inject LayoutUtils layoutUtils;
    @Inject PictureUtils pictureUtils;
    @Inject OnlineSearchPresenter presenter;

    private RecyclerView photosRecyclerView;
    private OnlineSearchAdapter adapter;
    private boolean isViewSet;

    public static OnlineSearchFragment newInstance() {
        return new OnlineSearchFragment();
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
        final View layout = inflater.inflate(R.layout.fragment_search_online_list, container, false);

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

    @Override public void onResume() {
        super.onResume();
    }

    @Override public void init(final OnlineSearchListPresenter listPresenter) {
        adapter = new OnlineSearchAdapter(listPresenter, pictureUtils);
        photosRecyclerView.setAdapter(adapter);
        presenter.attachListView(adapter);
    }

    @Override public void showErrorNetworkMessage() {
        Snackbar.make(Objects.requireNonNull(getView()),
                R.string.error_network_connection_message, Snackbar.LENGTH_SHORT).show();
    }

    @Override public void showErrorDownloadingPhotosMessage() {
        Snackbar.make(Objects.requireNonNull(getView()),
                R.string.error_download_photos_message, Snackbar.LENGTH_SHORT).show();
    }

    @Override public void showErrorAddingToFavoritesMessage() {
        Snackbar.make(Objects.requireNonNull(getView()),
                R.string.error_add_photo_to_favorites_message, Snackbar.LENGTH_SHORT).show();
    }

    @Override public void showErrorDeletingFromFavoritesMessage() {
        Snackbar.make(Objects.requireNonNull(getView()),
                R.string.error_delete_photo_from_favorites_message, Snackbar.LENGTH_SHORT).show();
    }

    @Override public void showErrorSavingPhoto() {
        Snackbar.make(Objects.requireNonNull(getView()),
                R.string.error_save_photo_message, Snackbar.LENGTH_SHORT).show();
    }

    @Override public void showErrorDeletingPhoto() {
        Snackbar.make(Objects.requireNonNull(getView()),
                R.string.error_delete_photo_message, Snackbar.LENGTH_SHORT).show();
    }

    private void inject() {
        MainApplication.getApp()
                .getComponentsManager()
                .getHomeComponent()
                .inject(this);
    }

    private void initView(final View layout) {
        photosRecyclerView = layout.findViewById(R.id.rv_photos);
        photosRecyclerView.setLayoutManager(layoutUtils.getAdjustedGridLayoutManager());
        if (photosRecyclerView.getItemAnimator() != null) {
            photosRecyclerView.getItemAnimator().setChangeDuration(0);
        }

        final SearchView searchView = layout.findViewById(R.id.sv_photos_query);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) {
                presenter.onSearchClick(query);
                return true;
            }

            @Override public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void preparePresenter() {
        presenter.attachView(this);
        isViewSet = true;
        presenter.create();
    }
}
