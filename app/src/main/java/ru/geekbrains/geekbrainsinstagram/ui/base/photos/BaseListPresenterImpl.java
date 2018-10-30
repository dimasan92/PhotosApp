package ru.geekbrains.geekbrainsinstagram.ui.base.photos;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.domain.model.PhotoModel;
import ru.geekbrains.geekbrainsinstagram.ui.base.photos.BaseListPresenter.RowView;

public abstract class BaseListPresenterImpl<V extends RowView> implements BaseListPresenter<V> {

    protected List<PhotoModel> photoModels = new ArrayList<>();
    private ListView listView;

    @Override public int getCount() {
        return photoModels.size();
    }

    public void attachView(final ListView listView) {
        this.listView = listView;
    }

    public void detachView() {
        listView = null;
    }

    public void setPhotoModels(final List<PhotoModel> photoModels) {
        this.photoModels = photoModels;
        listView.updatePhotos();
    }

    public void addPhotoModel(final PhotoModel photoModel) {
        photoModels.add(photoModel);
        listView.updatePhoto(photoModels.indexOf(photoModel));
    }

    public void updatePhotoModel(final PhotoModel photoModel) {
        int position = searchItemPosition(photoModel);
        if (position == -1) {
            return;
        }
        photoModels.set(position, photoModel);
        listView.updatePhoto(position);
    }

    public void deletePhotoModel(final PhotoModel photoModel) {
        listView.deletePhoto(photoModels.indexOf(photoModel));
        photoModels.remove(photoModel);
    }

    private int searchItemPosition(final PhotoModel photoModel) {
        int position = -1;
        for (int i = 0; i < photoModels.size(); i++) {
            if (photoModels.get(i).getId().equals(photoModel.getId())) {
                position = i;
                break;
            }
        }
        return position;
    }
}

