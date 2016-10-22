package com.robapp.behaviors;

import robdev.Actions;
import robdev.Behavior;

/**
 * Created by Arthur on 20/10/2016.
 */

public class DummyBehavior implements Behavior {

    @Override
    public void run(Actions actions) {

            try {

                actions.moveForward(3);
                actions.turnRight();
                actions.turnRight();
                actions.moveForward(5);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
    }

    @Override
    public void run() {

    }
}
