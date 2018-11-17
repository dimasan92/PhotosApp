package ru.geekbrains.pictureapp.presentation.util;

import android.content.Context;
import android.net.Uri;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.core.content.FileProvider;
import ru.geekbrains.pictureapp.R;

@Singleton
public final class ContentUtilsImpl implements ContentUtils {

    private final String authority;
    private final Context appContext;

    @Inject ContentUtilsImpl(final Context context) {
        appContext = context.getApplicationContext();
        authority = context.getResources().getString(R.string.authority);
    }

    @Override public Uri getUri(final String filePath) {
        System.out.println("!!!!!!!!!"+filePath);
        return FileProvider.getUriForFile(appContext, authority, new File(filePath));
    }

    @Override public String getExtensionForUnsplashPhoto(final String url) {
        return Uri.parse(url).getQueryParameter("fm");
    }
}
