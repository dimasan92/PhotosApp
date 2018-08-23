package ru.geekbrains.geekbrainsinstagram.ui.cameragallery;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.geekbrains.geekbrainsinstagram.MainApplication;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.base.BaseFragment;
import ru.geekbrains.geekbrainsinstagram.ui.model.InnerStoragePhotoModel;

public final class CameraGalleryFragment extends BaseFragment implements CameraGalleryContract.View {

    private static final int REQUEST_CAMERA_PHOTO = 1;
    private static final int COLUMN_COUNT = 3;

    @Inject
    CameraGalleryContract.Presenter presenter;

    @Inject
    CameraPhotoAdapter adapter;

    private RecyclerView photoRecyclerView;

    public static CameraGalleryFragment newInstance() {
        return new CameraGalleryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApplication.getApp().getComponentsManager().getFragmentComponent().inject(this);
        presenter.setView(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera_gallery, container, false);

        FloatingActionButton fab = view.findViewById(R.id.take_photo_button);
        fab.setOnClickListener(v -> presenter.takeAPhoto());

        initRecyclerView(view);

        presenter.viewIsReady();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CAMERA_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                presenter.photoTook(true);
            } else {
                presenter.photoTook(false);
            }
        }
    }

    @Override
    public void showPhotos(List<InnerStoragePhotoModel> photos) {
        adapter.setPictures(photos);
    }

    @Override
    public void showNotifyingMessage(@StringRes int message) {
        if (getView() == null) {
            return;
        }
        Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT)
                .setAction(android.R.string.ok, v -> {
                })
                .show();
    }

    @Override
    public boolean isCameraAvailable(Intent cameraIntent) {
        if (getActivity() == null) {
            return false;
        }
        return cameraIntent.resolveActivity(getActivity().getPackageManager()) != null;
    }

    @Override
    public boolean setCameraPermissions(Intent cameraIntent, Uri uri) {
        if (getActivity() == null) {
            return false;
        }
        List<ResolveInfo> cameraActivities = getActivity()
                .getPackageManager().queryIntentActivities(cameraIntent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo activity : cameraActivities) {
            getActivity().grantUriPermission(activity.activityInfo.packageName,
                    uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        return true;
    }

    @Override
    public void startCamera(Intent cameraIntent) {
        startActivityForResult(cameraIntent, REQUEST_CAMERA_PHOTO);
    }

    private void initRecyclerView(View layout) {
        photoRecyclerView = layout.findViewById(R.id.camera_photo_recycler_view);
        photoRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), COLUMN_COUNT));
        photoRecyclerView.setAdapter(adapter);
    }
}
