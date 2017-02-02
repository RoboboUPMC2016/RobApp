package com.robapp.behaviors.natives;

import robdev.*;

public class BeahviorTestReactive3 implements Behavior {

   private int cpt;

   @Override
    public void run(final Actions actions) {
	
	cpt = 0;
	
        Runnable run = new Runnable() {
            @Override
            public void run() {
  	
		actions.stop();
 		actions.speak("Shock Detected");
		actions.moveBackward(2);

		if(cpt % 2 == 0)
		{
			actions.speak("Left");
 			actions.setEmotion(Emotion.Surprised);
			actions.turnLeft();
		}
		else
		{	actions.speak("Left");
			actions.setEmotion(Emotion.Embarrased);
			actions.turnRight();
		}
              	cpt++;
               
               actions.speak("I love Scrum or not");
                
                
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

