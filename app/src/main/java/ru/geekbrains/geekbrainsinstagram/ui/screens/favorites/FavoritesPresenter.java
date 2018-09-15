package ru.geekbrains.geekbrainsinstagram.ui.screens.favorites;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekbrains.domain.interactor.photos.ChangeFavoritePhotoStatusUseCase;
import ru.geekbrains.domain.interactor.photos.DeletePhotoUseCase;
import ru.geekbrains.domain.interactor.photos.GetFavoritesUseCase;
import ru.geekbrains.domain.model.PhotoModel;
import ru.geekbrains.geekbrainsinstagram.base.BasePresenter;
import ru.geekbrains.geekbrainsinstagram.di.fragment.FragmentScope;
import ru.geekbrains.geekbrainsinstagram.model.ViewPhotoModel;
import ru.geekbrains.geekbrainsinstagram.model.mapper.ViewPhotoModelMapper;
import ru.geekbrains.geekbrainsinstagram.ui.common.NotifyingMessage;

@FragmentScope
public final class FavoritesPresenter extends BasePresenter<IFavoritesPresenter.IView>
        implements IFavoritesPresenter {

    private final GetFavoritesUseCase getFavoritesUseCase;
    private final ChangeFavoritePhotoStatusUseCase changeFavoritePhotoStatusUseCase;
    private final DeletePhotoUseCase deletePhotoUseCase;
    private final ViewPhotoModelMapper photosMapper;

    @Inject
    FavoritesPresenter(GetFavoritesUseCase getFavoritesUseCase,
                       ChangeFavoritePhotoStatusUseCase changeFavoritePhotoStatusUseCase,
                       DeletePhotoUseCase deletePhotoUseCase,
                       ViewPhotoModelMapper photosMapper) {
        this.getFavoritesUseCase = getFavoritesUseCase;
        this.changeFavoritePhotoStatusUseCase = changeFavoritePhotoStatusUseCase;
        this.deletePhotoUseCase = deletePhotoUseCase;
        this.photosMapper = photosMapper;
    }

    @Override
    public void start() {
        uploadPhotos();
    }

    @Override
    public void deletePhotoFromFavorites(ViewPhotoModel photo) {
        PhotoModel photoModel = photosMapper.viewToDomainWithFavoriteChange(photo);
        addDisposable(changeFavoritePhotoStatusUseCase.execute(photoModel)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> successDeletePhotoFromFavorites(photo),
                        throwable -> errorDeletePhotoFromFavorites()));
    }

    @Override
    public void deletePhotoFromDevice(ViewPhotoModel photo) {
        PhotoModel photoModel = photosMapper.viewToDomain(photo);
        addDisposable(deletePhotoUseCase.execute(photoModel)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> successDeletePhotoFromDevice(photo),
                        throwable -> errorDeletePhotoFromDevice()));
    }

    private void uploadPhotos() {
        addDisposable(getFavoritesUseCase.execute()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(photos -> view.addPhotos(photosMapper.domainToView(photos)),
                        getDefaultErrorHandler()));
    }

    private void successDeletePhotoFromFavorites(ViewPhotoModel photo) {
        view.deletePhoto(photo);
        view.showNotifyingMessage(NotifyingMessage.PHOTO_SUCCESSFULLY_DELETED_FROM_FAVORITES);
    }

    private void successDeletePhotoFromDevice(ViewPhotoModel photo) {
        view.deletePhoto(photo);
        view.showNotifyingMessage(NotifyingMessage.PHOTO_SUCCESSFULLY_DELETED_FROM_DEVICE);
    }

    private void errorDeletePhotoFromFavorites() {
        view.showNotifyingMessage(NotifyingMessage.ERROR_DELETE_PHOTO_FROM_FAVORITES);
    }

    private void errorDeletePhotoFromDevice() {
        view.showNotifyingMessage(NotifyingMessage.ERROR_DELETE_PHOTO_FROM_DEVICE);
    }
}
