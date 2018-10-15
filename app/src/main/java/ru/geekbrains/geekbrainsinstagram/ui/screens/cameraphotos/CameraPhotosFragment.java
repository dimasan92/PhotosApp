package ru.geekbrains.geekbrainsinstagram.ui.screens.cameraphotos;

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
import ru.geekbrains.geekbrainsinstagram.MainApplication;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.di.fragment.cameraphotos.CameraPhotosFragmentComponent;
import ru.geekbrains.geekbrainsinstagram.exception.CameraCannotLaunchException;
import ru.geekbrains.geekbrainsinstagram.model.ViewPhotoModel;
import ru.geekbrains.geekbrainsinstagram.ui.containers.main.mediator.MainContainerToContentMediator;
import ru.geekbrains.geekbrainsinstagram.util.CameraUtils;
import ru.geekbrains.geekbrainsinstagram.util.LayoutUtils;
import ru.geekbrains.geekbrainsinstagram.util.PictureUtils;

public final class CameraPhotosFragment extends Fragment implements CameraPhotosPresenter.CameraPhotosView {

    private static final int REQUEST_CAMERA_PHOTO = 1;

    @Inject
    LayoutUtils layoutUtils;

    @Inject
    PictureUtils pictureUtils;

    @Inject
    CameraUtils cameraUtils;

    @Inject
    MainContainerToContentMediator mediator;

    @Inject
    CameraPhotosPresenter presenter;

    private CoordinatorLayout mainLayout;
    private RecyclerView photosRecyclerView;
    private CameraPhotosAdapter adapter;
    private boolean isViewSet;

    public static CameraPhotosFragment newInstance() {
        return new CameraPhotosFragment();
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.fragment_camera_photos, container, false);

        inject();
        initView(layout);

        presenter.setCameraResultOkCode(Activity.RESULT_OK);
        presenter.attachView(this);
        isViewSet = true;
        presenter.create();

        return layout;
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
    public void init(final CameraPhotoListPresenter presenter) {
        adapter = new CameraPhotosAdapter(presenter, pictureUtils);
        photosRecyclerView.setAdapter(adapter);
        if (photosRecyclerView.getItemAnimator() != null) {
            photosRecyclerView.getItemAnimator().setChangeDuration(0);
        }
        this.presenter.attachListView(adapter);
    }

    @Override
    public void startCamera(ViewPhotoModel photoModel) {
        try {
            Intent cameraIntent = cameraUtils.getAdjustedCameraInvoker(photoModel);
            startActivityForResult(cameraIntent, REQUEST_CAMERA_PHOTO);
        } catch (CameraCannotLaunchException e) {
            presenter.cameraCannotLaunch();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!isViewSet) {
            presenter.attachView(this);
            presenter.attachListView(adapter);
            isViewSet = true;
        }
        if (requestCode == REQUEST_CAMERA_PHOTO) {
            presenter.cameraHasClosed(resultCode);
        }
    }

    @Override
    public void showDeletePhotoDialog(ViewPhotoModel photoModel) {
        if (getContext() == null) {
            return;
        }
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        dialogBuilder.setTitle(R.string.delete_photo_dialog_title);
        dialogBuilder.setPositiveButton(R.string.delete_photo_dialog_delete,
                (dialog, which) -> presenter.deletePhotoConfirm(photoModel));
        dialogBuilder.setNegativeButton(R.string.delete_photo_dialog_cancel, (dialog, which) -> {
        });
        dialogBuilder.show();
    }

    @Override
    public void showCannotLaunchCameraMessage() {
        Snackbar.make(mainLayout, R.string.error_camera_open_message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showPhotoSuccessAddMessage() {
        Snackbar.make(mainLayout, R.string.photo_successfully_added_message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showPhotoSuccessDeleteMessage() {
        Snackbar.make(mainLayout, R.string.photo_successfully_deleted_message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorAddToFavoritesMessage() {
        Snackbar.make(mainLayout, R.string.error_add_photo_to_favorites_message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorDeleteFromFavoritesMessage() {
        Snackbar.make(mainLayout, R.string.error_delete_photo_from_favorites_message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorPhotoDeletePhotoMessage() {
        Snackbar.make(mainLayout, R.string.error_delete_photo_message, Snackbar.LENGTH_SHORT).show();
    }

    private void inject() {
        CameraPhotosFragmentComponent component = (CameraPhotosFragmentComponent) MainApplication.getApp()
                .getComponentsManager().getFragmentComponent(CameraPhotosFragment.class);
        component.inject(this);
    }

    private void initView(View layout) {
        mainLayout = layout.findViewById(R.id.cl_main_layout);
        photosRecyclerView = layout.findViewById(R.id.rv_photos);
        photosRecyclerView.setLayoutManager(layoutUtils.getAdjustedGridLayoutManager());
        Toolbar toolbar = layout.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.appbar_camera_title);
        mediator.setupToolbar(toolbar);
        layout.findViewById(R.id.camera_fab).setOnClickListener(v -> presenter.takeAPhotoRequest());
    }
}
