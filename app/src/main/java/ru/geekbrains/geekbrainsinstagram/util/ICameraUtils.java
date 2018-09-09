package ru.geekbrains.geekbrainsinstagram.util;

import android.content.Intent;

import ru.geekbrains.geekbrainsinstagram.exception.CameraCannotLaunchException;
import ru.geekbrains.geekbrainsinstagram.model.PresentPhotoModel;

public interface ICameraUtils {

    Intent getAdjustedCameraInvoker(PresentPhotoModel photoModel) throws CameraCannotLaunchException;

    void revokeCameraPermissions(PresentPhotoModel photoModel);
}
