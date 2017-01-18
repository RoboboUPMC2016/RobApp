package com.robapp.behaviors.executions;

/**
 * Created by Arthur on 15/12/2016.
 */

public class BehaviorThread extends Thread{

    private boolean handlerRunning;

    public BehaviorThread(Runnable run)
    {
        super(run);
        handlerRunning = false;
    }

    public boolean isHandlerRunning()
    {
        return handlerRunning;
    }

    public void setHandlerRunning(boolean handlerRunning)
    {
        this.handlerRunning = handlerRunning;
    }
}
