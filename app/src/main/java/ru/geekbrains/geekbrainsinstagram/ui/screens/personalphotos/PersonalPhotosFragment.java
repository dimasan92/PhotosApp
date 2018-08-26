package ru.geekbrains.geekbrainsinstagram.ui.screens.personalphotos;

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
import ru.geekbrains.geekbrainsinstagram.ui.model.PhotoModel;

public final class PersonalPhotosFragment extends BaseFragment
        implements IPersonalPhotosPresenter.IView {

    private static final int REQUEST_CAMERA_PHOTO = 1;
    private static final int COLUMN_COUNT = 3;

    @Inject
    IPersonalPhotosPresenter presenter;

    @Inject
    PersonalPhotosAdapter adapter;

    public static PersonalPhotosFragment newInstance() {
        return new PersonalPhotosFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject();
        presenter.setView(this);
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_photos, container, false);

        FloatingActionButton fab = view.findViewById(R.id.take_photo_fab);
        fab.setOnClickListener(v -> presenter.takeAPhoto());

        initRecyclerView(view);

        presenter.viewIsReady();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CAMERA_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                presenter.photoHasTaken();
            } else {
                presenter.photoHasCanceled();
            }
        }
    }

    @Override
    public void showPhotos(List<PhotoModel> photos) {
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
    public void startCamera(Intent cameraIntent) {
        startActivityForResult(cameraIntent, REQUEST_CAMERA_PHOTO);
    }

    private void inject() {
        MainApplication.getApp().getComponentsManager().getFragmentComponent().inject(this);
    }

    private void initRecyclerView(View layout) {
        RecyclerView photoRecyclerView = layout.findViewById(R.id.personal_photos_recycler_view);
        photoRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), COLUMN_COUNT));
        photoRecyclerView.setAdapter(adapter);
    }
}
