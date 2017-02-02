package com.robapp.behaviors.natives;

import robdev.*;

public class BeahviorTestReactive2 implements Behavior {


    @Override
    public void run(final Actions actions) {

        Runnable run1 = new Runnable() {
            @Override
            public void run() {
                actions.stop();
                actions.moveBackward(2);
                actions.turnRight();
            }

        };

       Runnable run2 = new Runnable() {
            @Override
            public void run() {
                actions.stop();
                actions.moveBackward(2);
                actions.turnLeft();
            }

        };

		

        actions.when(Event.SHOCK_DETECTED, run1);
	actions.when(Event.IRFRONT, run2);
	
	actions.setEmotion(Emotion.Smyling);
	int cpt = 0;        
	while (true)
        {
            
            actions.moveForward(3);
	    if(cpt % 2 == 0)
            	actions.turnRight();
	    else
		actions.turnLeft();
	
	    cpt++;
	    if(cpt == 2)
		cpt =0;
        }
    }
}

