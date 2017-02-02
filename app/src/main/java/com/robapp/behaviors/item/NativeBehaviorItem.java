package com.robapp.behaviors.item;

import com.mytechia.robobo.rob.IRobInterfaceModule;
import com.mytechia.robobo.rob.movement.IRobMovementModule;
import com.robapp.behaviors.actions.Actions;
import com.robapp.behaviors.executions.ContextManager;
import com.robapp.behaviors.interfaces.BehaviorItemI;
import com.robapp.tools.Utils;

import robdev.Behavior;

/**
 *  A Behaviot item which represents the native behavior, the default behaviors of the application
 * Created by Arthur on 19/10/2016.
 */

public class NativeBehaviorItem implements BehaviorItemI {

    String name;
    Behavior behavior;

    /**
     * Constructor
     * @param name The behavior name
     * @param behavior The behavior instance
     */
    public NativeBehaviorItem(String name, Behavior behavior)
    {
        this.name = name;
        this.behavior = behavior;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void run() {

        boolean status = false;
        Actions act = null;

        try{
            //Change operation mode for stopping communication errors
            IRobMovementModule module = Utils.getRoboboManager().getModuleInstance(IRobMovementModule.class);
            IRobInterfaceModule rob =  Utils.getRoboboManager().getModuleInstance(IRobInterfaceModule.class);
            rob.getRobInterface().setOperationMode((byte)1);
            act = new Actions(module);
            //Init the ExecutionContext
            ContextManager.initContext();
            //Start the behavior
            behavior.run(act);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally {
            Utils.setBehaviorStarted(false);
            Utils.updateBehaviorActivity();
        }
    }

    @Override
    public String toString()
    {
        return getName();
    }
}
