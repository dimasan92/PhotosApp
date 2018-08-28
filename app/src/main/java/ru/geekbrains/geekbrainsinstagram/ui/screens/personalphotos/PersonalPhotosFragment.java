package ru.geekbrains.geekbrainsinstagram.ui.screens.personalphotos;

import android.app.Activity;
import android.content.Intent;
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
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import ru.geekbrains.geekbrainsinstagram.MainApplication;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.base.BaseFragment;
import ru.geekbrains.geekbrainsinstagram.model.PhotoModel;
import ru.geekbrains.geekbrainsinstagram.utils.ILayoutUtils;

public final class PersonalPhotosFragment extends BaseFragment
        implements IPersonalPhotosPresenter.IView {

    private static final int REQUEST_CAMERA_PHOTO = 1;

    @Inject
    ILayoutUtils layoutUtils;

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

        initRecyclerView(view);
        setupListeners(view);

        presenter.viewIsReady();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
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
    public void addPhotos(List<PhotoModel> photos) {
        adapter.updatePhotos(photos);
    }

    @Override
    public void addNewPhoto(PhotoModel photoModel) {
        adapter.addPhoto(photoModel);
    }

    @Override
    public void updatePhoto(PhotoModel photoModel) {
        adapter.updatePhoto(photoModel);
    }

    @Override
    public void deletePhoto(PhotoModel photoModel) {
        adapter.deletePhoto(photoModel);
    }

    @Override
    public void showNotifyingMessage(@StringRes int message) {
        if (getView() == null) {
            return;
        }
        Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showDeletePhotoDialog(PhotoModel photoModel) {
        if (getContext() == null) {
            return;
        }
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        dialogBuilder.setTitle(R.string.delete_photo_dialog_title);
        dialogBuilder.setPositiveButton(android.R.string.yes,
                (dialog, which) -> presenter.deletePhoto(photoModel));
        dialogBuilder.setNegativeButton(android.R.string.no, (dialog, which) -> {
        });
        dialogBuilder.show();
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
        photoRecyclerView.setLayoutManager(layoutUtils
                .getAdjusetGridLayoutManager(getResources().getConfiguration().orientation));
        photoRecyclerView.setAdapter(adapter);
    }

    private void setupListeners(View layout) {
        FloatingActionButton fab = layout.findViewById(R.id.take_photo_fab);
        fab.setOnClickListener(v -> presenter.takeAPhoto());

        presenter.changePhotoFavoriteState(adapter.onFavoritesClick());
        presenter.deleteRequest(adapter.onDeleteClick());
    }
}
