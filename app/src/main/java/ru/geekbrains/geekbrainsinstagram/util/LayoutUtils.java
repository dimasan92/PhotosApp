package ru.geekbrains.geekbrainsinstagram.util;

import android.content.Context;
import android.util.DisplayMetrics;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.recyclerview.widget.GridLayoutManager;
import ru.geekbrains.geekbrainsinstagram.R;

@Singleton
public final class LayoutUtils implements ILayoutUtils {

    private static final int APPROX_WIDTH_IN_DP = 200;
    private static final int MIN_SPAN_COUNT = 2;
    private final int mainLayoutMargin;
    private final int itemLayoutMargin;

    private final Context appContext;

    @Inject
    LayoutUtils(Context appContext) {
        this.appContext = appContext;
        mainLayoutMargin = (int) (appContext.getResources().getDimension(R.dimen.smallest_margin));
        itemLayoutMargin = (int) (appContext.getResources().getDimension(R.dimen.smallest_margin));
    }

    @Override
    public GridLayoutManager getAdjustedGridLayoutManager() {
        return new GridLayoutManager(appContext, getSpanCountGrid());
    }

    @Override
    public int getPhotoSizeGrid() {
        return (getDisplayMetrics().widthPixels - getFullMarginGrid()) / getSpanCountGrid();
    }

    private int getFullMarginGrid() {
        return mainLayoutMargin * 2 + itemLayoutMargin * 2 * getSpanCountGrid();
    }

    private int getSpanCountGrid() {
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
