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

import ru.geekbrains.geekbrainsinstagram.exception.CameraCannotLaunchException;
import ru.geekbrains.geekbrainsinstagram.model.ViewPhotoModel;

@Singleton
public final class CameraUtilsImpl implements CameraUtils {

    private final Context appContext;
    private final ContentUtils contentUtils;

    @Inject
    CameraUtilsImpl(Context context, ContentUtils contentUtils) {
        this.appContext = context.getApplicationContext();
        this.contentUtils = contentUtils;
    }

    @Override
    public Intent getAdjustedCameraInvoker(final ViewPhotoModel photoModel)
            throws CameraCannotLaunchException {
        final Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (isCameraAvailable(cameraIntent)) {
            Uri uri = contentUtils.getUriForPhoto(photoModel);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            setCameraPermissions(cameraIntent, uri);
            return cameraIntent;
        }
        throw new CameraCannotLaunchException();
    }

    @Override
    public void revokeCameraPermissions(final ViewPhotoModel photoModel) {
        appContext.revokeUriPermission(contentUtils.getUriForPhoto(photoModel),
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
    }

    private boolean isCameraAvailable(final Intent cameraIntent) {
        return cameraIntent.resolveActivity(appContext.getPackageManager()) != null;
    }

    private void setCameraPermissions(final Intent cameraIntent, final Uri uri) {
        List<ResolveInfo> cameraActivities = appContext.getPackageManager()
                .queryIntentActivities(cameraIntent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo activity : cameraActivities) {
            appContext.grantUriPermission(activity.activityInfo.packageName,
                    uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
    }
}
