package com.robapp.behaviors.exceptions;

/**
 * A RuntimeException for stopping behavior execution
 * Created by Arthur on 25/11/2016.
 */

public class StopBehaviorException extends RuntimeException {

    public StopBehaviorException(String msg)
    {
        super(msg);
    }
}
