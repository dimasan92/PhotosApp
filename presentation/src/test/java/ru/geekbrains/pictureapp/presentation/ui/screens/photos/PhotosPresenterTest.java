package ru.geekbrains.pictureapp.presentation.ui.screens.photos;

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
import ru.geekbrains.pictureapp.domain.interactor.photos.camera.GetPhotosUseCase;
import ru.geekbrains.pictureapp.domain.interactor.photos.camera.GetPlaceForNewPhotoUseCase;
import ru.geekbrains.pictureapp.domain.interactor.photos.common.DeleteImageUseCase;
import ru.geekbrains.pictureapp.domain.interactor.photos.favorites.ChangeFavoriteStatusUseCase;
import ru.geekbrains.pictureapp.domain.model.ImageModel;
import ru.geekbrains.pictureapp.presentation.ui.base.photos.BaseListPresenter.ListView;
import ru.geekbrains.pictureapp.presentation.ui.navigator.MainNavigator;
import ru.geekbrains.pictureapp.presentation.ui.screens.photos.PhotosPresenter.PhotosView;
import ru.geekbrains.pictureapp.presentation.ui.screens.photos.PhotosPresenterImpl.PhotosListPresenterImpl;
import ru.geekbrains.pictureapp.presentation.ui.updater.Updater;
import ru.geekbrains.pictureapp.presentation.util.CameraUtils;
import ru.geekbrains.pictureapp.presentation.util.ParseUtils;

public final class PhotosPresenterTest {

    private PhotosPresenter presenter;
    private PhotosListPresenterImpl listPresenter;
    private TestScheduler testScheduler;

    @Mock GetPhotosUseCase getPhotosUseCase;
    @Mock GetPlaceForNewPhotoUseCase getPlaceForNewPhotoUseCase;
    @Mock ChangeFavoriteStatusUseCase changeFavoriteStatusUseCase;
    @Mock DeleteImageUseCase deleteImageUseCase;

    @Mock Updater updater;
    @Mock MainNavigator mainNavigator;
    @Mock CameraUtils cameraUtils;
    @Mock ParseUtils parseUtils;

