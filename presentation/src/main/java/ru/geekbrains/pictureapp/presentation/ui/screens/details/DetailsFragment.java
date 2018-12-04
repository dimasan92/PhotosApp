package ru.geekbrains.pictureapp.presentation.ui.screens.details;

import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.OnScrollListener;

import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import ru.geekbrains.pictureapp.R;
import ru.geekbrains.pictureapp.presentation.MainApplication;
import ru.geekbrains.pictureapp.presentation.ui.screens.BackListener;
import ru.geekbrains.pictureapp.presentation.util.PictureUtils;
import ru.geekbrains.pictureapp.presentation.util.ViewUtils;

import static ru.geekbrains.pictureapp.presentation.ui.screens.details.DetailsPresenter.DetailsView;

public final class DetailsFragment extends Fragment implements DetailsView, BackListener {

    private static final String PHOTOS_KEY = "photos_key";
    private static final String POSITION_KEY = "position_key";

    @Inject PictureUtils pictureUtils;
    @Inject ViewUtils viewUtils;
    @Inject DetailsPresenter presenter;

    private ImageView isFavoriteImageView;
    private RecyclerView imagesView;
    private DetailsAdapter adapter;
    private final Point point = new Point();
    private int currentPosition;

    private boolean isViewSet;

    public static DetailsFragment newInstance(final String[] photos, final int initPosition) {
        final DetailsFragment fragment = new DetailsFragment();
        final Bundle bundle = new Bundle();
        bundle.putStringArray(PHOTOS_KEY, photos);
        bundle.putInt(POSITION_KEY, initPosition);
        fragment.setArguments(bundle);
        return fragment;
    }

    //region overrides Fragment methods
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        inject();
        super.onCreate(savedInstanceState);
    }

    private void inject() {
        MainApplication.getApp()
                .getComponentsManager()
                .getDetailsComponent()
                .inject(this);
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.fragment_details, container, false);
        initView(layout);
        initListeners(layout);
        preparePresenter();
        return layout;
    }

    private void initView(final View layout) {
        imagesView = layout.findViewById(R.id.rv_details);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        imagesView.setLayoutManager(layoutManager);
        if (imagesView.getItemAnimator() != null) {
            imagesView.getItemAnimator().setChangeDuration(0);
        }

        final PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(imagesView);

        isFavoriteImageView = layout.findViewById(R.id.iv_is_favorite);
    }

    private void initListeners(final View layout) {
        imagesView.addOnScrollListener(getOnScrollListener());
        isFavoriteImageView.setOnClickListener(v -> presenter.onFavoriteClick(currentPosition));
        layout.findViewById(R.id.iv_delete).setOnClickListener(v -> presenter.onDeleteClick(currentPosition));
    }

    private OnScrollListener getOnScrollListener() {
        return new OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                final int offset = recyclerView.computeHorizontalScrollOffset();
                final int pageWidth = point.x;
                if (offset % pageWidth == 0) {
                    currentPosition = offset / pageWidth;
                }
            }
        };
    }

    private void preparePresenter() {
        presenter.attachView(this);
        isViewSet = true;
        presenter.create(getInitPhotos(), getInitPosition());
    }

    private String[] getInitPhotos() {
        return getArguments() == null ? new String[0] : getArguments().getStringArray(PHOTOS_KEY);
    }

    private int getInitPosition() {
        return getArguments() == null ? 0 : getArguments().getInt(POSITION_KEY);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!prepareScreenPoint()) {
            presenter.back();
            return;
        }
        if (!isViewSet) {
            presenter.attachView(this);
            presenter.attachListView(adapter);
        }
        presenter.start();
    }

    private boolean prepareScreenPoint() {
        if (getActivity() == null) {
            return false;
        }
        getActivity().getWindowManager().getDefaultDisplay().getSize(point);
        return true;
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stop();
        isViewSet = false;
    }
    //endregion

    //region implements BackListener
    @Override
    public void onBackPressed() {
        presenter.back();
    }
    //endregion

    //region implements DetailsView
    @Override
    public void init(final DetailsListPresenter listPresenter) {
        adapter = new DetailsAdapter(listPresenter, pictureUtils);
        imagesView.setAdapter(adapter);
        imagesView.scrollToPosition(getInitPosition());
        presenter.attachListView(adapter);
    }

    @Override
    public void release() {
        MainApplication.getApp()
                .getComponentsManager()
                .releaseDetailsComponent();
    }

    @Override
    public void setFavorite(boolean isFavorite) {
        isFavoriteImageView.setImageResource(isFavorite ?
                R.drawable.ic_star_filled_24dp :
                R.drawable.ic_star_border_24dp);
    }

    @Override
    public void showDeleteDialog(final int position) {
        viewUtils.showDeleteImageDialog(getActivity(), (dialog, which) -> presenter.deleteConfirm(position));
    }

    @Override
    public void showErrorAddingToFavoritesMessage() {
        showMessage(R.string.error_add_photo_to_favorites_message);
    }

    @Override
    public void showErrorDeletingFromFavoritesMessage() {
        showMessage(R.string.error_delete_photo_from_favorites_message);
    }

    @Override
    public void showErrorDeletingMessage() {
        showMessage(R.string.error_delete_photo_message);
    }

    private void showMessage(@StringRes int message) {
        if (getView() == null) {
            return;
        }
        Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
    }
    //endregion
}
