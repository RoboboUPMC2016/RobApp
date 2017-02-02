package com.robapp.behaviors.item;

import android.widget.Toast;

import com.mytechia.robobo.rob.IRobInterfaceModule;
import com.mytechia.robobo.rob.movement.IRobMovementModule;
import com.robapp.behaviors.actions.Actions;
import com.robapp.behaviors.executions.ContextManager;
import com.robapp.behaviors.loader.BehaviorClassLoader;
import com.robapp.behaviors.interfaces.BehaviorItemI;
import com.robapp.tools.Utils;

import java.io.File;
import java.util.StringTokenizer;

import robdev.Behavior;

/**
 * The implementation of BehaviotItemI.
 * It implement a behavior.
 * Created by Arthur on 19/10/2016.
 */

public class BehaviorFileItem implements BehaviorItemI
{
    private File file;
    private String name;
    private String url;
    private int id;

    /**
     * Constructor
     */
    public BehaviorFileItem()
    {
       file = null;
        name = null;
        url = null;
        id =-1;
    }

    /**
     * Constructor
     * @param f The file which contains the behavior code
     * @param id The behavior id
     */
    public BehaviorFileItem(File f,int id)
    {
        this.file =f;
        StringTokenizer tok = new StringTokenizer(file.getName(),".");
        name = tok.nextToken();
        url = null;
        this.id=id;
    }

    /**
     * Constructor
     * @param f The file which contains the behavior code
     * @param name The behavior name
     * @param id The behavior id
     */
    public BehaviorFileItem(File f,String name,int id)
    {
        this.file = f;
        this.name = name;
        url = null;
        this.id=id;
    }

    /**
     * Get the behavior file
     * @return The behavior file
     */
    public File getFile() {
        return file;
    }

    /**
     * Set behavior file
     * @param file
     */
    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * Set the behavior name
     * @param name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Set the behavior URL
     * @param url
     */
    public void setUrl(String url)
    {
        this.url = url;
    }

    /**
     * Get the the behavior URL
     * @return the behavior URL
     */
    public String getUrl()
    {
        return url;
    }

    /**
     * Get the behavior ID
     * @return the behavior ID
     */
    public int getId()
    {
        return id;
    }

    /**
     * Set the behavior ID
     * @param id The behavior ID
     */
    public void setId(int id)
    {
        this.id = id;
    }

    @Override
    public void run() {

        boolean status = false;
        Actions act = null;
        try{
            //We load dynamically the class
            String filename = file.getName();
            int ind = filename.indexOf(".");
            String className = filename.substring(0,ind);

            Class<?> myClass =  BehaviorClassLoader.getClassFromDexFile(Utils.getCurrentActivity().getApplicationContext(),file.getAbsolutePath(),className);
            Behavior behavior = (Behavior) myClass.newInstance();

            //Useless test because if behavior is not an isntance of Behavior
            //The previous line throws an Exception but just in case we let the test
            if(behavior instanceof Behavior)
            {
                //Change the operation mode for stopping errors (Communication Problems - Robobo)
                IRobMovementModule module = Utils.getRoboboManager().getModuleInstance(IRobMovementModule.class);
                IRobInterfaceModule rob =  Utils.getRoboboManager().getModuleInstance(IRobInterfaceModule.class);
                rob.getRobInterface().setOperationMode((byte)1);
                //Init the ExecutionContext
                ContextManager.initContext();
                act = new Actions(module);
                //Start the behavior
                behavior.run(act);
            }
        }
        catch(Exception e){
            e.printStackTrace();
            Toast.makeText(Utils.getCurrentActivity().getApplicationContext(),"Error : "+e.getMessage(),Toast.LENGTH_SHORT);
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
