import robdev.*;

public class BehaviorTestIR1 implements Behavior {

    @Override
    public void run(Actions actions) {
		
		actions.wait(Event.IRFRONT);
	        actions.moveBackward(3); 
    }

}
