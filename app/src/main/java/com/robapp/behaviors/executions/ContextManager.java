package com.robapp.behaviors.executions;

import com.robapp.behaviors.listener.CmdHandler;
import com.robapp.behaviors.interfaces.EventHandlerI;

import robdev.Event;

/**
 * Created by Arthur on 09/12/2016.
 */

public class ContextManager {

    private static ExecutionContext context = new ExecutionContext();
    private static Boolean action = false;
    private static int cpt = 0;
    private static Runnable lastAction = null;


    public static void executeInNewContext(Runnable run)
    {

        System.out.println("Handler Started");
        context.createChildContext();
        run.run();
        context.removeChildContext();
        System.out.println("Handler Finished");
    }

    public static void addEventHandler(EventHandlerI handler)
    {
            context.addEventHandler(handler);
    }

    public static void removeEventHandler(EventHandlerI handler)
    {
        context.removeEventHandler(handler);
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

    public static void checkHandlerIsRunning()
    {
        context.waitEventHandlerEnd();
    }

    public static void initContext()
    {
        context = new ExecutionContext();
        context.initCurrentThread();
    }

    public static void lockAction()
    {
            synchronized (context)
            {
                while (action) {
                    try {
                        cpt++;
                        context.wait();
                        cpt--;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                action = true;
            }

    }

    public static void unlockAction()
    {
        synchronized (context) {
            action = false;
            try{
                if(cpt > 0)
                {
                    context.notifyAll();
                }
            }
            catch(Exception e)
            {

            }
        }

    }

    public static void saveLastAction(Runnable run)
    {
        lastAction = run;
    }

    public static void rerunLastAction()
    {
        lastAction.run();
    }

    public static void stopExecution()
    {
        context.stopExecution();
    }
}
