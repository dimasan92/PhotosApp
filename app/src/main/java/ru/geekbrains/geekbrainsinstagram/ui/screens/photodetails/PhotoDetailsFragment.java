package ru.geekbrains.geekbrainsinstagram.ui.screens.photodetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import ru.geekbrains.geekbrainsinstagram.MainApplication;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.base.BaseFragment;
import ru.geekbrains.geekbrainsinstagram.model.PresentPhotoModel;
import ru.geekbrains.geekbrainsinstagram.ui.common.NotifyingMessage;
import ru.geekbrains.geekbrainsinstagram.ui.mediator.IActivityToFragmentMediator;
import ru.geekbrains.geekbrainsinstagram.util.IPictureUtils;

public final class PhotoDetailsFragment extends BaseFragment implements IPhotoDetailsPresenter.IView {

    private static final String PHOTO_ID_KEY = "photo_id_key";

    @Inject
    IActivityToFragmentMediator activityToFragmentMediator;

    @Inject
    IPictureUtils pictureUtils;

    @Inject
    IPhotoDetailsPresenter presenter;

    private ImageView mainImageView;

    public static PhotoDetailsFragment newInstance(String photoId) {
        PhotoDetailsFragment fragment = new PhotoDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PHOTO_ID_KEY, photoId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_deatails, container, false);

        inject();

        mainImageView = view.findViewById(R.id.iv_photo_details);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.setView(this);
        if (getArguments() == null) {
            presenter.start();
        } else {
            presenter.start(getArguments().getString(PHOTO_ID_KEY));
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stop();
    }

    @Override
    public void loadPhoto(PresentPhotoModel photo) {
        pictureUtils.loadImageIntoImageViewFull(photo, mainImageView);
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
        MainApplication.getApp().getComponentsManager().getFragmentComponent().inject(this);
    }

    private void showNotifyingMessage(@StringRes int messageId) {
        if (getView() == null) {
            return;
        }
        Snackbar.make(getView(), messageId, Snackbar.LENGTH_SHORT).show();
    }
}