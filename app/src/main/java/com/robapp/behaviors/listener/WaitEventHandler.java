package com.robapp.behaviors.listener;

import com.robapp.behaviors.interfaces.EventHandlerI;

import robdev.Actions;
import robdev.Event;

/**
 * Created by Arthur on 09/12/2016.
 */

public class WaitEventHandler implements EventHandlerI {

    private Object object;
    private Event event;
    private boolean arrived;

    public WaitEventHandler(Object object,Event event) {
        this.event = event;
        this.object = object;
        this.arrived = false;
    }

    @Override
    public boolean handleEvent(Event event) {
        if(this.event.equals(event))
        {
            synchronized (object)
            {
                arrived = true;
                object.notify();
            }
            return true;
        }
        return false;
    }

    public boolean isArrived()
    {
        return arrived;
    }
}
