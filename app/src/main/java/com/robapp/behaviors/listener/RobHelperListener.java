package com.robapp.behaviors.listener;

import android.widget.Button;

import com.mytechia.robobo.framework.RoboboManager;
import com.mytechia.robobo.framework.RoboboManagerListener;
import com.mytechia.robobo.framework.RoboboManagerState;
import com.mytechia.robobo.framework.service.RoboboServiceHelper;
import com.robapp.app.activity.BaseActivity;
import com.robapp.utils.Utils;

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

    }

    @Override
    public void onError(String errorMsg) {
        Utils.getCurrentActivity().showErrorDialog(errorMsg);
    }
}