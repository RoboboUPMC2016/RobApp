package com.robapp.behaviors.interfaces;

import robdev.Event;

/**
 * This is the Interface of an EventListener
 * Created by Arthur on 03/12/2016.
 */

public interface EventListenerI {

    /**
     * Notify that an event occured
     * @param e The event which occured
     * @return True of the event handle the notification false otherwise
     */
    public boolean notifyEvent(Event e);
}
