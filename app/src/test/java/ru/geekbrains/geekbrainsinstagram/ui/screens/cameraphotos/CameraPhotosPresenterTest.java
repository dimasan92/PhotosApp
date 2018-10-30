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
import ru.geekbrains.domain.interactor.photos.DeletePhotoUseCase;
import ru.geekbrains.domain.interactor.photos.GetCameraPhotosUseCase;
import ru.geekbrains.domain.interactor.photos.GetPlaceForNewCameraPhotoUseCase;
import ru.geekbrains.domain.interactor.photos.SetFavoritePhotoStatusUseCase;
import ru.geekbrains.domain.model.PhotoModel;
import ru.geekbrains.geekbrainsinstagram.ui.base.photos.BaseListPresenter.ListView;
import ru.geekbrains.geekbrainsinstagram.ui.screens.cameraphotos.CameraPhotosPresenter.CameraPhotosView;
import ru.geekbrains.geekbrainsinstagram.util.CameraUtils;

public final class CameraPhotosPresenterTest {

    private CameraPhotosPresenter presenter;
    private TestScheduler testScheduler;

    @Mock CameraPhotosView view;
    @Mock GetCameraPhotosUseCase getCameraPhotosUseCase;
    @Mock GetPlaceForNewCameraPhotoUseCase getPlaceForNewCameraPhotoUseCase;
    @Mock SetFavoritePhotoStatusUseCase setFavoritePhotoStatusUseCase;
    @Mock DeletePhotoUseCase deletePhotoUseCase;
    @Mock CameraPhotoUpdaterUseCase cameraPhotoUpdaterUseCase;
    @Mock CameraUtils cameraUtils;

    @Mock ListView listView;

    @Before public void setup() {
        MockitoAnnotations.initMocks(this);
        testScheduler = new TestScheduler();
        presenter = Mockito.spy(new CameraPhotosPresenterImpl(getCameraPhotosUseCase, getPlaceForNewCameraPhotoUseCase,
                setFavoritePhotoStatusUseCase, deletePhotoUseCase, cameraPhotoUpdaterUseCase, testScheduler, cameraUtils));
    }

    @Test public void updatesPhotosTest() {
        final List<PhotoModel> photoModels = Arrays.asList(new PhotoModel("id1", "path1"),
                new PhotoModel("id2", "path2"));
        Mockito.when(getCameraPhotosUseCase.execute()).thenReturn(Single.just(photoModels));

        presenter.attachListView(listView);
        presenter.start();
        testScheduler.advanceTimeBy(10, TimeUnit.MILLISECONDS);

        Mockito.verify(listView).updatePhotos();
    }

    @Test public void takeAPhotoRequestFailedTest() {
        Mockito.when(getPlaceForNewCameraPhotoUseCase.execute())
                .thenReturn(Single.error(new RuntimeException("Wrong place")));

        presenter.attachView(view);
        presenter.takeAPhotoRequest();
        testScheduler.advanceTimeBy(10, TimeUnit.MILLISECONDS);
        Mockito.verify(view).showCouldNotLaunchCameraMessage();
    }

    @Test public void takeAPhotoTest() {
        final int okCode = 1;
        final PhotoModel photoModel = new PhotoModel("id", "path");
        Mockito.when(getPlaceForNewCameraPhotoUseCase.execute()).thenReturn(Single.just(photoModel));

        presenter.attachView(view);
        presenter.attachListView(listView);
        presenter.setCameraResultOkCode(okCode);

        presenter.takeAPhotoRequest();
        testScheduler.advanceTimeBy(10, TimeUnit.MILLISECONDS);
        Mockito.verify(view).startCamera(photoModel.getFilePath());

        presenter.cameraHasClosed(okCode);
        Mockito.verify(listView).updatePhoto(0);
        Mockito.verify(view).showPhotoSuccessAddedMessage();
        Mockito.verify(cameraUtils).revokeCameraPermissions(photoModel.getFilePath());
    }

    @Test public void deletePhotoFailedTest() {
        final PhotoModel photoModel = new PhotoModel("id", "path");
        Mockito.when(deletePhotoUseCase.execute(photoModel))
                .thenReturn(Completable.error(new RuntimeException("Error deleting")));

        presenter.attachView(view);
        presenter.deletePhotoConfirm(photoModel);
        testScheduler.advanceTimeBy(10, TimeUnit.MILLISECONDS);

        Mockito.verify(view).showErrorDeletingPhotoMessage();
    }

    @Test public void deletePhotoTest() {
        final PhotoModel photoModel = new PhotoModel("id", "path");
        final List<PhotoModel> photoModels = new ArrayList<>();
        photoModels.add(photoModel);
        Mockito.when(deletePhotoUseCase.execute(photoModel)).thenReturn(Completable.complete());
        Mockito.when(getCameraPhotosUseCase.execute()).thenReturn(Single.just(photoModels));

        presenter.attachView(view);
        presenter.attachListView(listView);
        presenter.start();
        presenter.deletePhotoConfirm(photoModel);
        testScheduler.advanceTimeBy(10, TimeUnit.MILLISECONDS);

        Mockito.verify(listView).deletePhoto(0);
        Mockito.verify(cameraPhotoUpdaterUseCase).execute();
        Mockito.verify(view).showPhotoSuccessDeletedMessage();
    }
}
