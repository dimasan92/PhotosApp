package ru.geekbrains.geekbrainsinstagram.util;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.recyclerview.widget.GridLayoutManager;

@Singleton
public final class LayoutUtils implements ILayoutUtils {

    private static final int APPROX_WIDTH_IN_DP = 200;
    private static final int MIN_SPAN_COUNT = 2;

    private final Context appContext;

    @Inject
    LayoutUtils(Context appContext) {
        this.appContext = appContext;
    }

    @Override
    public GridLayoutManager getAdjustedGridLayoutManager() {
        return new GridLayoutManager(appContext, getSpanCount());
    }

    @Override
    public int getPhotoSize() {
        return getDisplayMetrics().widthPixels / getSpanCount();
    }

    private int getSpanCount() {
        float widthInDp = getDisplayMetrics().widthPixels / getDisplayMetrics().density;
        int spanCount = (int) (widthInDp / APPROX_WIDTH_IN_DP);
        if (spanCount < MIN_SPAN_COUNT) {
            return MIN_SPAN_COUNT;
        } else {
            return spanCount;
        }
    }

    private DisplayMetrics getDisplayMetrics() {
        return appContext.getResources().getDisplayMetrics();
    }
}
