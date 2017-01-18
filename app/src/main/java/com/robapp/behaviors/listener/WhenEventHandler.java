package com.robapp.behaviors.listener;

import com.robapp.behaviors.executions.ContextManager;
import com.robapp.behaviors.interfaces.EventHandlerI;

import robdev.Event;

/**
 * Created by Arthur on 09/12/2016.
 */

public class WhenEventHandler implements EventHandlerI {

    private final Runnable runnable;
    private final Event event;

    public WhenEventHandler(Runnable runnable, Event event) {
        this.runnable = runnable;
        this.event = event;
    }

    @Override
    public boolean handleEvent(Event event) {

        if(event.equals(this.event))
        {
            ContextManager.executeInNewContext(runnable);
            return true;
        }
        return false;
    }
}
