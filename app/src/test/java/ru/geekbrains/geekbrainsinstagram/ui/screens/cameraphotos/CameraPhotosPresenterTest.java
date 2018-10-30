package ru.geekbrains.geekbrainsinstagram.ui.screens.cameraphotos;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;
import ru.geekbrains.domain.interactor.photos.CameraPhotoUpdaterUseCase;
import ru.geekbrains.domain.interactor.photos.ChangePhotoFavoriteStatusUseCase;
import ru.geekbrains.domain.interactor.photos.DeletePhotoUseCase;
import ru.geekbrains.domain.interactor.photos.GetCameraPhotosUseCase;
import ru.geekbrains.domain.interactor.photos.GetPlaceForNewCameraPhotoUseCase;
import ru.geekbrains.domain.model.PhotoModel;
import ru.geekbrains.geekbrainsinstagram.ui.base.photos.BaseListPresenter.ListView;
import ru.geekbrains.geekbrainsinstagram.ui.screens.cameraphotos.CameraPhotosPresenter.CameraPhotosView;
import ru.geekbrains.geekbrainsinstagram.ui.screens.cameraphotos.CameraPhotosPresenterImpl.CameraPhotosListPresenterImpl;
import ru.geekbrains.geekbrainsinstagram.util.CameraUtils;

public final class CameraPhotosPresenterTest {

    private CameraPhotosPresenter presenter;
    private CameraPhotosListPresenterImpl listPresenter;
    private TestScheduler testScheduler;

    @Mock GetCameraPhotosUseCase getCameraPhotosUseCase;
    @Mock GetPlaceForNewCameraPhotoUseCase getPlaceForNewCameraPhotoUseCase;
    @Mock ChangePhotoFavoriteStatusUseCase changePhotoFavoriteStatusUseCase;
    @Mock DeletePhotoUseCase deletePhotoUseCase;
    @Mock CameraPhotoUpdaterUseCase cameraPhotoUpdaterUseCase;
    @Mock CameraUtils cameraUtils;

    @Mock CameraPhotosView mainView;
    @Mock ListView listView;

    @Before public void setup() {
        MockitoAnnotations.initMocks(this);
        testScheduler = new TestScheduler();
        listPresenter = Mockito.spy(new CameraPhotosListPresenterImpl(changePhotoFavoriteStatusUseCase,
                cameraPhotoUpdaterUseCase, testScheduler));
        presenter = Mockito.spy(new CameraPhotosPresenterImpl(getCameraPhotosUseCase, getPlaceForNewCameraPhotoUseCase,
                deletePhotoUseCase, cameraPhotoUpdaterUseCase, listPresenter, testScheduler, cameraUtils));
    }

    @Test public void takeAPhotoTest() {
        final int okCode = 1;
        final PhotoModel photoModel = new PhotoModel("id", "path");
        Mockito.when(getPlaceForNewCameraPhotoUseCase.execute()).thenReturn(Single.just(photoModel));

        presenter.attachView(mainView);
        presenter.attachListView(listView);
        presenter.setCameraResultOkCode(okCode);

        presenter.takeAPhotoRequest();
        testScheduler.advanceTimeBy(10, TimeUnit.MILLISECONDS);
        Mockito.verify(mainView).startCamera(photoModel.getFilePath());

        presenter.cameraHasClosed(okCode);
        Mockito.verify(listPresenter).addPhotoModel(photoModel);
        Mockito.verify(mainView).showPhotoSuccessAddedMessage();
        Mockito.verify(cameraUtils).revokeCameraPermissions(photoModel.getFilePath());
    }

    @Test public void takeAPhotoRequestFailedTest() {
        Mockito.when(getPlaceForNewCameraPhotoUseCase.execute())
                .thenReturn(Single.error(new RuntimeException("Wrong place")));

        presenter.attachView(mainView);
        presenter.takeAPhotoRequest();
        testScheduler.advanceTimeBy(10, TimeUnit.MILLISECONDS);
        Mockito.verify(mainView).showCouldNotLaunchCameraMessage();
    }

