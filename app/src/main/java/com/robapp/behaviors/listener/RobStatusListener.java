package com.robapp.behaviors.listener;

import com.mytechia.commons.framework.exception.InternalErrorException;
import com.mytechia.robobo.framework.RoboboManagerListener;
import com.mytechia.robobo.framework.RoboboManagerState;
import com.mytechia.robobo.rob.BatteryStatus;
import com.mytechia.robobo.rob.FallStatus;
import com.mytechia.robobo.rob.GapStatus;
import com.mytechia.robobo.rob.IRSensorStatus;
import com.mytechia.robobo.rob.IRobStatusListener;
import com.mytechia.robobo.rob.MotorStatus;
import com.mytechia.robobo.rob.WallConnectionStatus;

import java.util.Collection;

/**
 * Created by Arthur on 24/11/2016.
 */

public class RobStatusListener implements IRobStatusListener {


    @Override
    public void statusMotorsMT(MotorStatus motorStatus, MotorStatus motorStatus1) {

    }

    @Override
    public void statusMotorPan(MotorStatus motorStatus) {
        System.out.println("PAN : "+motorStatus.getAngularVelocity());
        System.out.println("PAN : "+motorStatus.getVariationAngle());
    }

    @Override
    public void statusMotorTilt(MotorStatus motorStatus) {
        System.out.println("TILT : "+motorStatus.getAngularVelocity());
        System.out.println("TILT : "+motorStatus.getVariationAngle());
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
