package com.robapp.behaviors.actions;

import com.mytechia.commons.framework.exception.InternalErrorException;
import com.mytechia.robobo.framework.exception.ModuleNotFoundException;
import com.mytechia.robobo.framework.hri.emotion.IEmotionModule;
import com.mytechia.robobo.framework.hri.speech.production.ISpeechProductionModule;
import com.mytechia.robobo.rob.IRob;
import com.mytechia.robobo.rob.IRobInterfaceModule;
import com.mytechia.robobo.rob.movement.IRobMovementModule;
import com.robapp.app.activity.BehaviorActivity;
import com.robapp.behaviors.executions.BehaviorThread;
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
            checkHandler();
            synchronized (this) {
                try {
                    Thread.sleep(i * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    throw new StopBehaviorException("Stop wait(" + i + ")");
                }
            }
    }

    public void wait(Event event) {

        checkHandler();
        ContextManager.lockAction();
        WaitEventHandler handler = new WaitEventHandler(this,event);
        ContextManager.addEventHandler(handler);

        try{
            ContextManager.unlockAction();
            this.wait();
        }
        catch(Exception e){
            e.printStackTrace();
            throw new StopBehaviorException("Stop wait(" + event + ")");
        }


        ContextManager.removeEventHandler(handler);


    }

    @Override
    public void moveForward(int i) {
        checkHandler();
        ContextManager.lockAction();
       try
        {
            moveModule.moveForwardsTime(VELOCITY,i*1000);
            ContextManager.unlockAction();
            waitCommandEnd();
        } catch (InternalErrorException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void moveForward(Event event) {

        checkHandler();
        ContextManager.lockAction();
        try {
            WaitEventHandler handler = new WaitEventHandler(ContextManager.getCMDHandler(),event);
            ContextManager.addEventHandler(handler);
            while(!handler.isArrived())
            {
                moveModule.moveForwardsTime(VELOCITY,Integer.MAX_VALUE);
                ContextManager.unlockAction();
                waitCommandEnd();
                ContextManager.lockAction();
            }
            ContextManager.removeEventHandler(handler);
            moveModule.stop();
            ContextManager.unlockAction();


        } catch (InternalErrorException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void moveBackward(int i) {
        checkHandler();
        ContextManager.lockAction();
        try {
            moveModule.moveBackwardsTime(VELOCITY, i * 1000);
            ContextManager.unlockAction();
            waitCommandEnd();
            ContextManager.lockAction();
        } catch (InternalErrorException e) {
            e.printStackTrace();
        }
        ContextManager.unlockAction();
    }

    @Override
    public void moveBackward(Event event) {
        checkHandler();
        ContextManager.lockAction();
        try{
            WaitEventHandler handler = new WaitEventHandler(ContextManager.getCMDHandler(),event);
            ContextManager.addEventHandler(handler);
            while(!handler.isArrived())
            {
                moveModule.moveBackwardsTime(VELOCITY,Integer.MAX_VALUE);
                ContextManager.unlockAction();
                waitCommandEnd();
                ContextManager.lockAction();
            }
            ContextManager.removeEventHandler(handler);
            moveModule.stop();
            ContextManager.unlockAction();
        }
        catch(InternalErrorException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void turnRight() {
        checkHandler();
        ContextManager.lockAction();
        try {
                moveModule.turnRightAngle(VELOCITY, (int) (90 * 4.89));
            ContextManager.unlockAction();
                waitCommandEnd();
        } catch (InternalErrorException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void turnLeft() {
        checkHandler();
        ContextManager.lockAction();
        try {
                moveModule.turnLeftAngle(VELOCITY, (int) (90 * 4.89));
            ContextManager.unlockAction();
                waitCommandEnd();
        } catch (InternalErrorException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        checkHandler();
        ContextManager.lockAction();
        try {
                moveModule.stop();
            ContextManager.unlockAction();
        } catch (InternalErrorException e) {e.printStackTrace();
            e.printStackTrace();
        }
    }

    @Override
    public void setEmotion(Emotion emotion) {
        checkHandler();
        ContextManager.lockAction();
        emotionModule.setCurrentEmotion(mappingEmotions(emotion));
        ContextManager.unlockAction();
        Thread.yield();
    }

    @Override
    public void speak(String text){
        checkHandler();
        ContextManager.lockAction();
        speechModule.sayText(text,ISpeechProductionModule.PRIORITY_HIGH);
        ContextManager.unlockAction();
    }

    @Override
    public void when(Event event, Runnable runnable) {
        ContextManager.addEventHandler(new WhenEventHandler(runnable,event));
    }

    public void selectVoice(String name)
    {
        checkHandler();
        ContextManager.lockAction();
        try {
            speechModule.selectVoice(name);
            ContextManager.unlockAction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void waitCommandEnd() {


        ContextManager.getCMDHandler().setWaiting(true);
        ContextManager.getCMDHandler().waitEndCmd();
        ContextManager.getCMDHandler().setWaiting(false);
    }

    public void checkHandler()
    {
        ContextManager.checkHandlerIsRunning();
    }

}