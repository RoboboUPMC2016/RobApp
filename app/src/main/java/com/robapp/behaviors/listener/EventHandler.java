package com.robapp.behaviors.listener;

import com.robapp.behaviors.executions.ContextManager;
import com.robapp.behaviors.interfaces.EventListenerI;

import robdev.Event;

/**
 * The implementation of the EventListener, this implementation define a runnable to execute when
 * the event will occur
 * Created by Arthur on 09/12/2016.
 */

public class EventHandler implements EventListenerI {

    private final Runnable runnable;
    private final Event event;

    /**
     *
     * COnstructor
     * @param runnable The runnable to execute when the event occur
     * @param event The evnet waited
     */
    public EventHandler(Runnable runnable, Event event) {
        this.runnable = runnable;
        this.event = event;
    }

    @Override
    public boolean notifyEvent(Event event) {

        if(event.equals(this.event))
        {
            ContextManager.executeInNewContext(runnable);
            return true;
        }
        return false;
    }
}
