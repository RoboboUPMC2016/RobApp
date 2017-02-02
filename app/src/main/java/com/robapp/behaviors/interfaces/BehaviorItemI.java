package com.robapp.behaviors.interfaces;
/**
 * The interface which represents a behavior
 * Created by Arthur on 19/10/2016.
 */

public interface BehaviorItemI extends Runnable {

    /**
     * Get the name of the behavior
     * @return The behavior name
     */
    public String getName();

   @Override
    public String toString();

}
