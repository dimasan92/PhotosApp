package ru.geekbrains.geekbrainsinstagram.ui.screens.photodetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import ru.geekbrains.geekbrainsinstagram.MainApplication;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.base.BaseFragment;
import ru.geekbrains.geekbrainsinstagram.ui.mediator.IActivityToFragmentMediator;

public final class PhotoDetailsFragment extends BaseFragment implements IPhotoDetailsPresenter.IView {

    private static final String PHOTO_ID_KEY = "photo_id_key";

    @Inject
    IActivityToFragmentMediator activityToFragmentMediator;

    @Inject
    IPhotoDetailsPresenter presenter;

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

        activityToFragmentMediator.setupToolbar(view.findViewById(R.id.photo_details_toolbar));
        ImageView imageView = view.findViewById(R.id.iv_photo_details);

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

    private void inject() {
        MainApplication.getApp().getComponentsManager().getFragmentComponent().inject(this);
    }
}
