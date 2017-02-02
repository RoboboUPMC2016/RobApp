package com.robapp.behaviors.listener;

import com.mytechia.robobo.framework.RoboboManager;
import com.mytechia.robobo.framework.hri.emotion.IEmotionModule;
import com.mytechia.robobo.framework.misc.shock.IShockDetectionListener;
import com.mytechia.robobo.framework.misc.shock.IShockDetectionModule;
import com.mytechia.robobo.framework.misc.shock.ShockCategory;
import com.mytechia.robobo.framework.service.RoboboServiceHelper;
import com.mytechia.robobo.rob.IRob;
import com.mytechia.robobo.rob.IRobInterfaceModule;
import com.robapp.app.activity.BaseActivity;
import com.robapp.app.activity.BehaviorActivity;
import com.robapp.behaviors.executions.ContextManager;
import com.robapp.tools.Utils;

import robdev.Event;

/**
 * AN implementation of RoboboServiceHelper.Listener
 * Used for linked ExcutinManger to modules for dispatching events.
 * Created by Arthur on 03/11/2016.
 */

public class RobHelperListener implements RoboboServiceHelper.Listener
{

    @Override
    public void onRoboboManagerStarted(RoboboManager roboboManager) {
        Utils.setRoboboManager(roboboManager);
        Utils.getCurrentActivity().dismissProgessDial();
        BaseActivity.robStarted = true;


        try{

            //Link the ShockModule
            IShockDetectionModule shockMod = (IShockDetectionModule) roboboManager.getModuleInstance(IShockDetectionModule.class);
            shockMod.subscribe(new IShockDetectionListener() {
                @Override
                public void shockDetected(ShockCategory shock) {
                    ContextManager.dispatcheEvent(Event.SHOCK_DETECTED);
                }
            });

            StatusListener statusListener = new StatusListener();
            Utils.setStatusListener(statusListener);

            IRobInterfaceModule robMod = ((IRobInterfaceModule)roboboManager.getModuleInstance(IRobInterfaceModule.class));
            IRob rob = robMod.getRobInterface();
            rob.addRobStatusListener(statusListener);

            if(Utils.getCurrentActivity() instanceof BehaviorActivity)
                roboboManager.getModuleInstance(IEmotionModule.class).subscribe((BehaviorActivity)Utils.getCurrentActivity());

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void onError(String errorMsg) {
        Utils.getCurrentActivity().showErrorDialog(errorMsg);
    }
}