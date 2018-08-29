package ru.geekbrains.geekbrainsinstagram.utils;

import android.content.Context;
import android.content.res.Configuration;

import androidx.recyclerview.widget.GridLayoutManager;

public final class LayoutUtils implements ILayoutUtils {

    private static final int PORTRAIT_SPAN_COUNT = 2;
    private static final int LANDSCAPE_SPAN_COUNT = 3;

    private final Context appContext;

    public LayoutUtils(Context appContext) {
        this.appContext = appContext;
    }

    @Override
    public GridLayoutManager getAdjustedGridLayoutManager(int orientation) {
        int spanCount = PORTRAIT_SPAN_COUNT;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            spanCount = LANDSCAPE_SPAN_COUNT;
        }
        return new GridLayoutManager(appContext, spanCount);
    }
}
