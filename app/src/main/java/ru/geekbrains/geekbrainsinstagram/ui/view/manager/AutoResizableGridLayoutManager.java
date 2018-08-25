package ru.geekbrains.geekbrainsinstagram.ui.view.manager;

import android.content.Context;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AutoResizableGridLayoutManager extends GridLayoutManager {

    private static final int ITEM_PHOTO_WIDTH = 100;
    private static final int MIN_SPAN_COUNT = 1;
    private boolean isInitialState = true;

    public AutoResizableGridLayoutManager(Context context) {
        super(context, MIN_SPAN_COUNT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (isInitialState) {
            int layoutWorkingArea = getWidth() - getPaddingStart() - getPaddingEnd();
            int spanCount = Math.max(MIN_SPAN_COUNT, layoutWorkingArea / ITEM_PHOTO_WIDTH);
            setSpanCount(spanCount);
            isInitialState = false;
        }

        super.onLayoutChildren(recycler, state);
    }
}
