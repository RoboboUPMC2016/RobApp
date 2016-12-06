package com.robapp.behaviors.natives;

import com.mytechia.robobo.framework.hri.emotion.Emotion;
import com.robapp.behaviors.actions.Acts;

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
           ((Acts)actions).setEmotion(Emotion.IN_LOVE);
            System.out.println("Etape 3");
            actions.wait(Events.IRBACK);
            System.out.println("Etape 4");
            actions.moveBackward(Events.SHOCK_DETECTED);
            System.out.println("Fin");

    }
}
