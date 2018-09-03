package ru.geekbrains.geekbrainsinstagram.util;

import android.content.Intent;

import ru.geekbrains.geekbrainsinstagram.exception.LaunchCameraException;
import ru.geekbrains.geekbrainsinstagram.model.PresentPhotoModel;

public interface ICameraUtils {

    Intent getAdjustedCameraInvoker(PresentPhotoModel photoModel) throws LaunchCameraException;

    void revokeCameraPermissions(PresentPhotoModel photoModel);
}
