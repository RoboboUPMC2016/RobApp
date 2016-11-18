package com.robapp.utils;

import android.app.Activity;
import android.content.Context;

import com.mytechia.robobo.framework.RoboboManager;
import com.robapp.app.activity.BaseActivity;
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

    private static BaseActivity current = null;
    private static RoboboManager roboboManager;

    private static ArrayList<BehaviorItemI> behaviors;


    public static void init(Context context)
    {
        behaviors = new ArrayList<BehaviorItemI>();
        behaviors.add(new NativeBehaviorItem("Dummy Behavior Native",new DummyBehavior()));
        behaviors.add(new NativeBehaviorItem("Round Trip",new RoundTripBehavior()));
        behaviors.add(new NativeBehaviorItem("Square Trip",new SquareTripBehavior()));

        File dir = context.getDir("behavior_downloaded",Context.MODE_PRIVATE);

        for(File f : dir.listFiles())
        {
            System.out.println("Fichier : "+f.getName());
            behaviors.add(new BehaviorFileItem(f));
        }
    }
    public static void setCurrentActivity(BaseActivity current)
    {
        Utils.current = current;
    }

    static public BaseActivity getCurrentActivity()
    {
        return current;
    }

    static public String moveFileToDir(File f,File dir) throws Exception
    {

        System.out.println("File : "+f.getAbsolutePath());
        File newF = new File(dir.getAbsolutePath()+"/"+ f.getName());
        if(newF.exists())
            newF.delete();
        newF.createNewFile();
        System.out.println("New File : "+newF.getAbsolutePath());
        FileChannel in = new FileInputStream(f).getChannel();
        FileChannel out = new FileOutputStream(newF).getChannel();

        in.transferTo(0,in.size(),out);

        return newF.getName();
    }

    static public boolean moveBehaviorDownloaded(Context context,File f)
    {

        File dir = context.getDir("behavior_downloaded",Context.MODE_PRIVATE);

        try {
            moveFileToDir(f, dir);
            return true;
        }
        catch(Exception e)
        {
            System.err.println("Error : behavior not moved");
            e.printStackTrace();
        }

        return false;

    }

    static public ArrayList<BehaviorItemI> getAllItem()
    {
        return behaviors;
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
