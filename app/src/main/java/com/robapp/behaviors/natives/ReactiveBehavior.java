package com.robapp.behaviors.natives;

import robdev.Actions;
import robdev.Behavior;
import robdev.Emotion;
import robdev.Event;

/**
 * Created by Arthur on 15/12/2016.
 */

public class ReactiveBehavior implements Behavior {

    private int cpt = 0;

    @Override
    public void run(final Actions actions) {

        Runnable run = new Runnable() {
            @Override
            public void run() {
                actions.stop();
                if(cpt % 2 == 0) {
                    System.out.println("Emb");
                    actions.setEmotion(Emotion.Embarrased);
                }
                else
                {
                    System.out.println("Sad");
                    actions.setEmotion(Emotion.Sad);
                }
                cpt++;
            }

        };

        actions.when(Event.SHOCK_DETECTED, run);
        while (true)
        {
            actions.moveForward(3);
            actions.turnLeft();
        }
    }
}
