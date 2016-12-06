package com.robapp.behaviors.actions;

import com.mytechia.commons.framework.exception.InternalErrorException;
import com.mytechia.robobo.framework.exception.ModuleNotFoundException;
import com.mytechia.robobo.framework.hri.emotion.Emotion;
import com.mytechia.robobo.framework.hri.emotion.IEmotionListener;
import com.mytechia.robobo.framework.hri.emotion.IEmotionModule;
import com.mytechia.robobo.framework.hri.emotion.webgl.WebGLEmotionDisplayActivity;
import com.mytechia.robobo.framework.hri.speech.production.ISpeechProductionModule;
import com.mytechia.robobo.framework.hri.speech.production.android.AndroidSpeechProductionModule;
import com.mytechia.robobo.rob.IRob;
import com.mytechia.robobo.rob.IRobInterfaceModule;
import com.mytechia.robobo.rob.movement.IRobMovementModule;
import com.robapp.behaviors.exceptions.StopBehaviorException;
import com.robapp.behaviors.interfaces.CmdHandlerI;
import com.robapp.behaviors.interfaces.EventHandlerI;
import com.robapp.utils.Utils;

import java.util.ArrayList;

import robdev.Actions;
import robdev.Events;


public class Acts implements Actions,EventHandlerI,CmdHandlerI {

    final private  static  Short velocity = new Short("70") ;
    IRobMovementModule moveModule;
    IRobInterfaceModule robModule;
    IEmotionModule emotionModule;
    ISpeechProductionModule speechModule;
    IRob rob;
    boolean waiting;

    ArrayList<Events> eventsAwaited;

    public Acts (IRobMovementModule mMove) throws ModuleNotFoundException {
        moveModule = mMove;
        this.emotionModule = Utils.getRoboboManager().getModuleInstance(IEmotionModule.class);
        this.robModule = Utils.getRoboboManager().getModuleInstance(IRobInterfaceModule.class);
        this.speechModule = Utils.getRoboboManager().getModuleInstance(ISpeechProductionModule.class);
        this.rob = this.robModule.getRobInterface();
        this.eventsAwaited = new ArrayList<Events>();
        this.waiting = false;


        try {
            this.rob.setRobStatusPeriod(100);
        } catch (InternalErrorException e) {
            e.printStackTrace();
        }
    }

    private Emotion mappingEmotions(){
        return Emotion.NORMAL;
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

    @Override
    public void wait(Events events) {

        synchronized (this) {
            switch(events) {
                case SHOCK_DETECTED:
                    waitEvent(events);
                    break;
                case IRFRONT:
                    waitEvent(events);
                    break;
                case IRBACK:
                    waitEvent(events);
                    break;
                default:
                    System.out.println("Error : Event not handled");
            }
        }
    }

    @Override
    public void moveForward(int i) {
       try
        {
            moveModule.moveForwardsTime(velocity,i*1000);
            waitEndCmd();
        } catch (InternalErrorException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void moveForward(Events events) {

        try {
            addWaitedEvent(events);
            while(isWaitingEvent(events))
            {
                waiting = true;
                moveModule.moveForwardsTime(velocity,Integer.MAX_VALUE);
                waitEndCmd();
                waiting = false;
            }
            moveModule.stop();

        } catch (InternalErrorException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void moveBackward(int i) {
        try {
            waiting = true;
            moveModule.moveBackwardsTime(velocity, i * 1000);
            waitEndCmd();
            waiting = false;
        } catch (InternalErrorException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void moveBackward(Events events) {

        try{
            addWaitedEvent(events);
            while(isWaitingEvent(events))
            {
                waiting =true;
                moveModule.moveBackwardsTime(velocity,Integer.MAX_VALUE);
                waitEndCmd();
                waiting =false;
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

                waiting = true;
                moveModule.turnRightAngle(new Short("40"), (int) (90 * 4.89));
                waitEndCmd();
                waiting = false;

        } catch (InternalErrorException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void turnLeft() {
        try {

                waiting = true;
                moveModule.turnLeftAngle(velocity, (int) (90 * 4.89));
                waitEndCmd();
                waiting = false;

        }catch (InternalErrorException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        try {
            synchronized (this) {
                moveModule.stop();
            }
        } catch (InternalErrorException e) {
            e.printStackTrace();
        }
    }

    public void speak(String text){
        speechModule.sayText(text,ISpeechProductionModule.PRIORITY_HIGH);
    }

    public void selectVoice(String name)
    {
        try {
            speechModule.selectVoice(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleEvent(Events e) {
        synchronized (this) {
            switch (e) {
                case SHOCK_DETECTED:
                    System.out.println("Shock");
                    removeWaitEvent(e);
                    this.notify();
                    break;
                case IRFRONT:
                    System.out.println("IRFRont");
                    removeWaitEvent(e);
                    this.notify();
                    break;
                case IRBACK:
                    System.out.println("IRBack");
                    removeWaitEvent(e);
                    this.notify();
                    break;
                default:
                    break;
            }
        }
    }

    private void addWaitedEvent(Events e) {
        eventsAwaited.add(e);
    }

    private void removeWaitEvent(Events e) {
        eventsAwaited.remove(e);
    }

    @Override
    public boolean isWaitingEvent(Events e) {
        return eventsAwaited.contains(e);
    }

    @Override
    public void waitEvent(Events ev) {
        synchronized (this) {
            try{
                addWaitedEvent(ev);
                while(isWaitingEvent(ev))
                    this.wait();
                removeWaitEvent(ev);
            }catch(InterruptedException e)
            {
                removeWaitEvent(ev);
                e.printStackTrace();
                throw new StopBehaviorException("Interrupt wait event");
            }
        }
    }

    @Override
    public void waitEndCmd() {

        synchronized (this)
        {
            try{
                this.waiting = true;
                this.wait();
                this.waiting = false;
            }
            catch(InterruptedException e){
                this.waiting = false;
                if(Thread.currentThread().isInterrupted())
                    throw new StopBehaviorException("wait instruction");
            }
        }
    }

    @Override
    public void handleEndCmd() {
        synchronized (this)
        {
            this.notify();
        }
    }

    @Override
    public boolean IsWaiting() {
        return waiting;
    }

    public void setEmotion(Emotion e)
    {
        this.emotionModule.setCurrentEmotion(e);
    }
}