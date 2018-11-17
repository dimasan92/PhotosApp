package ru.geekbrains.pictureapp.presentation.ui.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnticipateInterpolator;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

public final class CustomFabBehavior extends FloatingActionButton.Behavior {
    public CustomFabBehavior() {
    }

    public CustomFabBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                               @NonNull FloatingActionButton child,
                               @NonNull View target, int dxConsumed, int dyConsumed,
                               int dxUnconsumed, int dyUnconsumed,
                               @ViewCompat.NestedScrollType int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed,
                dxUnconsumed, dyUnconsumed, type);

        if (dyConsumed > 0) {
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
            int fab_bottomMargin = layoutParams.bottomMargin;
            child.animate().alpha(0.0f).translationY(child.getHeight() + fab_bottomMargin)
                    .setInterpolator(new AnticipateInterpolator()).start();
        } else if (dyConsumed < 0) {
            child.animate().alpha(1.0f)
                    .translationY(0).setInterpolator(new AnticipateInterpolator()).start();
        }
    }

    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                       @NonNull FloatingActionButton child,
                                       @NonNull View directTargetChild, @NonNull View target,
                                       @ViewCompat.ScrollAxis int axes,
                                       @ViewCompat.NestedScrollType int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

}
