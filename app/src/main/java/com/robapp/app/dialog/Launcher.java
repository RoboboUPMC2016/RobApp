package com.robapp.app.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.View;

import com.robapp.app.activity.BehaviorActivity;
import com.robapp.behaviors.interfaces.BehaviorItemI;
/**
 * Created by Arthur on 19/10/2016.
 */

public class Launcher extends ProgressDialog {

    private int timed;
    private Handler handler;
    private Runnable run;
    private BehaviorItemI behavior;
    private BehaviorActivity act;
    boolean started = false;
    Thread t;

    public Launcher(Context context, int timed, BehaviorItemI behavior) {
        super(context);
        this.timed = timed;
        this.behavior = behavior;
        this.act = (BehaviorActivity)context;
        this.setTitle(" Lancement ");
        started = false;
        this.setButton(BUTTON_POSITIVE,"Lancer",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                started = true;
                dismiss();

            }
        });
        t = new Thread(behavior);
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
                    dismiss();
                else
                    handler.postDelayed(this,1000);
            }
        };

        this.getButton(BUTTON_POSITIVE).setVisibility(View.VISIBLE);
        handler.postDelayed(run,1000);
    }

    public void update()
    {
        this.setMessage("Lancement dans "+timed+" s");
    }

    @Override
    public void onStop()
    {
        synchronized (this)
        {
            handler.removeCallbacks(run);
            this.notify();
        }
    }

    public boolean launchConfirmed()
    {
        return started;
    }

    public Thread getThread()
    {
        if(started)
            return t;
        return null;
    }
}
