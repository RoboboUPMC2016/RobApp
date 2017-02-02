package com.robapp.behaviors.executions;

import com.robapp.behaviors.interfaces.EventListenerI;

import java.util.LinkedList;

import robdev.Event;

/**
 * The class which used for dispatching event to handler
 * Created by Arthur on 09/12/2016.
 */

public class Dispatcher {

    private LinkedList<EventListenerI> listeners;


    public Dispatcher() {
        listeners = new LinkedList<EventListenerI>();
    }

    /**
     * Add an EventListener
     * @param listener The EventListener to add
     */
    public void addEventListener(EventListenerI listener) {
        synchronized (listeners)
        {
            listeners.addFirst(listener);
        }
    }

    /**
     * Remove an EventListener
     * @param listener The EventListener to remove
     */
    public void removeEventListener(EventListenerI listener) {
        synchronized (listeners)
        {
            listeners.remove(listener);
        }
    }

    /**
     * Dispatch an event to listener
     * @param e The Event to dispatch
     */
    public void dispatchEvent(Event e)
    {
        synchronized (listeners)
        {
            for(EventListenerI handler : listeners)
            {
                if(handler.notifyEvent(e))
                    return;
            }
        }

    }
}
