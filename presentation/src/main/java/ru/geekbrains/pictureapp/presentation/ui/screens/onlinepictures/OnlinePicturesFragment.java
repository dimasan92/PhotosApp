package ru.geekbrains.pictureapp.presentation.ui.screens.onlinepictures;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import ru.geekbrains.pictureapp.R;
import ru.geekbrains.pictureapp.presentation.MainApplication;
import ru.geekbrains.pictureapp.presentation.ui.screens.onlinepictures.OnlinePicturesPresenter.OnlinePicturesView;
import ru.geekbrains.pictureapp.presentation.util.LayoutUtils;
import ru.geekbrains.pictureapp.presentation.util.PictureUtils;

public final class OnlinePicturesFragment extends Fragment implements OnlinePicturesView {

    @Inject LayoutUtils layoutUtils;
    @Inject PictureUtils pictureUtils;
    @Inject OnlinePicturesPresenter presenter;

    private RecyclerView photosView;
    private OnlinePicturesAdapter adapter;
    private boolean isViewSet;

    private InputMethodManager inputManager;

    public static OnlinePicturesFragment newInstance() {
        return new OnlinePicturesFragment();
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
        final View layout = inflater.inflate(R.layout.fragment_search_online_list, container, false);

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

        final SearchView searchView = layout.findViewById(R.id.sv_photos_query);
        searchView.setOnQueryTextListener(getSearchListener());
    }

    private OnQueryTextListener getSearchListener() {
        return new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.onSearchClick(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        };
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() == null) {
            return;
        }
        inputManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stop();
        isViewSet = false;
    }
    //endregion

    //region implements OnlinePicturesView
    @Override
    public void init(final OnlinePicturesListPresenter listPresenter) {
        adapter = new OnlinePicturesAdapter(listPresenter, pictureUtils);
        photosView.setAdapter(adapter);
        presenter.attachListView(adapter);
    }

    @Override
    public void showErrorNetworkMessage() {
        showMessage(R.string.error_network_connection_message);
    }

    @Override
    public void showErrorDownloadingPhotosMessage() {
        showMessage(R.string.error_download_photos_message);
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
    public void showErrorSavingPhoto() {
        showMessage(R.string.error_save_photo_message);
    }

    @Override
    public void showErrorDeletingPhoto() {
        showMessage(R.string.error_delete_photo_message);
    }

    private void showMessage(@StringRes int message) {
        if (getView() == null) {
            return;
        }
        inputManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
    }
    //endregion
}
