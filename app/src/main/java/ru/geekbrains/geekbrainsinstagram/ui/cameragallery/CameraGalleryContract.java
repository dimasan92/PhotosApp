package ru.geekbrains.geekbrainsinstagram.ui.cameragallery;

import androidx.annotation.StringRes;
import ru.geekbrains.geekbrainsinstagram.base.BaseContract;

public interface CameraGalleryContract {

    interface View extends BaseContract.View {

        void showNotifyingMessage(@StringRes int message);
    }

    interface Presenter extends BaseContract.Presenter<View> {

    }
}
