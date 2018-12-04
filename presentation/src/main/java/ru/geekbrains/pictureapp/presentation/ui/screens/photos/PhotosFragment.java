package ru.geekbrains.pictureapp.presentation.ui.screens.photos;

import android.app.Activity;
import android.content.Intent;
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
import ru.geekbrains.pictureapp.domain.model.ImageModel;
import ru.geekbrains.pictureapp.presentation.MainApplication;
import ru.geekbrains.pictureapp.presentation.exception.CameraCannotLaunchException;
import ru.geekbrains.pictureapp.presentation.ui.container.mediator.ContainerToContentMediator;
import ru.geekbrains.pictureapp.presentation.ui.navigator.Screens;
import ru.geekbrains.pictureapp.presentation.ui.screens.photos.PhotosPresenter.PhotosView;
import ru.geekbrains.pictureapp.presentation.util.CameraUtils;
import ru.geekbrains.pictureapp.presentation.util.LayoutUtils;
import ru.geekbrains.pictureapp.presentation.util.PictureUtils;
import ru.geekbrains.pictureapp.presentation.util.ViewUtils;

public final class PhotosFragment extends Fragment implements PhotosView {

    private static final int REQUEST_CAMERA_PHOTO = 1;

    @Inject LayoutUtils layoutUtils;
    @Inject PictureUtils pictureUtils;
    @Inject CameraUtils cameraUtils;
    @Inject ViewUtils viewUtils;
    @Inject ContainerToContentMediator mediator;
    @Inject PhotosPresenter presenter;

    private CoordinatorLayout mainLayout;
    private RecyclerView photosView;
    private PhotosAdapter adapter;
    private boolean isViewSet;

    public static PhotosFragment newInstance() {
        return new PhotosFragment();
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
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.fragment_photos, container, false);

        initView(layout);
        initToolbar(layout);
        initListeners(layout);
        preparePresenter();

        return layout;
    }

    private void initView(final View layout) {
        mainLayout = layout.findViewById(R.id.cl_main_layout);
        photosView = layout.findViewById(R.id.rv_photos);
        photosView.setLayoutManager(layoutUtils.getAdjustedGridLayoutManager());
        if (photosView.getItemAnimator() != null) {
            photosView.getItemAnimator().setChangeDuration(0);
        }
    }

    private void initToolbar(final View layout) {
        final Toolbar toolbar = layout.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.toolbar_camera_title);
        mediator.setupToolbar(toolbar, Screens.HOME_SCREEN);
    }

    private void initListeners(final View layout) {
        layout.findViewById(R.id.fab_camera).setOnClickListener(v -> presenter.takeAPhotoRequest());
    }

    private void preparePresenter() {
        presenter.setCameraResultOkCode(Activity.RESULT_OK);
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

    @Override
    public void onStop() {
        super.onStop();
        presenter.stop();
        isViewSet = false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        setViewToPresenter();
        if (requestCode == REQUEST_CAMERA_PHOTO) {
            presenter.cameraHasClosed(resultCode);
        }
    }

    private void setViewToPresenter() {
        if (!isViewSet) {
            presenter.attachView(this);
            presenter.attachListView(adapter);
            isViewSet = true;
        }
    }
    //endregion

    //region implements PhotosView
    @Override
    public void init(final PhotosListPresenter listPresenter) {
        adapter = new PhotosAdapter(listPresenter, pictureUtils);
        photosView.setAdapter(adapter);
        presenter.attachListView(adapter);
    }

    @Override
    public void startCamera(final String filePath) {
        try {
            final Intent cameraIntent = cameraUtils.getAdjustedCameraInvoker(filePath);
            startActivityForResult(cameraIntent, REQUEST_CAMERA_PHOTO);
        } catch (CameraCannotLaunchException e) {
            presenter.cameraCouldNotLaunch();
        }
    }

    @Override
    public void showPhotoDeleteDialog(final ImageModel imageModel) {
        viewUtils.showDeleteImageDialog(getActivity(), (dialog, which) -> presenter.deletePhotoConfirm(imageModel));
    }

    @Override
    public void showCouldNotLaunchCameraMessage() {
        showMessage(R.string.error_camera_open_message);
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
    public void showErrorDeletingPhotoMessage() {
        showMessage(R.string.error_delete_photo_message);
    }

    private void showMessage(@StringRes int message) {
        Snackbar.make(mainLayout, message, Snackbar.LENGTH_SHORT).show();
    }
    //endregion
}
