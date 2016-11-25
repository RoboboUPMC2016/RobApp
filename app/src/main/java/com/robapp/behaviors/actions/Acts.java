package com.robapp.behaviors.actions;

import com.mytechia.commons.framework.exception.InternalErrorException;
import com.mytechia.robobo.framework.RoboboManager;
import com.mytechia.robobo.framework.exception.ModuleNotFoundException;
import com.mytechia.robobo.rob.BatteryStatus;
import com.mytechia.robobo.rob.FallStatus;
import com.mytechia.robobo.rob.GapStatus;
import com.mytechia.robobo.rob.IRSensorStatus;
import com.mytechia.robobo.rob.IRob;
import com.mytechia.robobo.rob.IRobInterfaceModule;
import com.mytechia.robobo.rob.IRobStatusListener;
import com.mytechia.robobo.rob.MotorStatus;
import com.mytechia.robobo.rob.WallConnectionStatus;
import com.mytechia.robobo.rob.movement.IRobMovementModule;
import com.robapp.behaviors.exceptions.StopBehaviorException;
import com.robapp.utils.Utils;

import java.util.Collection;

import robdev.Actions;


public class Acts implements Actions  {

    IRobMovementModule modMove;

    IRobInterfaceModule robModule;

    RoboboManager roboboManager;

    IRob rob;

    int movementEnd;
    int movementEnd1;


    public Acts (IRobMovementModule mMove) throws ModuleNotFoundException {
        modMove = mMove;
        movementEnd = 0;
        movementEnd1 = 0;

        roboboManager = Utils.getRoboboManager();
        //low-level platform control module
        this.robModule = this.roboboManager.getModuleInstance(IRobInterfaceModule.class);
        //get the instance of the ROB interface
        this.rob = this.robModule.getRobInterface();
        try {
            this.rob.setRobStatusPeriod(100);
        } catch (InternalErrorException e) {
            e.printStackTrace();
        }


        rob.addRobStatusListener(new IRobStatusListener() {
            @Override
            public void statusMotorsMT(MotorStatus motorStatus, MotorStatus motorStatus1) {
                if (motorStatus.getAngularVelocity() == 0 && motorStatus1.getAngularVelocity() == 0
                        && (movementEnd1 != 0 || movementEnd != 0)) {
                    synchronized (Utils.getRoboboManager()) {
                        Utils.getRoboboManager().notify();
                    }
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
        });
    }

    @Override
    public void pause() {
        try {
            modMove.stop();
        } catch (InternalErrorException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void moveForward(int i) {
        try {
            modMove.moveForwardsTime(new Short("70"),i*1000);
            synchronized (Utils.getRoboboManager()) {
                Utils.getRoboboManager().wait();
            }
        }
        catch(Exception e){
            e.printStackTrace();
            throw new StopBehaviorException("Stop Behavior");
        }
    }

    @Override
    public void moveBackward(int i) {
        try {
            modMove.moveBackwardsTime(new Short("70"),i*1000);
            synchronized (Utils.getRoboboManager()) {
                Utils.getRoboboManager().wait();
            }
        } catch(Exception e){
            e.printStackTrace();
            throw new StopBehaviorException("Stop Behavior");
        }

    }

    @Override
    public void turnRight() {
        try {
            modMove.turnRightAngle(new Short("40"), (int)(90*4.89));
            synchronized (Utils.getRoboboManager()) {
                Utils.getRoboboManager().wait();
            }
        } catch(Exception e){
            throw new StopBehaviorException("Stop Behavior");
        }
    }

    @Override
    public void turnLeft() {
        try {
            modMove.turnLeftAngle(new Short("40"), (int)(90*4.89));
            synchronized (Utils.getRoboboManager()) {
                Utils.getRoboboManager().wait();
            }
        }  catch(Exception e){
            e.printStackTrace();
            throw new StopBehaviorException("Stop Behavior");
        }
    }
}