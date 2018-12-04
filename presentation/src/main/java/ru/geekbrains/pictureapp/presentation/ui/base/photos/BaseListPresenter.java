package ru.geekbrains.pictureapp.presentation.ui.base.photos;

import ru.geekbrains.pictureapp.domain.model.ImageModel;

public interface BaseListPresenter<V extends BaseListPresenter.RowView> {

    interface ListView {

        void updatePhotos();

        void updatePhoto(final int position);

        void deletePhoto(final int position);
    }

    interface RowView {

        void loadImage(final ImageModel imageModel);
    }

    void bind(int position, V view);

    int getCount();
}
