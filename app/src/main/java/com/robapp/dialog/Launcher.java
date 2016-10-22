package com.robapp.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.View;

import com.robapp.activity.MainActivity;
import com.robapp.interfaces.BehaviorItemI;

/**
 * Created by Arthur on 19/10/2016.
 */

public class Launcher extends ProgressDialog {

    private int timed;
    private Handler handler;
    private Runnable run;
    private BehaviorItemI behavior;
    private MainActivity act;
    boolean started = false;
    Thread t;

    public Launcher(Context context, int timed, BehaviorItemI behavior) {
        super(context);
        this.timed = timed;
        this.behavior = behavior;
        this.act = (MainActivity)context;
        this.setTitle(" Lancement ");
        started = false;
        this.setButton(BUTTON_POSITIVE,"Lancer",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                if(!started)
                    launchBehavior();
                else
                    t.interrupt();
            }
        });
    }

    @Override
    public void onStart() {

        update();
        handler = new Handler();
        run = new Runnable() {
            @Override
            public void run() {
                timed--;
                update();
                if(timed == 0)
                {
                    launchBehavior();
                }
                else
                    handler.postDelayed(this,1000);
            }
        };

        this.getButton(BUTTON_POSITIVE).setVisibility(View.VISIBLE);
        handler.postDelayed(run,1000);
    }

    public void update()
    {
        String msg = "Lancement du comportement "+behavior.getName();

        if(timed == 0)
            this.setMessage(msg);
        else
            this.setMessage(msg+" dans "+timed+" s");
    }

    public void launchBehavior() {

        this.setMessage("Lancement du comportement : "+behavior.getName());

        try {

            handler.removeCallbacks(run);
            started =true;

            t = new Thread(behavior);
            handler.post(t);
            this.dismiss();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
