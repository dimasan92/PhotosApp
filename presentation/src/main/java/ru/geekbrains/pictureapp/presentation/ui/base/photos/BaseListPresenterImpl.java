package ru.geekbrains.pictureapp.presentation.ui.base.photos;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import ru.geekbrains.pictureapp.domain.model.PhotoModel;
import ru.geekbrains.pictureapp.presentation.ui.base.BasePresenter;
import ru.geekbrains.pictureapp.presentation.ui.base.photos.BaseListPresenter.RowView;

public abstract class BaseListPresenterImpl<V extends BasePresenter.BaseView,
        RV extends RowView> implements BaseListPresenter<RV> {

    protected List<PhotoModel> photoModels = new ArrayList<>();
    private final CompositeDisposable disposables = new CompositeDisposable();
    protected V mainView;
    private ListView listView;

    @Override public int getCount() {
        return photoModels.size();
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

    protected void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }

    protected Consumer<Throwable> getDefaultErrorHandler() {
        return Throwable::printStackTrace;
    }

    public void setPhotoModels(final List<PhotoModel> photoModels) {
        this.photoModels = photoModels;
        listView.updatePhotos();
    }

    public void addPhotoModel(final PhotoModel photoModel) {
        photoModels.add(photoModel);
        listView.updatePhoto(photoModels.indexOf(photoModel));
    }

    protected void updatePhotoModel(final PhotoModel photoModel) {
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

