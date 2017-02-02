package com.robapp.behaviors.natives;

import robdev.*;

public class BeahviorTestReactive1 implements Behavior {


    @Override
    public void run(final Actions actions) {

        Runnable run = new Runnable() {
            @Override
            public void run() {
                actions.stop();
                actions.speak("Shock Detected");
                actions.setEmotion(Emotion.Surprised);
                actions.moveBackward(2);
                actions.turnRight();
            }

        };

        actions.when(Event.SHOCK_DETECTED, run);
        while (true)
        {
            actions.setEmotion(Emotion.Smyling);
            actions.moveForward(Event.IRFRONT);
            actions.moveBackward(1);
            actions.turnRight();

        }
    }
}

