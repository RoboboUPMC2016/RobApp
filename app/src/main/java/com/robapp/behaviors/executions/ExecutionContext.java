package com.robapp.behaviors.executions;

import com.robapp.behaviors.listener.CmdHandler;
import com.robapp.behaviors.interfaces.EventHandlerI;
import com.robapp.utils.Utils;

import robdev.Event;

/**
 * Created by Arthur on 09/12/2016.
 */

public class ExecutionContext {

    private ExecutionContext child;
    private Dispatcher dispatcher;
    private CmdHandler cmdHandler;

    public ExecutionContext() {

        this.child = null;
        this.dispatcher = new Dispatcher();
        this.cmdHandler = new CmdHandler();
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
            child = new ExecutionContext();
        else
            child.createChildContext();
    }

    protected void removeChildContext()
    {
        if(child.child == null)
            child = null;
        else
            child.removeChildContext();
    }

    protected void addEventHandler(EventHandlerI handler)
    {
        if(child != null)
            child.addEventHandler(handler);
        else
            dispatcher.addEventHandler(handler);
    }


    protected CmdHandler getCMDHandler()
    {
        if(child == null)
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
}
