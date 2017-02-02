package com.robapp.behaviors.listener;

import com.mytechia.commons.framework.exception.InternalErrorException;
import com.mytechia.robobo.rob.BatteryStatus;
import com.mytechia.robobo.rob.FallStatus;
import com.mytechia.robobo.rob.GapStatus;
import com.mytechia.robobo.rob.IRSensorStatus;
import com.mytechia.robobo.rob.IRobStatusListener;
import com.mytechia.robobo.rob.MotorStatus;
import com.mytechia.robobo.rob.WallConnectionStatus;
import com.robapp.behaviors.executions.ContextManager;

import java.util.Collection;

import robdev.Event;


/**
 * An implementation of the IRobStatusListener
 * Used for beeing noify when a command end and
 * when the IR sensor has detected something
 *
 * Created by Arthur on 03/12/2016.
 */

public class StatusListener implements IRobStatusListener {

    int movementEnd;
    int movementEnd1;
    private  final int IRTHRESHOLD = 100;

    /**
     * Constructor
     */
    public StatusListener()
    {
        movementEnd =0;
        movementEnd =0;
    }

    @Override
    public void statusMotorsMT(MotorStatus motorStatus, MotorStatus motorStatus1) {

        //If the motors stop notify it
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
                            //IR sensor 1 - 5 : Front Sensor
                            //Notify if something is detected
                                if(IR.getDistance() > IRTHRESHOLD)
                                    ContextManager.dispatcheEvent(Event.IRFRONT);
                            break;
                        case IRSensorStatus6:
                        case IRSensorStatus7:
                        case IRSensorStatus8:
                        case IRSensorStatus9:
                            //IR sensor 6 - 9 : Back Sensor
                            //Notify if something is detected
                            //The robort we used has some problems with back IR
                            // it's why the IRTHRESOL it's doubled here
                            //Maybe add another IRTHRESOLD should be better
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
    }

}
