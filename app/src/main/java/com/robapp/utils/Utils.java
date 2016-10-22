package com.robapp.utils;

import android.app.Activity;
import android.content.Context;

import com.mytechia.robobo.framework.RoboboManager;
import com.robapp.behaviors.BehaviorFileItem;
import com.robapp.behaviors.DummyBehavior;
import com.robapp.behaviors.NativeBehaviorItem;
import com.robapp.behaviors.RoundTripBehavior;
import com.robapp.behaviors.SquareTripBehavior;
import com.robapp.interfaces.BehaviorItemI;

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

    public static void setCurrentActivity(Activity current)
    {
        Utils.current = current;
    }

    static public Activity getCurrentActivity()
    {
        return current;
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

    static public void setRoboboManager(RoboboManager roboboManager)
    {
        Utils.roboboManager = roboboManager;
    }

    static public RoboboManager getRoboboManager()
    {
        return roboboManager;
    }
}
