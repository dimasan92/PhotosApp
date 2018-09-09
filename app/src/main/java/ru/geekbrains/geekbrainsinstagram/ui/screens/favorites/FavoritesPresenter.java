package ru.geekbrains.geekbrainsinstagram.ui.screens.favorites;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekbrains.domain.interactor.photos.ChangeFavoritePhotoStatusUseCase;
import ru.geekbrains.domain.interactor.photos.DeletePhotoUseCase;
import ru.geekbrains.domain.interactor.photos.GetFavoritesUseCase;
import ru.geekbrains.domain.model.PhotoModel;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.base.BasePresenter;
import ru.geekbrains.geekbrainsinstagram.di.fragment.FragmentScope;
import ru.geekbrains.geekbrainsinstagram.model.PresentPhotoModel;
import ru.geekbrains.geekbrainsinstagram.model.mapper.IPresentModelPhotosMapper;

@FragmentScope
public final class FavoritesPresenter extends BasePresenter<IFavoritesPresenter.IView>
        implements IFavoritesPresenter {

    private final GetFavoritesUseCase getFavoritesUseCase;
    private final ChangeFavoritePhotoStatusUseCase changeFavoritePhotoStatusUseCase;
    private final DeletePhotoUseCase deletePhotoUseCase;
    private final IPresentModelPhotosMapper photosMapper;

    @Inject
    FavoritesPresenter(GetFavoritesUseCase getFavoritesUseCase,
                       ChangeFavoritePhotoStatusUseCase changeFavoritePhotoStatusUseCase,
                       DeletePhotoUseCase deletePhotoUseCase,
                       IPresentModelPhotosMapper photosMapper) {
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
    public void deletePhotoFromFavorites(PresentPhotoModel photo) {
        PhotoModel photoModel = photosMapper.viewToDomainWithFavoriteChange(photo);
        addDisposable(changeFavoritePhotoStatusUseCase.execute(photoModel)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> successDeletePhotoFromFavorites(photo),
                        throwable -> errorDeletePhotoFromFavorites()));
    }

    @Override
    public void deletePhotoFromDevice(PresentPhotoModel photo) {
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

    private void successDeletePhotoFromFavorites(PresentPhotoModel photo) {
        view.deletePhoto(photo);
        view.showNotifyingMessage(R.string.photo_successfully_deleted_from_favorites_message);
    }

    private void successDeletePhotoFromDevice(PresentPhotoModel photo) {
        view.deletePhoto(photo);
        view.showNotifyingMessage(R.string.photo_successfully_deleted_from_device_message);
    }

    private void errorDeletePhotoFromFavorites() {
        view.showNotifyingMessage(R.string.error_delete_photo_from_favorites_message);
    }

    private void errorDeletePhotoFromDevice() {
        view.showNotifyingMessage(R.string.error_delete_photo_from_device_message);
    }
}
