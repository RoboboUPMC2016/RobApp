package com.robapp.behaviors.executions;

import com.robapp.behaviors.listener.CmdHandler;
import com.robapp.behaviors.interfaces.EventHandlerI;
import com.robapp.behaviors.listener.HandlerListener;
import com.robapp.utils.Utils;

import robdev.Event;

/**
 * Created by Arthur on 09/12/2016.
 */

public class ExecutionContext {

    private ExecutionContext child;
    private Dispatcher dispatcher;
    private CmdHandler cmdHandler;
    private boolean handlerRunning;
    private HandlerListener handlerListener;
    private Thread currentThread;

    public ExecutionContext() {

        this.child = null;
        this.dispatcher = new Dispatcher();
        this.cmdHandler = new CmdHandler();
        this.handlerRunning = false;
        this.handlerListener = new HandlerListener();
        this.currentThread = null;
    }

    public void notifyEvent(Event e){
        if(child != null)
        {
            child.notifyEvent(e);
            return;
        }
        dispatcher.dispatchEvent(e);
    }

    protected void createChildContext()
    {
        if(child == null)
        {
            child = new ExecutionContext();
            child.setIsHandlerRunning(true);
        }
        else
            child.createChildContext();
    }

    protected void removeChildContext()
    {
        if(child.child == null)
        {
            ExecutionContext context = child;
            child = null;
            context.setIsHandlerRunning(false);
            context.handlerListener.notifyHandlerEnd();
            context.notifyEndCommand();
            notifyEndCommand();
        }
        else
            child.removeChildContext();
    }

    protected void addEventHandler(EventHandlerI handler)
    {
        if(currentThread == Thread.currentThread())
            dispatcher.addEventHandler(handler);
        else
            child.addEventHandler(handler);
    }

    protected void removeEventHandler(EventHandlerI handler)
    {
        if(currentThread == Thread.currentThread())
            dispatcher.removeEventHandler(handler);
        else
            child.removeEventHandler(handler);

    }


    protected CmdHandler getCMDHandler()
    {
        if(currentThread == Thread.currentThread())
            return cmdHandler;
        else
            return child.getCMDHandler();
    }

    public void  notifyEndCommand()
  {
        if(child != null)
            child.notifyEndCommand();
        else if(cmdHandler.IsWaiting())
            cmdHandler.handleEndCmd();
  }

    private boolean isHandlerRunning()
    {
        if(child != null)
            return child.isHandlerRunning();

        return handlerRunning;
    }

    public void waitEventHandlerEnd()
    {
        if(child != null)
            child.waitEventHandlerEnd();
        else
        {


            if(currentThread == Thread.currentThread())
            {
                return;
            }
            else
            {
                handlerListener.waitHandlerEnd();
            }
        }
    }

    protected void setIsHandlerRunning(boolean handlerRunning)
    {
        if(handlerRunning)
            currentThread = Thread.currentThread();
        else
            currentThread = null;

        this.handlerRunning = handlerRunning;
    }

    protected void initCurrentThread()
    {
        currentThread = Utils.getThread();
    }

    protected void stopExecution()
    {
        if(child != null)
            child.stopExecution();
        currentThread.interrupt();
        child = null;
    }
}