    @Test public void deletePhotoTest() {
        final PhotoModel photoModel = new PhotoModel("id", "path");
        final List<PhotoModel> photoModels = new ArrayList<>();
        photoModels.add(photoModel);
        Mockito.when(deletePhotoUseCase.execute(photoModel)).thenReturn(Completable.complete());
        Mockito.when(getCameraPhotosUseCase.execute()).thenReturn(Single.just(photoModels));

        presenter.attachView(mainView);
        presenter.attachListView(listView);
        presenter.start();
        presenter.deletePhotoConfirm(photoModel);
        testScheduler.advanceTimeBy(10, TimeUnit.MILLISECONDS);

        Mockito.verify(listPresenter).deletePhotoModel(photoModel);
        Mockito.verify(cameraPhotoUpdaterUseCase).execute();
        Mockito.verify(mainView).showPhotoSuccessDeletedMessage();
    }

    @Test public void deletePhotoFailedTest() {
        final PhotoModel photoModel = new PhotoModel("id", "path");
        Mockito.when(deletePhotoUseCase.execute(photoModel))
                .thenReturn(Completable.error(new RuntimeException("Error deleting")));

        presenter.attachView(mainView);
        presenter.deletePhotoConfirm(photoModel);
        testScheduler.advanceTimeBy(10, TimeUnit.MILLISECONDS);

        Mockito.verify(mainView).showErrorDeletingPhotoMessage();
    }

    @Test public void onFavoritesClickTest() {
        final List<PhotoModel> photoModels = Arrays.asList(new PhotoModel("id1", "path1"),
                new PhotoModel("id2", "path2"));
        final PhotoModel changedModel = new PhotoModel(photoModels.get(0), !photoModels.get(0).isFavorite());
        Mockito.when(getCameraPhotosUseCase.execute()).thenReturn(Single.just(photoModels));
        Mockito.when(changePhotoFavoriteStatusUseCase.execute(photoModels.get(0))).thenReturn(Single.just(changedModel));

        presenter.attachView(mainView);
        presenter.attachListView(listView);
        presenter.start();
        testScheduler.advanceTimeBy(10, TimeUnit.MILLISECONDS);
        Mockito.verify(listView).updatePhotos();

        listPresenter.onFavoriteClick(0);
        testScheduler.advanceTimeBy(10, TimeUnit.MILLISECONDS);

        Mockito.verify(listView).updatePhoto(0);
        Mockito.verify(cameraPhotoUpdaterUseCase).execute();
    }

    @Test public void onFavoritesClickFailedTest() {
        final List<PhotoModel> photoModels = Arrays.asList(new PhotoModel("id1", "path1"),
                new PhotoModel("id2", "path2"));
        Mockito.when(getCameraPhotosUseCase.execute()).thenReturn(Single.just(photoModels));
        Mockito.when(changePhotoFavoriteStatusUseCase.execute(photoModels.get(0)))
                .thenReturn(Single.error(new RuntimeException("Error change status")));

        presenter.attachView(mainView);
        presenter.attachListView(listView);
        presenter.start();
        testScheduler.advanceTimeBy(10, TimeUnit.MILLISECONDS);
        Mockito.verify(listView).updatePhotos();

        listPresenter.onFavoriteClick(0);
        testScheduler.advanceTimeBy(10, TimeUnit.MILLISECONDS);

        Mockito.verify(mainView).showErrorDeletingFromFavoritesMessage();
    }

    @Test public void onDeleteClickTest() {
        final List<PhotoModel> photoModels = Arrays.asList(new PhotoModel("id1", "path1"),
                new PhotoModel("id2", "path2"));
        Mockito.when(getCameraPhotosUseCase.execute()).thenReturn(Single.just(photoModels));

        presenter.attachView(mainView);
        presenter.attachListView(listView);
        presenter.start();
        testScheduler.advanceTimeBy(10, TimeUnit.MILLISECONDS);
        Mockito.verify(listView).updatePhotos();

        listPresenter.onDeleteClick(0);
        testScheduler.advanceTimeBy(10, TimeUnit.MILLISECONDS);

        Mockito.verify(mainView).showPhotoDeleteDialog(photoModels.get(0));
    }
}
