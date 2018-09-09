package ru.geekbrains.geekbrainsinstagram.ui.screens.photodetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.base.BaseFragment;

public final class PhotoDetailsFragment extends BaseFragment {

    private static final String PHOTO_ID_KEY = "photo_id_key";

//    @Inject
//    ActivityToFragmentMediator activityToFragmentMediator;

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
//
//        inject();
//
//
//        activityToFragmentMediator.setupToolbar(view.findViewById(R.id.single_photo_toolbar));
//
//        ImageView imageView = view.findViewById(R.id.iv_single_photo);
//        imageView.setImageResource(R.drawable.toolbar);
        return view;
    }

//    private void inject() {
//        MainApplication.getApp().getComponentsManager().getFragmentComponent().inject(this);
//    }
}
