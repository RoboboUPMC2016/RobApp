package com.robapp.behaviors.natives;

import robdev.*;

public class SquareTripBehavior implements Behavior {

	public static final int ROUND_TRIP_NUMBER = 1;

	public void run(Actions actions) {

		int i = 0;

		while(true){

			for(int j=0;j<4;j++){
				actions.moveForward(1);
				actions.turnRight();
			}

			i+=1;
			if(i >= ROUND_TRIP_NUMBER)
				break;

		}
	}

	@Override
	public void run() {

	}
}