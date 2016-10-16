package com.robapp.utils;

import android.app.Activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * Created by Arthur on 16/10/2016.
 */

public class Utils {

    private static Activity current = null;

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
}
