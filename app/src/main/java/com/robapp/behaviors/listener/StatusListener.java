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
import com.robapp.behaviors.executions.ContextManager;
import com.robapp.behaviors.interfaces.CmdHandlerI;
import com.robapp.utils.Utils;

import java.util.ArrayList;
import java.util.Collection;

import robdev.Event;


/**
 * Created by Arthur on 03/12/2016.
 */

public class StatusListener implements IRobStatusListener {

    int movementEnd;
    int movementEnd1;
    private  final int IRTHRESHOLD = 100;

    public StatusListener()
    {
        movementEnd =0;
        movementEnd =0;
    }

    public void waitListener()
    {
        movementEnd = 0;
        movementEnd1 = 0;
    }

    @Override
    public void statusMotorsMT(MotorStatus motorStatus, MotorStatus motorStatus1) {

        if (motorStatus.getAngularVelocity() == 0 && motorStatus1.getAngularVelocity() == 0
                && (movementEnd1 != 0 || movementEnd != 0)) {
            ContextManager.notifyEndCommand();
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

                for(IRSensorStatus IR : collection)
                {
                    switch(IR.getId())
                    {
                        case IRSensorStatus1:
                        case IRSensorStatus2:
                        case IRSensorStatus3:
                        case IRSensorStatus4:
                        case IRSensorStatus5:
                                if(IR.getDistance() > IRTHRESHOLD)
                                    ContextManager.dispatcheEvent(Event.IRFRONT);
                            break;
                        case IRSensorStatus6:
                        case IRSensorStatus7:
                        case IRSensorStatus8:
                        case IRSensorStatus9:
                            if(IR.getDistance() > IRTHRESHOLD*2)
                                ContextManager.dispatcheEvent(Event.IRBACK);
                            break;
                    }
                }
    }

    @Override
    public void statusBattery(BatteryStatus batteryStatus) {

    }

    @Override
    public void statusWallConnectionStatus(WallConnectionStatus wallConnectionStatus) {

    }

    @Override
    public void robCommunicationError(InternalErrorException e) {
        System.out.println("Error Communication  ====> "+e.getMessage());
    }

}
