package ru.geekbrains.geekbrainsinstagram.ui.screens.favorites;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import ru.geekbrains.domain.interactor.photos.ChangeFavoritePhotoStatusUseCase;
import ru.geekbrains.domain.interactor.photos.DeletePhotoUseCase;
import ru.geekbrains.domain.interactor.photos.GetFavoritesUseCase;
import ru.geekbrains.domain.model.PhotoModel;
import ru.geekbrains.geekbrainsinstagram.base.BasePresenterImpl;
import ru.geekbrains.geekbrainsinstagram.di.fragment.favorites.FavoritesFragmentScope;
import ru.geekbrains.geekbrainsinstagram.model.ViewPhotoModel;
import ru.geekbrains.geekbrainsinstagram.model.mapper.ViewPhotoModelMapper;
import ru.geekbrains.geekbrainsinstagram.ui.screens.favorites.FavoritesListPresenter.FavoritesListView;

import static ru.geekbrains.geekbrainsinstagram.ui.screens.favorites.FavoritesPresenter.FavoritesView;

@FavoritesFragmentScope
public final class FavoritesPresenterImpl extends BasePresenterImpl<FavoritesView>
        implements FavoritesPresenter {

    private final GetFavoritesUseCase getFavoritesUseCase;
    private final ChangeFavoritePhotoStatusUseCase changeFavoritePhotoStatusUseCase;
    private final DeletePhotoUseCase deletePhotoUseCase;

    private final Scheduler uiScheduler;
    private final ViewPhotoModelMapper photosMapper;
    private final FavoritesListPresenterImpl listPresenter;

    @Inject
    FavoritesPresenterImpl(GetFavoritesUseCase getFavoritesUseCase,
                           ChangeFavoritePhotoStatusUseCase changeFavoritePhotoStatusUseCase,
                           DeletePhotoUseCase deletePhotoUseCase,
                           Scheduler uiScheduler, ViewPhotoModelMapper photosMapper) {
        this.getFavoritesUseCase = getFavoritesUseCase;
        this.changeFavoritePhotoStatusUseCase = changeFavoritePhotoStatusUseCase;
        this.deletePhotoUseCase = deletePhotoUseCase;
        this.uiScheduler = uiScheduler;
        this.photosMapper = photosMapper;
        listPresenter = new FavoritesListPresenterImpl();
    }

    @Override
    public void create() {
        view.init(listPresenter);
    }

    @Override
    public void start() {
        uploadPhotos();
    }

    @Override
    public void stop() {
        super.stop();
        listPresenter.detachView();
    }

    @Override
    public void attachListView(final FavoritesListView view) {
        listPresenter.attachView(view);
    }

    private void uploadPhotos() {
        addDisposable(getFavoritesUseCase.execute()
                .observeOn(uiScheduler)
                .subscribe(photos -> listPresenter.setPhotos(photosMapper.domainToView(photos)),
                        getDefaultErrorHandler()));
    }

    private void deletePhotoFromFavorites(ViewPhotoModel photo) {
        PhotoModel photoModel = photosMapper.viewToDomainWithFavoriteChange(photo);
        addDisposable(changeFavoritePhotoStatusUseCase.execute(photoModel)
                .observeOn(uiScheduler)
                .subscribe(() -> {
                            listPresenter.deletePhoto(photo);
                            view.showSuccessDeleteFromFavoritesMessage();
                        },
                        throwable -> view.showErrorDeleteFromFavoritesMessage()));
    }

    private void deletePhotoFromDevice(ViewPhotoModel photo) {
        PhotoModel photoModel = photosMapper.viewToDomain(photo);
        addDisposable(deletePhotoUseCase.execute(photoModel)
                .observeOn(uiScheduler)
                .subscribe(() -> {
                            listPresenter.deletePhoto(photo);
                            view.showSuccessDeleteFromDeviceMessage();
                        },
                        throwable -> view.showErrorDeleteFromDeviceMessage()));
    }

    class FavoritesListPresenterImpl implements FavoritesListPresenter {

        private List<ViewPhotoModel> photos = new ArrayList<>();
        private FavoritesListView listView;

        void setPhotos(List<ViewPhotoModel> photos) {
            this.photos = photos;
            listView.updatePhotos();
        }

        void attachView(FavoritesListView listView) {
            this.listView = listView;
        }

        void detachView() {
            listView = null;
        }

        @Override
        public void bind(final int position, final FavoriteView view) {
            final ViewPhotoModel photoModel = photos.get(position);
            view.loadImage(photoModel);
        }

        @Override
        public int getCount() {
            return photos.size();
        }

        @Override
        public void onDeleteFromFavoritesClick(final int position) {
            deletePhotoFromFavorites(photos.get(position));
        }

        @Override
        public void onDeleteFromDeviceClick(final int position) {
            deletePhotoFromDevice(photos.get(position));
        }

        void deletePhoto(final ViewPhotoModel photo) {
            listView.deletePhoto(photos.indexOf(photo));
            photos.remove(photo);
        }
    }
}
