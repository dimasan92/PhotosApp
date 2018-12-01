package ru.geekbrains.pictureapp.presentation.ui.screens.details;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import ru.geekbrains.pictureapp.R;
import ru.geekbrains.pictureapp.domain.model.ImageModel;
import ru.geekbrains.pictureapp.presentation.ui.base.photos.BaseListPresenter;
import ru.geekbrains.pictureapp.presentation.ui.base.photos.BaseImagesAdapter;
import ru.geekbrains.pictureapp.presentation.util.PictureUtils;

import static androidx.recyclerview.widget.RecyclerView.ViewHolder;

public final class DetailsAdapter extends BaseImagesAdapter<DetailsAdapter.Holder> {

    private final DetailsListPresenter presenter;
    private final PictureUtils pictureUtils;

    DetailsAdapter(final DetailsListPresenter presenter, final PictureUtils pictureUtils) {
        this.presenter = presenter;
        this.pictureUtils = pictureUtils;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new Holder(inflater.inflate(R.layout.item_details, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        presenter.bind(position, holder);
    }

    @Override
    public final int getItemCount() {
        return presenter.getCount();
    }

    final class Holder extends ViewHolder implements BaseListPresenter.RowView {

        private final ImageView mainImageView;

        Holder(@NonNull View itemView) {
            super(itemView);
            mainImageView = itemView.findViewById(R.id.iv_details);
        }

        @Override
        public void loadImage(final ImageModel imageModel) {
            pictureUtils.loadImageIntoFullView(imageModel, mainImageView);
        }
    }
}

