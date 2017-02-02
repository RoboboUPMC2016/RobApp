import robdev.*;

public class BehaviorTestSpeak1 implements Behavior {

    @Override
    public void run(Actions actions) {

	    actions.moveForward(5);
	    actions.speak("Hello I am Robobo who are you ?");
	
    }

}
