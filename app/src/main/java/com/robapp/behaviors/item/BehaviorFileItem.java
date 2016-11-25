package com.robapp.behaviors.item;

import android.widget.Button;

import com.mytechia.robobo.rob.IRobInterfaceModule;
import com.mytechia.robobo.rob.movement.IRobMovementModule;
import com.robapp.R;
import com.robapp.app.activity.BehaviorActivity;
import com.robapp.behaviors.actions.Acts;
import com.robapp.behaviors.loader.BehaviorClassLoader;
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
    private String url;

    public BehaviorFileItem()
    {
       file = null;
        name = null;
        url = null;
    }

    public BehaviorFileItem(File f)
    {
        this.file =f;
        StringTokenizer tok = new StringTokenizer(file.getName(),".");
        name = tok.nextToken();
        url = null;
    }

    public BehaviorFileItem(File f,String name)
    {
        this.file = f;
        this.name = name;
        url = null;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getUrl()
    {
        return url;
    }

    @Override
    public void run() {
        try{
            Class<?> myClass =  BehaviorClassLoader.getClassFromDexFile(Utils.getCurrentActivity().getApplicationContext(),file.getAbsolutePath(),getName());
            Object obj =  myClass.newInstance();
            System.out.println("Class "+myClass.getName()+"loaded : "+obj.toString());

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

        if(Utils.getCurrentActivity() instanceof BehaviorActivity)
        {
            Button startButton = (Button) Utils.getCurrentActivity().findViewById(R.id.startButton);
            startButton.setText("Demmarer");
            ((BehaviorActivity) Utils.getCurrentActivity()).setBehaviorStarted(false);
        }

    }

    @Override
    public String toString()
    {
        return getName();
    }
}
