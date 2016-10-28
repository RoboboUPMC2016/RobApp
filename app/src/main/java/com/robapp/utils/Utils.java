package com.robapp.utils;

import android.app.Activity;
import android.content.Context;

import com.mytechia.robobo.framework.RoboboManager;
import com.robapp.behaviors.item.BehaviorFileItem;
import com.robapp.behaviors.natives.DummyBehavior;
import com.robapp.behaviors.item.NativeBehaviorItem;
import com.robapp.behaviors.natives.RoundTripBehavior;
import com.robapp.behaviors.natives.SquareTripBehavior;
import com.robapp.behaviors.interfaces.BehaviorItemI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

/**
 * Created by Arthur on 16/10/2016.
 */

public class Utils {

    private static Activity current = null;
    private static RoboboManager roboboManager;
    private static String robName = null;
    private static boolean start = true;


    public static void setCurrentActivity(Activity current)
    {
        Utils.current = current;
    }

    static public Activity getCurrentActivity()
    {
        return current;
    }

    static public void setRoboboManager(RoboboManager roboboManager)
    {
        Utils.roboboManager = roboboManager;
    }

    static public RoboboManager getRoboboManager()
    {
        return roboboManager;
    }

    static public void setRobBluetoothName(String robName)
    {
        Utils.robName = robName;
    }

    static public String getRobBluetoothName()
    {
        return robName;
    }

    static public boolean isStart()
    {
        if(Utils.start == true)
        {
            Utils.start = false;
            return true;
        }

        return start;
    }
    static public String moveFileToDir(File f,File dir) throws Exception
    {
        File newF = new File(dir.getAbsolutePath()+"/"+ f.getName());
        FileChannel in = new FileInputStream(f).getChannel();
        FileChannel out = new FileOutputStream(newF).getChannel();

        in.transferTo(0,in.size(),out);

        return newF.getName();
    }

    static public ArrayList<BehaviorItemI> getAllItem(Context context)
    {
        ArrayList<BehaviorItemI> items = new ArrayList<BehaviorItemI>();
        items.add(new NativeBehaviorItem("Dummy Behavior",new DummyBehavior()));
        items.add(new NativeBehaviorItem("Round Trip",new RoundTripBehavior()));
        items.add(new NativeBehaviorItem("Square Trip",new SquareTripBehavior()));



        File dir = context.getDir("behavior_downloaded",Context.MODE_PRIVATE);

        for(File f : dir.listFiles())
            items.add(new BehaviorFileItem(f));
        return items;
    }


}
