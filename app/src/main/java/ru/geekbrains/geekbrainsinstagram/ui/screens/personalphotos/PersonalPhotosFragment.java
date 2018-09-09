package ru.geekbrains.geekbrainsinstagram.ui.screens.personalphotos;

import android.app.Activity;
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
import ru.geekbrains.geekbrainsinstagram.model.PresentPhotoModel;
import ru.geekbrains.geekbrainsinstagram.util.ICameraUtils;
import ru.geekbrains.geekbrainsinstagram.ui.mediator.IFragmentToFragmentMediator;
import ru.geekbrains.geekbrainsinstagram.util.ILayoutUtils;
import ru.geekbrains.geekbrainsinstagram.util.IPictureUtils;

public final class PersonalPhotosFragment extends BaseFragment
        implements IPersonalPhotosPresenter.IView {

    private static final int REQUEST_CAMERA_PHOTO = 1;

    @Inject
    ILayoutUtils layoutUtils;

    @Inject
    IPictureUtils pictureUtils;

    @Inject
    ICameraUtils cameraUtils;

    @Inject
    IFragmentToFragmentMediator fragmentToFragmentMediator;

    @Inject
    IPersonalPhotosPresenter presenter;

    private PersonalPhotosAdapter adapter;

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
        presenter.setView(this);
        presenter.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stop();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        onStart();
        if (requestCode == REQUEST_CAMERA_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                presenter.photoHasTaken();
            } else {
                presenter.photoHasCanceled();
            }
        }
    }

    @Override
    public void addPhotos(List<PresentPhotoModel> photos) {
        adapter.updatePhotos(photos);
    }

    @Override
    public void addNewPhoto(PresentPhotoModel photo) {
        adapter.addPhoto(photo);
    }

    @Override
    public void updatePhoto(PresentPhotoModel photo) {
        adapter.updatePhoto(photo);
    }

    @Override
    public void deletePhoto(PresentPhotoModel photo) {
        adapter.deletePhoto(photo);
    }

    @Override
    public void showNotifyingMessage(@StringRes int message) {
        fragmentToFragmentMediator.showNotifyingMessage(message, Snackbar.LENGTH_SHORT);
    }

    @Override
    public void showDeletePhotoDialog(PresentPhotoModel photo) {
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
    public void startCamera(PresentPhotoModel photo) {
        try {
            Intent cameraIntent = cameraUtils.getAdjustedCameraInvoker(photo);
            startActivityForResult(cameraIntent, REQUEST_CAMERA_PHOTO);
        } catch (CameraCannotLaunchException e) {
            presenter.cameraCannotLaunch();
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
            public void onFavoritesClick(PresentPhotoModel photo) {
                presenter.changePhotoFavoriteState(photo);
            }

            @Override
            public void onDeleteClick(PresentPhotoModel photo) {
                presenter.deleteRequest(photo);
            }
        };
    }
}
