package com.robapp.behaviors.item;

import com.robapp.app.interfaces.BehaviorItemI;

import java.io.File;

/**
 * Created by Arthur on 19/10/2016.
 */

public class BehaviorFileItem implements BehaviorItemI
{
    private File file;

    public BehaviorFileItem(File f)
    {
        this.file =file;
    }

    @Override
    public String getName() {
        return file.getName();
    }


    @Override
    public void run() {

    }
}
