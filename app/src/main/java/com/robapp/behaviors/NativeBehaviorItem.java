package com.robapp.behaviors;

import com.mytechia.robobo.framework.exception.ModuleNotFoundException;
import com.mytechia.robobo.rob.IRobInterfaceModule;
import com.mytechia.robobo.rob.movement.IRobMovementModule;
import com.robapp.actions.Acts;
import com.robapp.interfaces.BehaviorItemI;
import com.robapp.utils.Utils;

import robdev.Actions;
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

        try{
            IRobMovementModule module = Utils.getRoboboManager().getModuleInstance(IRobMovementModule.class);
            IRobInterfaceModule rob =  Utils.getRoboboManager().getModuleInstance(IRobInterfaceModule.class);
            rob.getRobInterface().setOperationMode((byte)1);

            Actions act = new Acts(module);
            behavior.run(act);
        }
        catch(Exception e)
        {
            System.out.println();
        }

    }
}
