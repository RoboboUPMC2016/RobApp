package com.robapp.behaviors.natives;

import robdev.Actions;
import robdev.Behavior;
import robdev.Events;

/**
 * Created by Arthur on 01/12/2016.
 */

public class MyBehavior implements Behavior {

    @Override
    public void run(Actions actions) {

            System.out.println("Etape 1");
            actions.moveForward(Events.IRFRONT);
            System.out.println("Etape 2");
            actions.wait(Events.IRBACK);
            System.out.println("Etape 3");
            actions.moveBackward(Events.SHOCK_DETECTED);
            System.out.println("Fin");

    }
}
