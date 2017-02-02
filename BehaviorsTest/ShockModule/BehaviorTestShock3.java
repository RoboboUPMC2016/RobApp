import robdev.*;

public class BehaviorTestShock3 implements Behavior {

    @Override
    public void run(Actions actions) {
		
	    while(true)
            {
		actions.moveForward(Event.SHOCK_DETECTED);
	        actions.moveBackward(Event.SHOCK_DETECTED);
	    }	   
    }

}
