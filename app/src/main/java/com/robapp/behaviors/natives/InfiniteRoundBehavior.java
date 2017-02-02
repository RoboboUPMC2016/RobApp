package com.robapp.behaviors.natives;

import robdev.*;

public class InfiniteRoundBehavior implements Behavior{

	public void run(Actions actions) {

		do{

			// The robot will moves forward until a shock is detected,
			actions.moveForward(Event.SHOCK_DETECTED);
			// then it performs the opposite way
			actions.moveBackward(Event.SHOCK_DETECTED);

		}while(true); // and starts again for the rest of its life !

	}
}