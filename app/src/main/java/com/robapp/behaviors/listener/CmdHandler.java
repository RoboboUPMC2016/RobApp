package com.robapp.behaviors.listener;

import com.robapp.behaviors.exceptions.StopBehaviorException;
import com.robapp.behaviors.interfaces.CmdHandlerI;

/**
 * The implementation aof the CMDHandlerI interface
 * Created by Arthur on 12/12/2016.
 */

public class CmdHandler implements CmdHandlerI {

    private boolean waiting;
    private boolean arrived;

    public CmdHandler()
    {
        this.waiting = false;
        this.arrived = false;
    }

    @Override
    public void waitEndCmd() {
        synchronized (this)
        {

            try {
                if(!arrived)
                {
                    this.wait();
                }
                arrived = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new StopBehaviorException("Behaviour Interrupted");
            }
        }
    }

    @Override
    public void notifyEndCmd() {
        synchronized (this)
        {
            this.arrived = true;
            System.out.println("notify end cmd");
            this.notify();
        }
    }

    @Override
    public boolean IsWaiting() {
        return waiting;
    }

    /**
     * Set the flag which indicate  if a thrad is waiting
     * @param waiting
     */
    public void setWaiting(boolean waiting)
    {
        this.waiting = waiting;
    }
}
