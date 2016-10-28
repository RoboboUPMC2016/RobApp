package com.robapp.behaviors.classloader;

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
    public static Class<Behavior> getClassFromDexFile(final String dexPath,final String className){
        BehaviorClassLoader classLoader = new BehaviorClassLoader(dexPath);
        try{
        Class<Behavior> behaviorClass = (Class<Behavior>) classLoader.loadClass(className);
            return behaviorClass;
        }catch(ClassNotFoundException ex){
            //TODO handle class not found case?
        }
        return null;
    }
}
