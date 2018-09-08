package ru.geekbrains.geekbrainsinstagram.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.geekbrains.geekbrainsinstagram.exception.LaunchCameraException;
import ru.geekbrains.geekbrainsinstagram.model.PresentPhotoModel;

@Singleton
public final class CameraUtils implements ICameraUtils {

    private final Context appContext;
    private final IContentUtils contentUtils;

    @Inject
    CameraUtils(Context appContext, IContentUtils contentUtils) {
        this.appContext = appContext;
        this.contentUtils = contentUtils;
    }

    @Override
    public Intent getAdjustedCameraInvoker(PresentPhotoModel photoModel) throws LaunchCameraException {
        final Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (isCameraAvailable(cameraIntent)) {
            Uri uri = contentUtils.getUriForPhoto(photoModel);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            setCameraPermissions(cameraIntent, uri);
            return cameraIntent;
        }
        throw new LaunchCameraException();
    }

    @Override
    public void revokeCameraPermissions(PresentPhotoModel photoModel) {
        appContext.revokeUriPermission(contentUtils.getUriForPhoto(photoModel),
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
    }

    private boolean isCameraAvailable(Intent cameraIntent) {
        return cameraIntent.resolveActivity(appContext.getPackageManager()) != null;
    }

    private void setCameraPermissions(Intent cameraIntent, Uri uri) {
        List<ResolveInfo> cameraActivities = appContext.getPackageManager()
                .queryIntentActivities(cameraIntent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo activity : cameraActivities) {
            appContext.grantUriPermission(activity.activityInfo.packageName,
                    uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
    }
}
