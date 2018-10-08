package ru.geekbrains.geekbrainsinstagram.ui.screens.personalphotos;

import android.content.Intent;
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
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import ru.geekbrains.geekbrainsinstagram.MainApplication;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.base.BaseFragment;
import ru.geekbrains.geekbrainsinstagram.exception.CameraCannotLaunchException;
import ru.geekbrains.geekbrainsinstagram.model.ViewPhotoModel;
import ru.geekbrains.geekbrainsinstagram.ui.common.NotifyingMessage;
import ru.geekbrains.geekbrainsinstagram.ui.mediator.IActivityToFragmentMediator;
import ru.geekbrains.geekbrainsinstagram.ui.mediator.IFragmentToFragmentMediator;
import ru.geekbrains.geekbrainsinstagram.util.CameraUtils;
import ru.geekbrains.geekbrainsinstagram.util.LayoutUtils;
import ru.geekbrains.geekbrainsinstagram.util.PictureUtils;

public final class PersonalPhotosFragment extends BaseFragment
        implements IPersonalPhotosPresenter.IView {

    private static final int REQUEST_CAMERA_PHOTO = 1;

    @Inject
    LayoutUtils layoutUtils;

    @Inject
    PictureUtils pictureUtils;

    @Inject
    CameraUtils cameraUtils;

    @Inject
    IActivityToFragmentMediator activityToFragmentMediator;

    @Inject
    IFragmentToFragmentMediator fragmentToFragmentMediator;

    @Inject
    IPersonalPhotosPresenter presenter;

    private PersonalPhotosAdapter adapter;
    private boolean isViewSet;

    public static PersonalPhotosFragment newInstance() {
        return new PersonalPhotosFragment();
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photos_list, container, false);

        inject();
        initRecyclerView(view);
        fragmentToFragmentMediator.setFabListener(v -> presenter.takeAPhotoRequest());

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!isViewSet) {
            presenter.setView(this);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!isViewSet) {
            presenter.setView(this);
            isViewSet = true;
        }
        if (requestCode == REQUEST_CAMERA_PHOTO) {
            presenter.cameraHasClosed(resultCode);
        }
    }

    @Override
    public void addPhotos(List<ViewPhotoModel> photos) {
        adapter.updatePhotos(photos);
    }

    @Override
    public void addNewPhoto(ViewPhotoModel photo) {
        adapter.addPhoto(photo);
    }

    @Override
    public void updatePhoto(ViewPhotoModel photo) {
        adapter.updatePhoto(photo);
    }

    @Override
    public void deletePhoto(ViewPhotoModel photo) {
        adapter.deletePhoto(photo);
    }


    @Override
    public void showDeletePhotoDialog(ViewPhotoModel photo) {
        if (getContext() == null) {
            return;
        }
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        dialogBuilder.setTitle(R.string.delete_photo_dialog_title);
        dialogBuilder.setPositiveButton(android.R.string.yes,
                (dialog, which) -> presenter.deletePhoto(photo));
        dialogBuilder.setNegativeButton(android.R.string.no, (dialog, which) -> {
        });
        dialogBuilder.show();
    }

    @Override
    public void startCamera(ViewPhotoModel photo) {
        try {
            Intent cameraIntent = cameraUtils.getAdjustedCameraInvoker(photo);
            startActivityForResult(cameraIntent, REQUEST_CAMERA_PHOTO);
        } catch (CameraCannotLaunchException e) {
            presenter.cameraCannotLaunch();
        }
    }

    @Override
    public void showNotifyingMessage(NotifyingMessage message) {
        switch (message) {
            case ERROR_CAMERA_OPEN:
                showNotifyingMessage(R.string.error_camera_open_message);
                break;
            case ERROR_DELETE_PHOTO:
                showNotifyingMessage(R.string.error_delete_photo_message);
                break;
            case PHOTO_SUCCESSFULLY_ADDED:
                showNotifyingMessage(R.string.photo_successfully_added_message);
                break;
            case PHOTO_SUCCESSFULLY_DELETED:
                showNotifyingMessage(R.string.photo_successfully_deleted_message);
                break;
            case ERROR_ADD_PHOTO_TO_FAVORITES:
                showNotifyingMessage(R.string.error_add_photo_to_favorites_message);
                break;
            case PHOTO_SUCCESSFULLY_DELETED_FROM_FAVORITES:
                showNotifyingMessage(R.string.error_delete_photo_from_favorites_message);
                break;
        }
    }

    private void inject() {
        MainApplication.getApp().getComponentsManager().getChildFragmentComponent().inject(this);
    }

    private void initRecyclerView(View layout) {
        RecyclerView photoRecyclerView = layout.findViewById(R.id.photos_recycler_view);
        photoRecyclerView.setLayoutManager(layoutUtils.getAdjustedGridLayoutManager());
        adapter = new PersonalPhotosAdapter(pictureUtils, adapterListener());
        photoRecyclerView.setAdapter(adapter);
        if (photoRecyclerView.getItemAnimator() != null) {
            photoRecyclerView.getItemAnimator().setChangeDuration(0);
        }
    }

    private PersonalPhotosAdapter.IPersonalPhotoListener adapterListener() {
        return new PersonalPhotosAdapter.IPersonalPhotoListener() {
            @Override
            public void onFavoritesClick(ViewPhotoModel photo) {
                presenter.changePhotoFavoriteState(photo);
            }

            @Override
            public void onDeleteClick(ViewPhotoModel photo) {
                presenter.deleteRequest(photo);
            }

            @Override
            public void onDetailsClick(List<ViewPhotoModel> photos) {
//                activityToFragmentMediator.openFullSizePhoto(photo);
            }
        };
    }

    private void showNotifyingMessage(@StringRes int message) {
        fragmentToFragmentMediator.showNotifyingMessage(message, Snackbar.LENGTH_SHORT);
    }
}
