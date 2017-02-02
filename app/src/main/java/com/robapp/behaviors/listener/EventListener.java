package com.robapp.behaviors.listener;

import com.robapp.behaviors.interfaces.EventListenerI;

import robdev.Event;

/**
 * The implementation of the interface EventListenerI, It will juste wait the event before
 * starting back the execution
 * Created by Arthur on 09/12/2016.
 */

public class EventListener implements EventListenerI {

    private Object object;
    private Event event;
    private boolean arrived;

    /**
     * Constructor
     * @param object The object where the thread wait
     * @param event The event waited
     */
    public EventListener(Object object, Event event) {
        this.event = event;
        this.object = object;
        this.arrived = false;
    }

    @Override
    public boolean notifyEvent(Event event) {
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

    /**
     * A flad to say if an event is already arrived
     * @return True is the event is laread arrive false otherwise
     */
    public boolean isArrived()
    {
        return arrived;
    }
}
