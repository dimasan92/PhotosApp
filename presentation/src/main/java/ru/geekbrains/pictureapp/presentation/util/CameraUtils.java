package ru.geekbrains.pictureapp.presentation.util;

import android.content.Intent;

import ru.geekbrains.pictureapp.presentation.exception.CameraCannotLaunchException;

public interface CameraUtils {

    Intent getAdjustedCameraInvoker(final String filePath) throws CameraCannotLaunchException;

    void revokeCameraPermissions(final String filePath);
}
