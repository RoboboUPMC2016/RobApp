package com.robapp.behaviors.executions;

import com.robapp.behaviors.listener.CmdHandler;
import com.robapp.behaviors.interfaces.EventListenerI;
import com.robapp.behaviors.listener.HandlerListener;

import robdev.Event;

/**
 * This the class which implements the execution context
 * Created by Arthur on 09/12/2016.
 */

public class ExecutionContext {

    private ExecutionContext child;
    private Dispatcher dispatcher;
    private CmdHandler cmdHandler;
    private boolean handlerRunning;
    private HandlerListener handlerListener;
    private Thread currentThread;

    /**
     * The constructor
     */
    public ExecutionContext() {

        this.child = null;
        this.dispatcher = new Dispatcher();
        this.cmdHandler = new CmdHandler();
        this.handlerRunning = false;
        this.handlerListener = new HandlerListener();
        this.currentThread = null;
    }

    /**
     * Notify and event
     * The Execution constext will give the event to its ExecutionContext child if it is not null
     * Else it will use is own dispatcher to manage the event
     * @param e The event to dispatch
     */
    public void notifyEvent(Event e){
        if(child != null)
        {
            child.notifyEvent(e);
            return;
        }
        dispatcher.dispatchEvent(e);
    }

    /**
     * Create an inner ExecutionContext
     */
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

    /**
     * Remove the inner ExecutionContext
     */
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

    /**
     *  Add an event listener
     *  If the inner context is not null it will call the method of the inner context.
     *  Else the listener is added to the dispatcher of the current object
     * @param listener The listener to add
     */
    protected void addEventListener(EventListenerI listener)
    {
        if(currentThread == Thread.currentThread())
            dispatcher.addEventListener(listener);
        else
            child.addEventListener(listener);
    }

    /**
     * Remove an event listener
     * If the inner context is not null it will call the method of the inner context.
     *  Else the listener is removed of the dispatcher of the current object
     * @param listener
     */
    protected void removeEventListener(EventListenerI listener)
    {
        if(currentThread == Thread.currentThread())
            dispatcher.removeEventListener(listener);
        else
            child.removeEventListener(listener);

    }


    /**
     * If the inner context is not null the method of the inner context will be called
     * Else it return the CMDHandler of the current ExecutionContext
     * @return The CMDHandler
     */
    protected CmdHandler getCMDHandler()
    {
        if(currentThread == Thread.currentThread())
            return cmdHandler;
        else
            return child.getCMDHandler();
    }

    /**
     * IF the inner context is not null it will call the method of the inner context
     * Else it will use the CMDHandler of the current context to handle the command end
     */
    public void  notifyEndCommand()
    {
        if(child != null)
            child.notifyEndCommand();
        else if(cmdHandler.IsWaiting())
            cmdHandler.notifyEndCmd();
    }


    /**
     * Wait the end of an event handler
     */
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

    /**
     * Set if an handler is running
     * @param handlerRunning
     */
    protected void setIsHandlerRunning(boolean handlerRunning)
    {
        if(handlerRunning)
            currentThread = Thread.currentThread();
        else
            currentThread = null;

        this.handlerRunning = handlerRunning;
    }

    /**
     * Record the thread which is associated to the current ExecutionCOntext
     */
    protected void initCurrentThread()
    {
        currentThread = Thread.currentThread();
    }
    /**
     * If the inner context is not null it will call the method of the inner context then it interrupts
     * the execution of the thread associated to the current execution context
     */
    protected void stopExecution()
    {
        if(child != null)
            child.stopExecution();
        currentThread.interrupt();
        child = null;
    }
}
