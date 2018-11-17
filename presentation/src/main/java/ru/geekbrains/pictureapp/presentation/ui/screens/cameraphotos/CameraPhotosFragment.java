package ru.geekbrains.pictureapp.presentation.ui.screens.cameraphotos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import ru.geekbrains.pictureapp.domain.model.PhotoModel;
import ru.geekbrains.pictureapp.presentation.MainApplication;
import ru.geekbrains.pictureapp.R;
import ru.geekbrains.pictureapp.presentation.exception.CameraCannotLaunchException;
import ru.geekbrains.pictureapp.presentation.ui.container.mediator.ContainerToContentMediator;
import ru.geekbrains.pictureapp.presentation.ui.navigator.Screens;
import ru.geekbrains.pictureapp.presentation.ui.screens.cameraphotos.CameraPhotosPresenter.CameraPhotosView;
import ru.geekbrains.pictureapp.presentation.util.CameraUtils;
import ru.geekbrains.pictureapp.presentation.util.LayoutUtils;
import ru.geekbrains.pictureapp.presentation.util.PictureUtils;

public final class CameraPhotosFragment extends Fragment implements CameraPhotosView {

    private static final int REQUEST_CAMERA_PHOTO = 1;

    @Inject LayoutUtils layoutUtils;
    @Inject PictureUtils pictureUtils;
    @Inject CameraUtils cameraUtils;
    @Inject ContainerToContentMediator mediator;
    @Inject CameraPhotosPresenter presenter;

    private CoordinatorLayout mainLayout;
    private RecyclerView photosRecyclerView;
    private CameraPhotosAdapter adapter;
    private boolean isViewSet;

    public static CameraPhotosFragment newInstance() {
        return new CameraPhotosFragment();
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        inject();
        super.onCreate(savedInstanceState);
    }

    @NonNull @Override public View onCreateView(@NonNull LayoutInflater inflater,
                                                @Nullable ViewGroup container,
                                                @Nullable Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.fragment_camera_photos, container, false);

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

    @Override public void init(final CameraPhotoListPresenter listPresenter) {
        adapter = new CameraPhotosAdapter(listPresenter, pictureUtils);
        photosRecyclerView.setAdapter(adapter);
        presenter.attachListView(adapter);
    }

    @Override public void startCamera(final String filePath) {
        try {
            final Intent cameraIntent = cameraUtils.getAdjustedCameraInvoker(filePath);
            startActivityForResult(cameraIntent, REQUEST_CAMERA_PHOTO);
        } catch (CameraCannotLaunchException e) {
            presenter.cameraCouldNotLaunch();
        }
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!isViewSet) {
            presenter.attachView(this);
            presenter.attachListView(adapter);
            isViewSet = true;
        }
        if (requestCode == REQUEST_CAMERA_PHOTO) {
            presenter.cameraHasClosed(resultCode);
        }
    }

    @Override public void showPhotoDeleteDialog(final PhotoModel photoModel) {
        if (getContext() == null) {
            return;
        }
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        dialogBuilder.setTitle(R.string.delete_photo_dialog_title);
        dialogBuilder.setPositiveButton(R.string.delete_photo_dialog_delete,
                (dialog, which) -> presenter.deletePhotoConfirm(photoModel));
        dialogBuilder.setNegativeButton(R.string.delete_photo_dialog_cancel, (dialog, which) -> {
        });
        dialogBuilder.show();
    }

    @Override public void showCouldNotLaunchCameraMessage() {
        Snackbar.make(mainLayout, R.string.error_camera_open_message, Snackbar.LENGTH_SHORT).show();
    }

    @Override public void showPhotoSuccessAddedMessage() {
        Snackbar.make(mainLayout, R.string.photo_successfully_added_message, Snackbar.LENGTH_SHORT).show();
    }

    @Override public void showPhotoSuccessDeletedMessage() {
        Snackbar.make(mainLayout, R.string.photo_successfully_deleted_message, Snackbar.LENGTH_SHORT).show();
    }

    @Override public void showErrorAddingToFavoritesMessage() {
        Snackbar.make(mainLayout, R.string.error_add_photo_to_favorites_message, Snackbar.LENGTH_SHORT).show();
    }

    @Override public void showErrorDeletingFromFavoritesMessage() {
        Snackbar.make(mainLayout, R.string.error_delete_photo_from_favorites_message, Snackbar.LENGTH_SHORT).show();
    }

    @Override public void showErrorDeletingPhotoMessage() {
        Snackbar.make(mainLayout, R.string.error_delete_photo_message, Snackbar.LENGTH_SHORT).show();
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
        if (photosRecyclerView.getItemAnimator() != null) {
            photosRecyclerView.getItemAnimator().setChangeDuration(0);
        }
        final Toolbar toolbar = layout.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.toolbar_camera_title);
        mediator.setupToolbar(toolbar, Screens.HOME_SCREEN);
        layout.findViewById(R.id.camera_fab).setOnClickListener(v -> presenter.takeAPhotoRequest());
    }

    private void preparePresenter() {
        presenter.setCameraResultOkCode(Activity.RESULT_OK);
        presenter.attachView(this);
        isViewSet = true;
        presenter.create();
    }
}
