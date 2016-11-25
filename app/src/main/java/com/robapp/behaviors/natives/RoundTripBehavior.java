package com.robapp.behaviors.natives;

import robdev.*;

public class RoundTripBehavior implements Behavior {

	public static final int ROUND_TRIP_NUMBER = 5;
	
	public void run(Actions actions) {
		
		int i = 0;
		
		while(true){
			
			//The robot moves forward during 3 seconds,
			actions.moveForward(3);
			
			// it performs a U-turn,
			actions.turnRight();
			actions.turnRight();
			
			// and comes back to its starting point !
			actions.moveForward(3);

			i+=1;
			if(i >= ROUND_TRIP_NUMBER)
				break;
			
			// Let's start again !
				
		}
	}

	@Override
	public void run() {

	}
}
