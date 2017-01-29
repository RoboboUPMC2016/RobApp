package com.robapp.behaviors.item;

import com.mytechia.robobo.rob.IRobInterfaceModule;
import com.mytechia.robobo.rob.movement.IRobMovementModule;
import com.robapp.behaviors.actions.Acts;
import com.robapp.behaviors.executions.ContextManager;
import com.robapp.behaviors.loader.BehaviorClassLoader;
import com.robapp.behaviors.interfaces.BehaviorItemI;
import com.robapp.tools.Utils;

import java.io.File;
import java.util.StringTokenizer;

import robdev.Behavior;

/**
 * Created by Arthur on 19/10/2016.
 */

public class BehaviorFileItem implements BehaviorItemI
{
    private File file;
    private String name;
    private String url;
    private int id;

    public BehaviorFileItem()
    {
       file = null;
        name = null;
        url = null;
        id =-1;
    }

    public BehaviorFileItem(File f,int id)
    {
        this.file =f;
        StringTokenizer tok = new StringTokenizer(file.getName(),".");
        name = tok.nextToken();
        url = null;
        this.id=id;
    }

    public BehaviorFileItem(File f,String name,int id)
    {
        this.file = f;
        this.name = name;
        url = null;
        this.id=id;
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

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    @Override
    public void run() {

        boolean status = false;
        Acts act = null;
        try{
            String filename = file.getName();
            int ind = filename.indexOf(".");
            String className = filename.substring(0,ind);
            System.out.println("Classe Name : "+className);
            System.out.println("File exists : "+file.exists());
            System.out.println("File can read : "+file.canRead());
            System.out.println("File can execute : "+file.canExecute());

            System.out.println("File can write : "+file.canWrite());
            Class<?> myClass =  BehaviorClassLoader.getClassFromDexFile(Utils.getCurrentActivity().getApplicationContext(),file.getAbsolutePath(),className);
            Object obj =  myClass.newInstance();

            if(obj instanceof Behavior)
            {
                Behavior behavior = (Behavior) myClass.newInstance();
                IRobMovementModule module = Utils.getRoboboManager().getModuleInstance(IRobMovementModule.class);
                IRobInterfaceModule rob =  Utils.getRoboboManager().getModuleInstance(IRobInterfaceModule.class);
                rob.getRobInterface().setOperationMode((byte)1);
                ContextManager.initContext();
                act = new Acts(module);
                behavior.run(act);
            }
        }
        catch(Exception e){
            e.printStackTrace();
            //Toast.makeText(Utils.getCurrentActivity().getApplicationContext(),"Error : "+e.getMessage(),Toast.LENGTH_SHORT);
        }
        finally{
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
