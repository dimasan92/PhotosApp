package ru.geekbrains.pictureapp.presentation.ui.base.photos;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import ru.geekbrains.pictureapp.domain.model.ImageModel;
import ru.geekbrains.pictureapp.presentation.ui.base.BasePresenter;
import ru.geekbrains.pictureapp.presentation.ui.base.photos.BaseListPresenter.RowView;

public abstract class BaseListPresenterImpl<V extends BasePresenter.BaseView,
        RV extends RowView> implements BaseListPresenter<RV> {

    protected List<ImageModel> imageModels = new ArrayList<>();
    private final CompositeDisposable disposables = new CompositeDisposable();
    protected V mainView;
    private ListView listView;

    @Override
    public int getCount() {
        return imageModels.size();
    }

    public void attachView(final V mainView, final ListView listView) {
        this.mainView = mainView;
        this.listView = listView;
    }

    public void detachView() {
        mainView = null;
        listView = null;
        disposables.clear();
    }

    protected void addDisposable(final Disposable disposable) {
        disposables.add(disposable);
    }

    public void setImageModels(final List<ImageModel> imageModels) {
        this.imageModels = imageModels;
        listView.updatePhotos();
    }

    public void addImageModel(final ImageModel imageModel) {
        imageModels.add(imageModel);
        listView.updatePhoto(imageModels.indexOf(imageModel));
    }

    protected void updateImageModel(final ImageModel imageModel) {
        int position = searchItemPosition(imageModel);
        if (position == -1) {
            return;
        }
        imageModels.set(position, imageModel);
        listView.updatePhoto(position);
    }

    public void deleteImageModel(final ImageModel imageModel) {
        listView.deletePhoto(imageModels.indexOf(imageModel));
        imageModels.remove(imageModel);
    }

    private int searchItemPosition(final ImageModel imageModel) {
        int position = -1;
        for (int i = 0; i < imageModels.size(); i++) {
            if (imageModels.get(i).getId().equals(imageModel.getId())) {
                position = i;
                break;
            }
        }
        return position;
    }
}

