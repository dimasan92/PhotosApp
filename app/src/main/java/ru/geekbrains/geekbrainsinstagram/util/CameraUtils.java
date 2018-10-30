package ru.geekbrains.geekbrainsinstagram.util;

import android.content.Intent;

import ru.geekbrains.geekbrainsinstagram.exception.CameraCannotLaunchException;

public interface CameraUtils {

    Intent getAdjustedCameraInvoker(final String filePath) throws CameraCannotLaunchException;

    void revokeCameraPermissions(final String filePath);
}
