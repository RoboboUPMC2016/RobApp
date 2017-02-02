import robdev.*;

public class BehaviorTestShock2 implements Behavior {

    @Override
    public void run(Actions actions) {

	    actions.moveForward(Event.SHOCK_DETECTED);
    }

}
