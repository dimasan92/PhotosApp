package ru.geekbrains.geekbrainsinstagram.ui.cameragallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.base.BaseFragment;

public final class CameraGalleryFragment extends BaseFragment implements CameraGalleryContract.View {

    public static CameraGalleryFragment newInstance() {
        return new CameraGalleryFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera_gallery, container, false);

        FloatingActionButton fab = view.findViewById(R.id.take_photo_button);
        fab.setOnClickListener(v -> {
        });

        return view;
    }

    @Override
    public void showNotifyingMessage(@StringRes int message) {
        if (getView() == null) {
            return;
        }
        Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT)
                .setAction(android.R.string.ok, v -> {})
                .show();
    }
}
