package com.robapp.behaviors.executions;

import com.robapp.behaviors.listener.CmdHandler;
import com.robapp.behaviors.interfaces.EventHandlerI;

import robdev.Event;

/**
 * Created by Arthur on 09/12/2016.
 */

public class ContextManager {

    private static final ExecutionContext context = new ExecutionContext();


    public static void executeInNewContext(Runnable run)
    {
        context.createChildContext();
        run.run();
        context.removeChildContext();
        notifyEndCommand();
    }

    public static void addEventHandler(EventHandlerI handler)
    {
            context.addEventHandler(handler);
    }

    public static CmdHandler getCMDHandler()
    {
        return context.getCMDHandler();
    }

    public static void dispatcheEvent(Event e)
    {
        context.notifyEvent(e);
    }

    public static void notifyEndCommand()
    {
        context.notifyEndCommand();
    }
}
