package com.robapp.behaviors.interfaces;

import com.mytechia.robobo.framework.misc.shock.ShockCategory;
import com.mytechia.robobo.rob.IRSensorStatus;

import java.util.Collection;

/**
 * Created by Arthur on 03/12/2016.
 */

public interface EventListenerI {

    public void subscribe(EventHandlerI handler);

    public void unsubscribe(EventHandlerI handler);

    public void setIRDistanceThreshold(double threshold);

    public void onShockEvent(ShockCategory shock);

    public void onIREvent(Collection<IRSensorStatus> collection);

}
