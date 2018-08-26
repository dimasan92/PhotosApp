package ru.geekbrains.geekbrainsinstagram.model;

import java.util.List;

import androidx.recyclerview.widget.DiffUtil;

public final class PhotoDiffUtilCallback extends DiffUtil.Callback {

    private final List<PhotoModel> oldPhotos;
    private final List<PhotoModel> newPhotos;

    public PhotoDiffUtilCallback(List<PhotoModel> oldPhotos, List<PhotoModel> newPhotos) {
        this.oldPhotos = oldPhotos;
        this.newPhotos = newPhotos;
    }

    @Override
    public int getOldListSize() {
        return oldPhotos.size();
    }

    @Override
    public int getNewListSize() {
        return newPhotos.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldPhotos.get(oldItemPosition).getId()
                .equals(newPhotos.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldPhotos.get(oldItemPosition).equals(newPhotos.get(newItemPosition));
    }
}
