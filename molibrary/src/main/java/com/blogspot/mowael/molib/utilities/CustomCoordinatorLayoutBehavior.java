package com.blogspot.mowael.molib.utilities;

import android.content.Context;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.blogspot.mowael.utilslibrary.Logger;

/**
 * Created by mwael on 4/25/2017.
 */

public class CustomCoordinatorLayoutBehavior<T extends View> extends CoordinatorLayout.Behavior<T> {

    private int scrollFactor = 10;
    private int scrollAnimationDuration = 500;

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
        if (dyConsumed > 0 && dyConsumed >= scrollFactor) {
           Logger.d("dyConsumed > 0", dxConsumed + "");
		   
            if (!hide) {
                Animation animation = new TranslateAnimation(0, 0, 0, child.getHeight());
                animation.setDuration(scrollAnimationDuration);
                animation.setFillAfter(true);
                child.startAnimation(animation);

                hide = true;
            }

        } else if (dyConsumed < 0 && (Math.abs(dyConsumed) >= scrollFactor)) {
          Logger.d("dyConsumed < 0", dyConsumed + "");
		  
            if (hide) {

                Animation animation = new TranslateAnimation(0, 0, child.getHeight(), 0);
                animation.setDuration(scrollAnimationDuration);
                animation.setFillAfter(true);
                child.startAnimation(animation);

                hide = false;
            }
        }

    }
}