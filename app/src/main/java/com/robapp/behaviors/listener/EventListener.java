package com.robapp.behaviors.listener;

import com.mytechia.robobo.framework.misc.shock.ShockCategory;
import com.mytechia.robobo.rob.IRSensorStatus;
import com.robapp.behaviors.interfaces.EventHandlerI;
import com.robapp.behaviors.interfaces.EventListenerI;

import java.util.ArrayList;
import java.util.Collection;

import robdev.Events;

/**
 * Created by Arthur on 03/12/2016.
 */

public class EventListener implements EventListenerI {

    private ArrayList<EventHandlerI> handlers;
    private double IRThreshold;

    public EventListener() {
        handlers = new ArrayList<EventHandlerI>();
        IRThreshold = 100;
    }

    public void subscribe(EventHandlerI handler) {
        synchronized (handlers)
        {
            handlers.add(handler);
        }
    }

    public void unsubscribe(EventHandlerI handler) {
        synchronized (handlers)
        {
            handlers.remove(handler);
        }
    }

    @Override
    public void setIRDistanceThreshold(double threshold) {
        IRThreshold = 1;
    }

    @Override
    public void onShockEvent(ShockCategory shock) {
        synchronized (handlers)
        {
            for(EventHandlerI handler : handlers)
            {
                if(handler.isWaitingEvent(Events.SHOCK_DETECTED))
                    handler.handleEvent(Events.SHOCK_DETECTED);
            }
        }

    }

    @Override
    public void onIREvent(Collection<IRSensorStatus> collection) {
        synchronized (handlers)
        {
            for(EventHandlerI handler : handlers)
            {
                boolean check = false;
                    for(IRSensorStatus IR : collection)
                    {
                        switch(IR.getId())
                        {
                            case IRSensorStatus1:
                            case IRSensorStatus2:
                            case IRSensorStatus3:
                            case IRSensorStatus4:
                            case IRSensorStatus5:
                                if(handler.isWaitingEvent(Events.IRFRONT))
                                    if(IR.getDistance() > IRThreshold)
                                    {
                                        handler.handleEvent(Events.IRFRONT);
                                        check = true;
                                    }
                                break;
                            case IRSensorStatus6:
                            case IRSensorStatus7:
                            case IRSensorStatus8:
                            case IRSensorStatus9:
                                if(handler.isWaitingEvent(Events.IRBACK))
                                    if (IR.getDistance() > IRThreshold)
                                    {
                                        System.out.println("Distance : "+IR.getDistance());
                                        handler.handleEvent(Events.IRBACK);
                                        check = true;
                                    }
                                break;
                        }
                        if(check)
                            break;
                    }
            }
        }

    }
}
