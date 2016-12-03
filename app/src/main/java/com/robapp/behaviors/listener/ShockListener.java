package com.robapp.behaviors.listener;

import com.mytechia.robobo.framework.misc.shock.IShockDetectionListener;
import com.mytechia.robobo.framework.misc.shock.ShockCategory;
import com.robapp.utils.Utils;

/**
 * Created by Arthur on 01/12/2016.
 */

public class ShockListener implements IShockDetectionListener {

    private Flag flag;

    public ShockListener()
    {
        flag =new Flag();
    }

    @Override
    public void shockDetected(ShockCategory shock) {

        System.out.println("Shock Detected");
        if(flag.isWaited())
        {
            System.out.println("Shock Waited");
            flag.set(true);
            synchronized (Utils.getRoboboManager())
            {
                Utils.getRoboboManager().notify();
            }
        }
    }

    public boolean flagIsSet()
    {
        return flag.isSet();
    }

    public void setWaited(boolean b)
    {
        flag.setIsWaited(b);
        flag.set(false);

    }

}
