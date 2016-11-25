package com.robapp.app.task;

import com.robapp.app.dialog.Launcher;
import com.robapp.behaviors.interfaces.BehaviorItemI;

/**
 * Created by Arthur on 22/11/2016.
 */

public class AsyncTask extends android.os.AsyncTask<BehaviorItemI, Integer, Boolean> {

    private Launcher launcher;

    public AsyncTask(Launcher launcher)
    {
        super();
        this.launcher = launcher;
    }

    @Override
    protected Boolean doInBackground(BehaviorItemI... params) {
        params[0].run();
        return true;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

}
