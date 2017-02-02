import robdev.*;

public class BehaviorTestIR5 implements Behavior {

    @Override
    public void run(Actions actions) {
		
	while(true)
	{
		actions.moveForward(Event.IRFRONT);
		actions.moveBackward(Event.IRBACK);
	}	
    }

}
