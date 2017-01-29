package com.robapp.behaviors.item;

import com.mytechia.robobo.rob.IRobInterfaceModule;
import com.mytechia.robobo.rob.movement.IRobMovementModule;
import com.robapp.behaviors.actions.Acts;
import com.robapp.behaviors.executions.ContextManager;
import com.robapp.behaviors.interfaces.BehaviorItemI;
import com.robapp.tools.Utils;

import robdev.Behavior;

/**
 * Created by Arthur on 19/10/2016.
 */

public class NativeBehaviorItem implements BehaviorItemI {

    String name;
    Behavior behavior;

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
        Acts act = null;

        try{

            IRobMovementModule module = Utils.getRoboboManager().getModuleInstance(IRobMovementModule.class);
            IRobInterfaceModule rob =  Utils.getRoboboManager().getModuleInstance(IRobInterfaceModule.class);
            rob.getRobInterface().setOperationMode((byte)1);
            act = new Acts(module);
            ContextManager.initContext();
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
