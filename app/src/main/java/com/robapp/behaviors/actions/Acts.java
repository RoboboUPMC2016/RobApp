package com.robapp.behaviors.actions;

import com.mytechia.commons.framework.exception.InternalErrorException;
import com.mytechia.robobo.framework.exception.ModuleNotFoundException;
import com.mytechia.robobo.framework.hri.emotion.IEmotionModule;
import com.mytechia.robobo.framework.hri.speech.production.ISpeechProductionModule;
import com.mytechia.robobo.rob.IRob;
import com.mytechia.robobo.rob.IRobInterfaceModule;
import com.mytechia.robobo.rob.movement.IRobMovementModule;
import com.robapp.behaviors.executions.ContextManager;
import com.robapp.behaviors.exceptions.StopBehaviorException;
import com.robapp.behaviors.listener.WaitEventHandler;
import com.robapp.behaviors.listener.WhenEventHandler;
import com.robapp.utils.Utils;


import robdev.Actions;
import robdev.Emotion;
import robdev.Event;


public class Acts implements Actions{

    private final static Short VELOCITY = new Short("70") ;
    private IRobMovementModule moveModule;
    private IRobInterfaceModule robModule;
    private IEmotionModule emotionModule;
    private ISpeechProductionModule speechModule;
    private IRob rob;

    public Acts (IRobMovementModule mMove) throws ModuleNotFoundException {
        moveModule = mMove;
        this.emotionModule = Utils.getRoboboManager().getModuleInstance(IEmotionModule.class);
        this.robModule = Utils.getRoboboManager().getModuleInstance(IRobInterfaceModule.class);
        this.speechModule = Utils.getRoboboManager().getModuleInstance(ISpeechProductionModule.class);
        this.rob = this.robModule.getRobInterface();

        try {
            this.rob.setRobStatusPeriod(100);
        } catch (InternalErrorException e) {
            e.printStackTrace();
        }
    }

    private com.mytechia.robobo.framework.hri.emotion.Emotion mappingEmotions(Emotion emotion){
        switch(emotion)
        {
            case Normal:
                return com.mytechia.robobo.framework.hri.emotion.Emotion.NORMAL;
            case Happy:
                return com.mytechia.robobo.framework.hri.emotion.Emotion.HAPPY;
            case Angry:
                return com.mytechia.robobo.framework.hri.emotion.Emotion.ANGRY;
            case Laughing:
                return  com.mytechia.robobo.framework.hri.emotion.Emotion.LAUGHING;
            case Smyling:
                return com.mytechia.robobo.framework.hri.emotion.Emotion.SMYLING;
            case Surprised:
                return com.mytechia.robobo.framework.hri.emotion.Emotion.SURPRISED;
            case Sad:
                return com.mytechia.robobo.framework.hri.emotion.Emotion.SAD;
            case In_Love:
                return  com.mytechia.robobo.framework.hri.emotion.Emotion.IN_LOVE;
            case Embarrased:
                return com.mytechia.robobo.framework.hri.emotion.Emotion.EMBARRASED;
            default:
                return com.mytechia.robobo.framework.hri.emotion.Emotion.NORMAL;
        }

    }

    @Override
    public void wait(int i) {

        synchronized (this)
        {
            try {
                Thread.sleep(i*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new StopBehaviorException("Stop wait("+i+")");
            }
        }

    }

    public void wait(Event event) {

        ContextManager.addEventHandler(new WaitEventHandler(this,event));
        synchronized (this)
        {
            try{
                this.wait();
            }
            catch(Exception e){
                e.printStackTrace();
            }

        }


    }

    @Override
    public void moveForward(int i) {
       try
        {
            moveModule.moveForwardsTime(VELOCITY,i*1000);
            waitCommandEnd();
        } catch (InternalErrorException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void moveForward(Event event) {

        try {
            WaitEventHandler handler = new WaitEventHandler(ContextManager.getCMDHandler(),event);
            ContextManager.addEventHandler(handler);
            while(!handler.isArrived())
            {
                moveModule.moveForwardsTime(VELOCITY,Integer.MAX_VALUE);
                waitCommandEnd();
            }
            moveModule.stop();
        } catch (InternalErrorException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void moveBackward(int i) {
        try {
            moveModule.moveBackwardsTime(VELOCITY, i * 1000);
            waitCommandEnd();
        } catch (InternalErrorException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void moveBackward(Event event) {
        try{
            WaitEventHandler handler = new WaitEventHandler(ContextManager.getCMDHandler(),event);
            ContextManager.addEventHandler(handler);
            while(!handler.isArrived())
            {
                moveModule.moveBackwardsTime(VELOCITY,Integer.MAX_VALUE);
                waitCommandEnd();
            }
            moveModule.stop();
        }
        catch(InternalErrorException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void turnRight() {
        try {
                moveModule.turnRightAngle(VELOCITY, (int) (90 * 4.89));
                waitCommandEnd();
        } catch (InternalErrorException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void turnLeft() {
        try {
                moveModule.turnLeftAngle(VELOCITY, (int) (90 * 4.89));
                waitCommandEnd();
        }catch (InternalErrorException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        try {
                moveModule.stop();
        } catch (InternalErrorException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setEmotion(Emotion emotion) {

        emotionModule.setCurrentEmotion(mappingEmotions(emotion));
    }

    @Override
    public void speak(String text){
        speechModule.sayText(text,ISpeechProductionModule.PRIORITY_HIGH);
    }

    @Override
    public void when(Event event, Runnable runnable) {
        ContextManager.addEventHandler(new WhenEventHandler(runnable,event));
    }

    public void selectVoice(String name)
    {
        try {
            speechModule.selectVoice(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void waitCommandEnd() {

        ContextManager.getCMDHandler().setWaiting(true);
        ContextManager.getCMDHandler().waitEndCmd();
        ContextManager.getCMDHandler().setWaiting(false);
    }

}