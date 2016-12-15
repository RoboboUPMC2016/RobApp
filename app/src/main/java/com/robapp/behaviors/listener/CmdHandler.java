package com.robapp.behaviors.listener;

import com.robapp.behaviors.exceptions.StopBehaviorException;
import com.robapp.behaviors.interfaces.CmdHandlerI;

/**
 * Created by Arthur on 12/12/2016.
 */

public class CmdHandler implements CmdHandlerI {

    private boolean waiting;

    public CmdHandler()
    {
        this.waiting = false;
    }

    @Override
    public void waitEndCmd() {
        synchronized (this)
        {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new StopBehaviorException("Behaviour Interrupted");
            }
        }
    }

    @Override
    public void handleEndCmd() {
        synchronized (this)
        {
            this.notify();
        }
    }

    @Override
    public boolean IsWaiting() {
        return waiting;
    }

    public void setWaiting(boolean waiting)
    {
        this.waiting = waiting;
    }
}
