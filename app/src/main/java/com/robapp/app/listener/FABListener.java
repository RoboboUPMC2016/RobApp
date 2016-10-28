package com.robapp.app.listener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.animation.Animation;

import com.robapp.R;
import com.robapp.app.activity.FileExplorerActivity;

import static android.view.animation.AnimationUtils.*;

/**
 * Created by Arthur on 16/10/2016.
 */

public class FABListener implements View.OnClickListener {

    private Activity act;

    Animation onClick;

    FloatingActionButton fileExpButton;
    Animation fade_in_f;
    Animation fade_out_f;

    FloatingActionButton cloudButton;
    Animation fade_in_c;
    Animation fade_out_c;

    Color c;
    boolean expended;

    public FABListener(Activity act)
    {
        this.act=act;

        onClick = loadAnimation(act,R.anim.onclick);

        fileExpButton = (FloatingActionButton)act.findViewById(R.id.fileExpButton);
        fileExpButton.setVisibility(View.GONE);
        fileExpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchFileExplorer();
            }
        });

        cloudButton= (FloatingActionButton)act.findViewById(R.id.cloudButton);
        cloudButton.setVisibility(View.GONE);

        fade_in_f = loadAnimation(act, R.anim.fade_in);
        fade_out_f = loadAnimation(act, R.anim.fade_out);

        fade_in_f.setAnimationListener(
                new AnimationListenerFadeIn(fileExpButton));
        fade_out_f.setAnimationListener(
                new AnimationListenerFadeOut(fileExpButton));


        fade_in_c = loadAnimation(act, R.anim.fade_in);
        fade_out_c = loadAnimation(act, R.anim.fade_out);

        fade_in_c.setAnimationListener(
                new AnimationListenerFadeIn(cloudButton));
        fade_out_c.setAnimationListener(
                new AnimationListenerFadeOut(cloudButton));



        expended = false;
    }

    @Override
    public void onClick(View v) {

        v.startAnimation(onClick);
        if(expended)
        {
            fileExpButton.startAnimation(fade_out_f);
            cloudButton.startAnimation(fade_out_c);
            expended=false;
        }
        else
        {
            fileExpButton.startAnimation(fade_in_f);
            cloudButton.startAnimation(fade_in_c);
            expended=true;
        }

    }

    public void launchFileExplorer(){
        Intent intent = new Intent(act, FileExplorerActivity.class);
        act.startActivity(intent);
    }
}
