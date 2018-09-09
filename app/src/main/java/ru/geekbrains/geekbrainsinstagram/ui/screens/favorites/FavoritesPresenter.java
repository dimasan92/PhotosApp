package ru.geekbrains.geekbrainsinstagram.ui.screens.favorites;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekbrains.domain.interactor.photos.GetFavoritesUseCase;
import ru.geekbrains.geekbrainsinstagram.base.BasePresenter;
import ru.geekbrains.geekbrainsinstagram.di.fragment.FragmentScope;
import ru.geekbrains.geekbrainsinstagram.model.PresentPhotoModel;
import ru.geekbrains.geekbrainsinstagram.model.mapper.IPresentModelPhotosMapper;

@FragmentScope
public final class FavoritesPresenter extends BasePresenter<IFavoritesPresenter.IView>
        implements IFavoritesPresenter {

    private final GetFavoritesUseCase getFavoritesUseCase;
    private final IPresentModelPhotosMapper photosMapper;

    @Inject
    FavoritesPresenter(GetFavoritesUseCase getFavoritesUseCase,
                       IPresentModelPhotosMapper photosMapper) {
        this.getFavoritesUseCase = getFavoritesUseCase;
        this.photosMapper = photosMapper;
    }

    @Override
    public void start() {
        uploadPhotos();
    }

    @Override
    public void deletePhotoFromFavorites(PresentPhotoModel photo) {

    }

    @Override
    public void deletePhotoFromDevice(PresentPhotoModel photo) {

    }

    private void uploadPhotos() {
        addDisposable(getFavoritesUseCase.execute()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(photos -> view.addPhotos(photosMapper.domainToView(photos)),
                        getDefaultErrorHandler()));
    }
}
