package com.robapp.behaviors.listener;

import robdev.Events;

/**
 * Created by Arthur on 01/12/2016.
 */

public class Flag {

    private boolean set;
    private boolean isWaited;
    private Events eventWaited;

    public Flag()
    {
        set = false;
        isWaited = false;
        eventWaited = null;
    }

    public void set(boolean isSet)
    {
        this.set = isSet;
    }

    public boolean isSet()
    {
        return set;
    }

    public void setIsWaited(boolean isWaited)
    {
        this.isWaited=isWaited;
    }

    public boolean isWaited()
    {
        return isWaited;
    }

    public Events getEventWaited() {
        return eventWaited;
    }

    public void setEventWaited(Events eventWaited) {
        this.eventWaited = eventWaited;
    }
}
