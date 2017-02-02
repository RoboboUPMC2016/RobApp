package com.robapp.behaviors.interfaces;

/**
 * A command handler it will be used for receiving and handling the end of a command
 * Created by Arthur on 03/12/2016.
 */

public interface CmdHandlerI {

    /**
     *  Wait the end of a command.
     *  The thread which will call this method will wait
     *  taht another thread call the methods notifyEndCmd
     */
    public void waitEndCmd();

    /**
     * Notify the end of a command.
     * The current thread will notify the thrad which call the function waitEndCmd
     */
    public void notifyEndCmd();

    /**
     * A flag which tells if a thrad wait the end of a command
     * @return treu if a thread waif false otherwise
     */
    public boolean IsWaiting();
}

