package com.blogspot.mowael.molib.utilities;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Created by moham on 4/25/2017.
 */

public class CustomCoordinatorLayoutBehavior<T extends View> extends CoordinatorLayout.Behavior<T>{

    public CustomCoordinatorLayoutBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, T child, View directTargetChild, View target, int nestedScrollAxes) {
//        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);

        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL ||
                super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    boolean hide = false;

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, final T child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);

        Logger.e("dyConsumed", dyConsumed + "");
        Logger.e("dyUnconsumed", dyUnconsumed + "");

        if (dyConsumed > 0) {
            Logger.d("dyConsumed > 0", dxConsumed + "");

            if (!hide) {
//                YoYo.with(Techniques.FadeOutDown).playOn(child);
                Animation animation = new TranslateAnimation(0, 0, 0, child.getHeight());
                animation.setDuration(500);
                animation.setFillAfter(true);
                child.startAnimation(animation);

                hide = true;
            }
//            child.hide();
        } else if (dyConsumed < 0) {
            Logger.d("dyConsumed < 0", dyConsumed + "");
            if (hide) {
//                YoYo.with(Techniques.FadeInUp).playOn(child);
                Animation animation = new TranslateAnimation(0, 0, child.getHeight(), 0);
                animation.setDuration(500);
                animation.setFillAfter(true);
                child.startAnimation(animation);

                hide = false;
            }
//            child.show();
        }

    }
}
