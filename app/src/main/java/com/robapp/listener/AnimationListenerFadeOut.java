package com.robapp.listener;

import android.view.View;
import android.view.animation.Animation;

import ch.qos.logback.classic.util.ContextInitializer;

/**
 * Created by Arthur on 16/10/2016.
 */

public class AnimationListenerFadeOut  implements Animation.AnimationListener{

    private View view;

    public AnimationListenerFadeOut(View view){
        this.view = view;
    }

    @Override
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        view.setVisibility(View.GONE);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }
}
