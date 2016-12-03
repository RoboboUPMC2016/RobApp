package com.robapp.behaviors.listener;

import android.widget.Button;

import com.mytechia.commons.framework.exception.InternalErrorException;
import com.mytechia.robobo.framework.RoboboManager;
import com.mytechia.robobo.framework.RoboboManagerListener;
import com.mytechia.robobo.framework.RoboboManagerState;
import com.mytechia.robobo.framework.exception.ModuleNotFoundException;
import com.mytechia.robobo.framework.misc.shock.IShockDetectionListener;
import com.mytechia.robobo.framework.misc.shock.IShockDetectionModule;
import com.mytechia.robobo.framework.misc.shock.ShockCategory;
import com.mytechia.robobo.framework.service.RoboboServiceHelper;
import com.mytechia.robobo.rob.BatteryStatus;
import com.mytechia.robobo.rob.FallStatus;
import com.mytechia.robobo.rob.GapStatus;
import com.mytechia.robobo.rob.IRSensorStatus;
import com.mytechia.robobo.rob.IRob;
import com.mytechia.robobo.rob.IRobInterfaceModule;
import com.mytechia.robobo.rob.IRobStatusListener;
import com.mytechia.robobo.rob.MotorStatus;
import com.mytechia.robobo.rob.WallConnectionStatus;
import com.mytechia.robobo.rob.movement.IRobMovementModule;
import com.robapp.app.activity.BaseActivity;
import com.robapp.utils.Utils;

import java.util.Collection;

/**
 * Created by Arthur on 03/11/2016.
 */

public class RobHelperListener implements RoboboServiceHelper.Listener
{

    @Override
    public void onRoboboManagerStarted(RoboboManager roboboManager) {
        Utils.setRoboboManager(roboboManager);
        Utils.getCurrentActivity().dismissProgessDial();
        BaseActivity.robStarted = true;


        roboboManager.addFrameworkListener(new RoboboManagerListener() {
            @Override
            public void loadingModule(String moduleInfo, String moduleVersion) {
            }

            @Override
            public void moduleLoaded(String moduleInfo, String moduleVersion) {
                System.out.println("ModuleInfo : "+moduleInfo);
                System.out.println("ModuleVersion : "+moduleVersion);

            }

            @Override
            public void frameworkStateChanged(RoboboManagerState state) {
            }

            @Override
            public void frameworkError(Exception ex) {
            }
        });

        try{
            final EventListener eventListener = new EventListener();
            Utils.setEventListener(eventListener);

            IShockDetectionModule shockMod = (IShockDetectionModule) roboboManager.getModuleInstance(IShockDetectionModule.class);
            shockMod.subscribe(new IShockDetectionListener() {
                @Override
                public void shockDetected(ShockCategory shock) {
                    eventListener.onShockEvent(shock);
                }
            });

            CmdListener cmdListener = new CmdListener();
            Utils.setStatusListener(cmdListener);

            IRobInterfaceModule robMod = ((IRobInterfaceModule)roboboManager.getModuleInstance(IRobInterfaceModule.class));
            IRob rob = robMod.getRobInterface();
            rob.addRobStatusListener(new CmdListener());
            rob.addRobStatusListener(new IRobStatusListener() {
                @Override
                public void statusMotorsMT(MotorStatus motorStatus, MotorStatus motorStatus1) {

                }

                @Override
                public void statusMotorPan(MotorStatus motorStatus) {

                }

                @Override
                public void statusMotorTilt(MotorStatus motorStatus) {

                }

                @Override
                public void statusGaps(Collection<GapStatus> collection) {

                }

                @Override
                public void statusFalls(Collection<FallStatus> collection) {

                }

                @Override
                public void statusIRSensorStatus(Collection<IRSensorStatus> collection) {
                        Utils.getEventListener().onIREvent(collection);
                }

                @Override
                public void statusBattery(BatteryStatus batteryStatus) {

                }

                @Override
                public void statusWallConnectionStatus(WallConnectionStatus wallConnectionStatus) {

                }

                @Override
                public void robCommunicationError(InternalErrorException e) {

                }
            });

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