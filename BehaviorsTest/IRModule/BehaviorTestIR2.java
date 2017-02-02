import robdev.*;

public class BehaviorTestIR2 implements Behavior {

    @Override
    public void run(Actions actions) {
		
		actions.wait(Event.IRBACK);
	        actions.moveForward(3); 
    }

}
