package com.robapp.app.listener;

import android.view.View;
import android.view.animation.Animation;

/**
 * Created by Arthur on 16/10/2016.
 */

public class AnimationListenerFadeIn implements Animation.AnimationListener{

    private View view;

    public AnimationListenerFadeIn(View view){
        this.view = view;
    }

    @Override
    public void onAnimationStart(Animation animation) {
        view.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAnimationEnd(Animation animation) {
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
