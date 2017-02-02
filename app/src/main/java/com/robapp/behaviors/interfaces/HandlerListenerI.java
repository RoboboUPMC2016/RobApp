package com.robapp.behaviors.interfaces;

/**
 * The interface for the HandlerListener, it allows to wait the end of a handler
 * Created by Arthur on 05/01/2017.
 */

public interface HandlerListenerI {

    /**
     * Wait the end of a handler.
     * The current thread wiil wait that another thread call the function notifyHandlerEnd
     */
    public void waitHandlerEnd();

    /**
     * Notify another thread that the handler has ended
     */
    public void notifyHandlerEnd();
}
