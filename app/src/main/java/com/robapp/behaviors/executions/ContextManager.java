package com.robapp.behaviors.executions;

import android.os.Handler;

import com.robapp.behaviors.listener.CmdHandler;
import com.robapp.behaviors.interfaces.EventListenerI;

import robdev.Event;

/**
 * The context manager is used for dispatching events and managing execution context for behaviors and handlers
 * Created by Arthur on 09/12/2016.
 */

public class ContextManager {

    private static ExecutionContext context = new ExecutionContext();
    private static Boolean action = false;
    private static int cpt = 0;
    private static Runnable lastAction = null;


    /**
     *
     * Create a new context and execute the runnable, then remove the execution contexte
     * @param run The runnable to execute in fact this is a handler
     */
    public static void executeInNewContext(final Runnable run)
    {
     /*   Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {*/
                System.out.println("Handler Started");
                context.createChildContext();
                run.run();
                context.removeChildContext();
                System.out.println("Handler Finished");
           /* }
        });*/


    }

    /**
     * Add an event listener
     * @param listener The EventListener to add
     */
    public static void addEventListener(EventListenerI listener)
    {
            context.addEventListener(listener);
    }

    /**
     * Remove an EventHandler
     * @param handler
     */
    public static void removeEventHandler(EventListenerI handler)
    {
        context.removeEventListener(handler);
    }

    /**
     * Get the CMDHandler which used for waiting the command end
     * @return
     */
    public static CmdHandler getCMDHandler()
    {
        return context.getCMDHandler();
    }

    /**
     *
     * Dispatch an event to sxecution contexts
     * @param e The event to dispatch
     */
    public static void dispatcheEvent(Event e)
    {
        context.notifyEvent(e);
    }

    /**
     * Notify the command end to ExecutionContexts
     */
    public static void notifyEndCommand()
    {
        context.notifyEndCommand();
    }

    /**
     * Check if a handler is running
     */
    public static void checkHandlerIsRunning()
    {
        context.waitEventHandlerEnd();
    }

    /**
     * Init the ExecutionContext
     */
    public static void initContext()
    {
        context = new ExecutionContext();
        context.initCurrentThread();
    }

    /**
     * Lock a mutex
     */
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

    /**
     * Unlock a mutex
     */
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

    /**
     * Stop the behavior execution
     */
    public static void stopExecution()
    {
        context.stopExecution();
    }
}
