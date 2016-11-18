package com.robapp.behaviors.compiler;

import android.content.Context;

import com.robapp.utils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.Enumeration;

import dalvik.system.DexClassLoader;
import dalvik.system.DexFile;
import dalvik.system.PathClassLoader;
import robdev.Behavior;


public final class BehaviorClassLoader extends PathClassLoader {

    private BehaviorClassLoader (final String dexPath){
        super(dexPath,dexPath.getClass().getClassLoader());
    }

    /**
     * Load dynamically a {@link Behavior} class from a compiled .dex file
     * @param dexPath the path of the .dex file
     * @param className the name of the class to load
     */
    //TODO test this method
    public static Class<?> getClassFromDexFile(Context context, final String dexPath, final String className){

        try{

            File dexOutputDir = context.getCodeCacheDir();
           /* DexFile dexFile = new DexFile(dexPath);
            Enumeration<String> classes = dexFile.entries();
            System.out.println("LIST CLASS");

            while(classes.hasMoreElements())
            {
                String classNameDex = classes.nextElement();
                System.out.println("Class : "+classNameDex);
                if(className.equals(classNameDex))
                    System.out.println("Class Found !!!");
            }*/
            DexClassLoader classLoader = new DexClassLoader(dexPath,dexOutputDir.getAbsolutePath(),null,context.getClassLoader());
            Class<?> behaviorClass = (Class<?>)classLoader.loadClass(className);
            return behaviorClass;
        }catch(Exception ex){
            System.out.println();
            ex.printStackTrace();
        }
        return null;
    }
}
