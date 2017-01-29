package com.robapp.behaviors.listener;

import com.robapp.behaviors.interfaces.HandlerListenerI;

/**
 * Created by Arthur on 05/01/2017.
 */

public class HandlerListener implements HandlerListenerI {


    @Override
    public void waitHandlerEnd() {
        synchronized (this)
        {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void notifyHandlerEnd() {
        synchronized (this)
        {
            this.notify();
        }

    }
}
