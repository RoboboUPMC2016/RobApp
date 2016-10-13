package com.example.arthur.roboboapp;

import com.mytechia.commons.framework.exception.InternalErrorException;
import com.mytechia.robobo.framework.RoboboManager;
import com.mytechia.robobo.framework.misc.shock.IShockDetectionListener;
import com.mytechia.robobo.framework.misc.shock.IShockDetectionModule;

/**
 * Created by Arthur on 06/10/2016.
 */

public class MyShockDetectionModule implements IShockDetectionModule {
    @Override
    public void subscribe(IShockDetectionListener listener) {

    }

    @Override
    public void unsubscribe(IShockDetectionListener listener) {

    }

    @Override
    public void startup(RoboboManager manager) throws InternalErrorException {

    }

    @Override
    public void shutdown() throws InternalErrorException {

    }

    @Override
    public String getModuleInfo() {
        return null;
    }

    @Override
    public String getModuleVersion() {
        return null;
    }
}
