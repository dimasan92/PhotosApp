package ru.geekbrains.geekbrainsinstagram.util;

import android.content.Intent;

import ru.geekbrains.geekbrainsinstagram.exception.CameraCannotLaunchException;
import ru.geekbrains.geekbrainsinstagram.model.ViewPhotoModel;

public interface CameraUtils {

    Intent getAdjustedCameraInvoker(final ViewPhotoModel photoModel) throws CameraCannotLaunchException;

    void revokeCameraPermissions(final ViewPhotoModel photoModel);
}
