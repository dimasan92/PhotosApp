package ru.geekbrains.pictureapp.presentation.ui.base.photos;

import androidx.recyclerview.widget.RecyclerView;

import static androidx.recyclerview.widget.RecyclerView.ViewHolder;
import static ru.geekbrains.pictureapp.presentation.ui.base.photos.BaseListPresenter.ListView;

public abstract class BasePhotosAdapter<VH extends ViewHolder>
        extends RecyclerView.Adapter<VH> implements ListView {

    @Override
    public void updatePhotos() {
        notifyDataSetChanged();
    }

    @Override
    public void updatePhoto(final int position) {
        notifyItemChanged(position);
    }

    @Override
    public void deletePhoto(final int position) {
        notifyItemRemoved(position);
    }
}
