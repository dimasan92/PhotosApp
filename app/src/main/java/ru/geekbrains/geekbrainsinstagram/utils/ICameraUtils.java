package ru.geekbrains.geekbrainsinstagram.utils;

import android.content.Intent;

import ru.geekbrains.geekbrainsinstagram.exception.LaunchCameraException;
import ru.geekbrains.geekbrainsinstagram.ui.model.PhotoModel;

public interface ICameraUtils {

    Intent getAdjustedCameraInvoker(PhotoModel photoModel) throws LaunchCameraException;

    void revokeCameraPermissions(PhotoModel photoModel);
}
