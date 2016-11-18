package com.robapp.behaviors.item;

import com.mytechia.robobo.rob.IRobInterfaceModule;
import com.mytechia.robobo.rob.movement.IRobMovementModule;
import com.robapp.behaviors.actions.Acts;
import com.robapp.behaviors.compiler.BehaviorClassLoader;
import com.robapp.behaviors.interfaces.BehaviorItemI;
import com.robapp.utils.Utils;

import java.io.File;
import java.util.StringTokenizer;

import robdev.Actions;
import robdev.Behavior;

/**
 * Created by Arthur on 19/10/2016.
 */

public class BehaviorFileItem implements BehaviorItemI
{
    private File file;
    private String name;
    public BehaviorFileItem(File f)
    {
        this.file =f;
        StringTokenizer tok = new StringTokenizer(file.getName(),".");
        name = tok.nextToken();
    }

    @Override
    public String getName() {

        return name;
    }


    @Override
    public void run() {
        try{
            Class<?> myClass =  BehaviorClassLoader.getClassFromDexFile(Utils.getCurrentActivity().getApplicationContext(),file.getAbsolutePath(),getName());
            Object obj =  myClass.newInstance();
            System.out.println("Class Loaded  + "+obj.toString());
            if(obj instanceof Behavior)
            {
                Behavior behavior = (Behavior) myClass.newInstance();
                IRobMovementModule module = Utils.getRoboboManager().getModuleInstance(IRobMovementModule.class);
                IRobInterfaceModule rob =  Utils.getRoboboManager().getModuleInstance(IRobInterfaceModule.class);
                rob.getRobInterface().setOperationMode((byte)1);

                Actions act = new Acts(module);
                behavior.run(act);
            }


        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public String toString()
    {
        return getName();
    }
}
