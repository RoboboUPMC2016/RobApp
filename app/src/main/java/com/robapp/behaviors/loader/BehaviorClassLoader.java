package com.robapp.behaviors.loader;

import android.content.Context;
import android.widget.Toast;

import java.io.File;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;
import robdev.Behavior;

/**
 * The behavior class loader, used for loading a behavior class
 */
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

            File f = new File(dexPath);
            System.out.println("File : "+f.length());

            File dexOutputDir = context.getCodeCacheDir();
            DexClassLoader classLoader = new DexClassLoader(dexPath,dexOutputDir.getAbsolutePath(),null,context.getClassLoader());
            Class<?> behaviorClass = (Class<?>)classLoader.loadClass(className);
            return behaviorClass;

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
