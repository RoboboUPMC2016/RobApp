package com.robapp.behaviors.interfaces;

import robdev.Events;

/**
 * Created by Arthur on 03/12/2016.
 */

public interface EventHandlerI {

    public void handleEvent(Events e);

    public boolean isWaitingEvent(Events e);

     void waitEvent(Events e);
}
