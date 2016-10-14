package com.robapp.actions;

import com.mytechia.commons.framework.exception.InternalErrorException;
import com.mytechia.robobo.rob.movement.IRobMovementModule;

import robdev.Actions;


public class Acts implements Actions  {

    IRobMovementModule modMove;

    public Acts (IRobMovementModule mMove){
        modMove = mMove;
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
            modMove.moveForwardsTime(new Short("50"),i*1000);
        } catch (InternalErrorException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void moveBackward(int i) {
        try {
            modMove.moveBackwardsTime(new Short("50"),i*1000);
        } catch (InternalErrorException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void turnRight() {
        try {
            modMove.turnRightAngle(new Short("50"),90);
        } catch (InternalErrorException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void turnLeft() {
        try {
            modMove.turnLeftAngle(new Short("50"), 90);
        } catch (InternalErrorException e) {
            e.printStackTrace();
        }
    }
}