    @Mock PhotosView mainView;
    @Mock ListView listView;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        testScheduler = new TestScheduler();
        listPresenter = Mockito.spy(new PhotosListPresenterImpl(changeFavoriteStatusUseCase,
                updater, testScheduler, mainNavigator, parseUtils));
        presenter = Mockito.spy(new PhotosPresenterImpl(getPhotosUseCase, getPlaceForNewPhotoUseCase,
                deleteImageUseCase, updater, listPresenter, testScheduler, cameraUtils));
    }

    @Test
    public void takeAPhotoTest() {
        final int okCode = 1;
        final ImageModel imageModel = new ImageModel("id", "path", "jpg");
        Mockito.when(getPlaceForNewPhotoUseCase.execute()).thenReturn(Single.just(imageModel));

        presenter.attachView(mainView);
        presenter.attachListView(listView);
        presenter.setCameraResultOkCode(okCode);

        presenter.takeAPhotoRequest();
        testScheduler.advanceTimeBy(10, TimeUnit.MILLISECONDS);
        Mockito.verify(mainView).startCamera(imageModel.getFilePath());

        presenter.cameraHasClosed(okCode);
        Mockito.verify(listPresenter).addImageModel(imageModel);
        Mockito.verify(cameraUtils).revokeCameraPermissions(imageModel.getFilePath());
    }

    @Test
    public void takeAPhotoRequestFailedTest() {
        Mockito.when(getPlaceForNewPhotoUseCase.execute())
                .thenReturn(Single.error(new RuntimeException("Wrong place")));

        presenter.attachView(mainView);
        presenter.takeAPhotoRequest();
        testScheduler.advanceTimeBy(10, TimeUnit.MILLISECONDS);
        Mockito.verify(mainView).showCouldNotLaunchCameraMessage();
    }

    @Test
    public void deletePhotoTest() {
        final ImageModel imageModel = new ImageModel("id", "path", "jpg");
        final List<ImageModel> imageModels = new ArrayList<>();
        imageModels.add(imageModel);
        Mockito.when(deleteImageUseCase.execute(imageModel)).thenReturn(Completable.complete());
        Mockito.when(getPhotosUseCase.execute()).thenReturn(Single.just(imageModels));

        presenter.attachView(mainView);
        presenter.attachListView(listView);
        presenter.start();
        presenter.deletePhotoConfirm(imageModel);
        testScheduler.advanceTimeBy(10, TimeUnit.MILLISECONDS);

        Mockito.verify(listPresenter).deleteImageModel(imageModel);
        Mockito.verify(updater).update(imageModel);
    }

    @Test
    public void deletePhotoFailedTest() {
        final ImageModel imageModel = new ImageModel("id", "path", "jpg");
        Mockito.when(deleteImageUseCase.execute(imageModel))
                .thenReturn(Completable.error(new RuntimeException("Error deleting")));

        presenter.attachView(mainView);
        presenter.deletePhotoConfirm(imageModel);
        testScheduler.advanceTimeBy(10, TimeUnit.MILLISECONDS);

        Mockito.verify(mainView).showErrorDeletingPhotoMessage();
    }

    @Test
    public void onFavoritesClickTest() {
        final List<ImageModel> imageModels = Arrays.asList(new ImageModel("id1", "path1", "jpg"),
                new ImageModel("id2", "path2", "jpg"));
        final ImageModel changedModel = new ImageModel(imageModels.get(0), !imageModels.get(0).isFavorite());
        Mockito.when(getPhotosUseCase.execute()).thenReturn(Single.just(imageModels));
        Mockito.when(changeFavoriteStatusUseCase.execute(imageModels.get(0))).thenReturn(Single.just(changedModel));

        verifyUploadPhotos();

        listPresenter.onFavoriteClick(0);
        testScheduler.advanceTimeBy(10, TimeUnit.MILLISECONDS);

        Mockito.verify(listView).updatePhoto(0);
        Mockito.verify(updater).update(changedModel);
    }

    @Test
    public void onFavoritesClickFailedTest() {
        final List<ImageModel> imageModels = Arrays.asList(new ImageModel("id1", "path1", "jpg"),
                new ImageModel("id2", true, "path2", "jpg"));
        Mockito.when(getPhotosUseCase.execute()).thenReturn(Single.just(imageModels));
        Mockito.when(changeFavoriteStatusUseCase.execute(imageModels.get(0)))
                .thenReturn(Single.error(new RuntimeException("Error change status")));
        Mockito.when(changeFavoriteStatusUseCase.execute(imageModels.get(1)))
                .thenReturn(Single.error(new RuntimeException("Error change status")));

        verifyUploadPhotos();

        listPresenter.onFavoriteClick(0);
        testScheduler.advanceTimeBy(10, TimeUnit.MILLISECONDS);
        Mockito.verify(mainView).showErrorAddingToFavoritesMessage();

        listPresenter.onFavoriteClick(1);
        testScheduler.advanceTimeBy(10, TimeUnit.MILLISECONDS);
        Mockito.verify(mainView).showErrorDeletingFromFavoritesMessage();
    }

    @Test
    public void onDeleteClickTest() {
        final List<ImageModel> imageModels = Arrays.asList(new ImageModel("id1", "path1", "jpg"),
                new ImageModel("id2", "path2", "jpg"));
        Mockito.when(getPhotosUseCase.execute()).thenReturn(Single.just(imageModels));

        verifyUploadPhotos();

        listPresenter.onDeleteClick(0);
        testScheduler.advanceTimeBy(10, TimeUnit.MILLISECONDS);

        Mockito.verify(mainView).showPhotoDeleteDialog(imageModels.get(0));
    }

    @Test
    public void onFullClickTest() {
        final List<ImageModel> imageModels = Arrays.asList(new ImageModel("id1", "path1", "jpg"),
                new ImageModel("id2", "path2", "jpg"));
        final String[] jsons = {"1", "2"};
        Mockito.when(parseUtils.parseObjects(imageModels)).thenReturn(jsons);
        Mockito.when(getPhotosUseCase.execute()).thenReturn(Single.just(imageModels));

        verifyUploadPhotos();

        listPresenter.onFullClick(0);
        testScheduler.advanceTimeBy(10, TimeUnit.MILLISECONDS);

        Mockito.verify(mainNavigator).navigateToDetails(jsons, 0);
    }

    private void verifyUploadPhotos() {
        presenter.attachView(mainView);
        presenter.attachListView(listView);
        presenter.start();
        testScheduler.advanceTimeBy(10, TimeUnit.MILLISECONDS);
        Mockito.verify(listView).updatePhotos();
    }
}
