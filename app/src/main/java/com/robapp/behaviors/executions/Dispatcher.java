package com.robapp.behaviors.executions;

import com.robapp.behaviors.interfaces.EventHandlerI;

import java.util.LinkedList;

import robdev.Event;

/**
 * Created by Arthur on 09/12/2016.
 */

public class Dispatcher {

    private LinkedList<EventHandlerI> handlers;

    public Dispatcher() {
        handlers = new LinkedList<EventHandlerI>();
    }

    public void addEventHandler(EventHandlerI handler) {
        synchronized (handlers)
        {
            handlers.addFirst(handler);
        }
    }

    public void removeEventHandler(EventHandlerI handler) {
        synchronized (handlers)
        {
            handlers.remove(handler);
        }
    }

    public void dispatchEvent(Event e)
    {
        for(EventHandlerI handler : handlers)
        {
            if(handler.handleEvent(e))
                  return;
        }
    }
}
