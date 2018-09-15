package ru.geekbrains.geekbrainsinstagram.util;

import android.content.Context;
import android.util.DisplayMetrics;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.recyclerview.widget.GridLayoutManager;
import ru.geekbrains.geekbrainsinstagram.R;

@Singleton
public final class LayoutUtilsImpl implements LayoutUtils {

    private static final int APPROXIMATELY_WIDTH_IN_DP = 200;
    private static final int MIN_SPAN_COUNT = 2;
    private final int mainLayoutMargin;
    private final int itemLayoutMargin;

    private final Context appContext;

    @Inject
    LayoutUtilsImpl(Context context) {
        appContext = context.getApplicationContext();
        mainLayoutMargin = (int) (context.getResources().getDimension(R.dimen.smallest_margin));
        itemLayoutMargin = (int) (context.getResources().getDimension(R.dimen.smallest_margin));
    }

    @Override
    public GridLayoutManager getAdjustedGridLayoutManager() {
        return new GridLayoutManager(appContext, getSpanGridCount());
    }

    @Override
    public int getGridPhotoSize() {
        return (getDisplayMetrics().widthPixels - getFullGridMargin()) / getSpanGridCount();
    }

    private int getFullGridMargin() {
        return mainLayoutMargin * 2 + itemLayoutMargin * 2 * getSpanGridCount();
    }

    private int getSpanGridCount() {
        float widthInDp = getDisplayMetrics().widthPixels / getDisplayMetrics().density;
        int spanCount = (int) (widthInDp / APPROXIMATELY_WIDTH_IN_DP);
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
