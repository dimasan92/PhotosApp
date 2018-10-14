package ru.geekbrains.geekbrainsinstagram.ui.screens.cameraphotos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import ru.geekbrains.geekbrainsinstagram.MainApplication;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.di.fragment.cameraphotos.CameraPhotosFragmentComponent;
import ru.geekbrains.geekbrainsinstagram.util.LayoutUtils;

public final class CameraPhotosFragment extends Fragment implements CameraPhotosPresenter.View {

    @Inject
    LayoutUtils layoutUtils;

    @Inject
    CameraPhotosPresenter presenter;

    private CameraPhotosAdapter adapter;
    private boolean isViewSet;

    public static CameraPhotosFragment newInstance() {
        return new CameraPhotosFragment();
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photos_list, container, false);

        inject();
        initRecyclerView(view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!isViewSet) {
            presenter.attachView(this);
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


    private void inject() {
        CameraPhotosFragmentComponent component = (CameraPhotosFragmentComponent) MainApplication.getApp()
                .getComponentsManager().getFragmentComponent(CameraPhotosFragment.class);
        component.inject(this);
    }

    private void initRecyclerView(View layout) {
        RecyclerView photoRecyclerView = layout.findViewById(R.id.photos_recycler_view);
        photoRecyclerView.setLayoutManager(layoutUtils.getAdjustedGridLayoutManager());
        adapter = new CameraPhotosAdapter();
        photoRecyclerView.setAdapter(adapter);
        if (photoRecyclerView.getItemAnimator() != null) {
            photoRecyclerView.getItemAnimator().setChangeDuration(0);
        }
    }
}
