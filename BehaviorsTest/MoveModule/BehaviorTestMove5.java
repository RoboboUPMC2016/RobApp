import robdev.*;

public class BehaviorTestMove5 implements Behavior {

    @Override
    public void run(Actions actions) {

	    actions.moveForward(5);
	    actions.turnRight();
	    actions.turnLeft();
            actions.moveBackward(5);
    }

}
