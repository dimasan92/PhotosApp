package ru.geekbrains.pictureapp.presentation.ui.screens.fullscreenphotos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import ru.geekbrains.pictureapp.domain.model.PhotoModel;
import ru.geekbrains.pictureapp.R;
import ru.geekbrains.pictureapp.presentation.ui.base.BasePresenter;
import ru.geekbrains.pictureapp.presentation.ui.common.NotifyingMessage;
import ru.geekbrains.pictureapp.presentation.util.PictureUtils;

public final class FullscreenPhotosFragment extends Fragment
        implements FullscreenPhotosPresenter.IView, BasePresenter.BaseView {

    private static final String PHOTO_IDS_KEY = "photo_ids_key";


//    @Inject
    PictureUtils pictureUtils;

//    @Inject
    FullscreenPhotosPresenter presenter;

    private ImageView mainImageView;
    private FullscreenPhotosAdapter adapter;

    public static FullscreenPhotosFragment newInstance(String[] photoIds) {
        FullscreenPhotosFragment fragment = new FullscreenPhotosFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArray(PHOTO_IDS_KEY, photoIds);
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    public android.view.View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                          @Nullable Bundle savedInstanceState) {
        android.view.View layout = inflater.inflate(R.layout.fragment_fullscreen_photos, container, false);

        inject();
//        initRecyclerView(layout);

        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.attachView(this);
        if (getArguments() == null) {
            presenter.start();
        } else {
            presenter.start(getArguments().getStringArray(PHOTO_IDS_KEY));
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stop();
    }

    @Override
    public void updatePhotos(List<PhotoModel> photos) {
        adapter.updatePhotos(photos);
    }

    @Override
    public void deletePhoto(PhotoModel photo) {
        adapter.deletePhoto(photo);
    }

    @Override
    public void showNotifyingMessage(NotifyingMessage message) {
        switch (message) {
            case ERROR_LOAD_PHOTO:
                showNotifyingMessage(R.string.error_load_photo_message);
                break;
        }
    }

    private void inject() {
//        MainApplication.getApp().getComponentsManager().getFragmentComponent().inject(this);
    }

    private void initRecyclerView(android.view.View layout) {
        RecyclerView fullscreenRecyclerView = layout.findViewById(R.id.rv_fullscreen_photos);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        adapter = new FullscreenPhotosAdapter(pictureUtils, photo -> presenter.deletePhoto(photo));

        fullscreenRecyclerView.setLayoutManager(layoutManager);
        fullscreenRecyclerView.setAdapter(adapter);
        snapHelper.attachToRecyclerView(fullscreenRecyclerView);
    }

    private void showNotifyingMessage(@StringRes int messageId) {
        if (getView() == null) {
            return;
        }
        Snackbar.make(getView(), messageId, Snackbar.LENGTH_SHORT).show();
    }
}
