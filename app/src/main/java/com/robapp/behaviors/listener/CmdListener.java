package com.robapp.behaviors.listener;

import com.mytechia.commons.framework.exception.InternalErrorException;
import com.mytechia.robobo.rob.BatteryStatus;
import com.mytechia.robobo.rob.FallStatus;
import com.mytechia.robobo.rob.GapStatus;
import com.mytechia.robobo.rob.IRSensorStatus;
import com.mytechia.robobo.rob.IRobStatusListener;
import com.mytechia.robobo.rob.MotorStatus;
import com.mytechia.robobo.rob.WallConnectionStatus;
import com.robapp.behaviors.actions.Acts;
import com.robapp.behaviors.interfaces.CmdHandlerI;
import com.robapp.utils.Utils;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Created by Arthur on 03/12/2016.
 */

public class CmdListener implements IRobStatusListener {

    int movementEnd;
    int movementEnd1;

    private ArrayList<CmdHandlerI> handlers;

    public CmdListener()
    {
        handlers = new ArrayList<CmdHandlerI>();
        movementEnd =0;
        movementEnd =0;
    }

    public void waitListener()
    {
        handlers = new ArrayList<CmdHandlerI>();
        movementEnd = 0;
        movementEnd1 = 0;
    }

    public void subscribe(CmdHandlerI handler)
    {
        synchronized (handlers){
            handlers.add(handler);
        }

    }

    public void unsubscribe(CmdHandlerI handler)
    {
        synchronized (handlers){
            handlers.remove(handler);
        }

    }

    @Override
    public void statusMotorsMT(MotorStatus motorStatus, MotorStatus motorStatus1) {

        CmdHandlerI handler = null;
        synchronized (handlers){
            if(handlers.size() > 0)
                handler = handlers.get(handlers.size()-1);
        }

        if(handler == null)
            return;

        if (motorStatus.getAngularVelocity() == 0 && motorStatus1.getAngularVelocity() == 0
                && (movementEnd1 != 0 || movementEnd != 0)) {
            if(handler.IsWaiting())
                handler.handleEndCmd();
        }

        movementEnd = motorStatus.getAngularVelocity();
        movementEnd1 = motorStatus1.getAngularVelocity();
    }

    @Override
    public void statusMotorPan(MotorStatus motorStatus) {

    }

    @Override
    public void statusMotorTilt(MotorStatus motorStatus) {

    }

    @Override
    public void statusGaps(Collection<GapStatus> collection) {

    }

    @Override
    public void statusFalls(Collection<FallStatus> collection) {

    }

    @Override
    public void statusIRSensorStatus(Collection<IRSensorStatus> collection) {
    }

    @Override
    public void statusBattery(BatteryStatus batteryStatus) {

    }

    @Override
    public void statusWallConnectionStatus(WallConnectionStatus wallConnectionStatus) {

    }

    @Override
    public void robCommunicationError(InternalErrorException e) {

    }
}
