import robdev.*;

public class BehaviorTestShock1 implements Behavior {

    @Override
    public void run(Actions actions) {

		
	while(true)
	{
	    actions.wait(Event.SHOCK_DETECTED);
	    actions.moveForward(5);
	}
	   
    }

}
