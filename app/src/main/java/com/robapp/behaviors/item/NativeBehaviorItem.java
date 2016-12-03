package com.robapp.behaviors.item;

import android.widget.Button;

import com.mytechia.robobo.rob.IRobInterfaceModule;
import com.mytechia.robobo.rob.movement.IRobMovementModule;
import com.robapp.R;
import com.robapp.app.activity.BehaviorActivity;
import com.robapp.behaviors.actions.Acts;
import com.robapp.behaviors.interfaces.BehaviorItemI;
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


        boolean event = false;
        boolean status = false;
        Acts act = null;

        try{

            IRobMovementModule module = Utils.getRoboboManager().getModuleInstance(IRobMovementModule.class);
            IRobInterfaceModule rob =  Utils.getRoboboManager().getModuleInstance(IRobInterfaceModule.class);
            rob.getRobInterface().setOperationMode((byte)1);
            act = new Acts(module);

            Utils.getEventListener().subscribe(act);
            event = true;
            Utils.getStatusListener().subscribe(act);
            status = true;
            behavior.run(act);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally{
            Utils.setBehaviorStarted(false);
            if(event)
                Utils.getEventListener().unsubscribe(act);
            if(status)
                Utils.getStatusListener().unsubscribe(act);
        }

        Utils.setBehaviorStarted(false);


    }

    @Override
    public String toString()
    {
        return getName();
    }
}
