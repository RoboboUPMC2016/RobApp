import robdev.*;

public class BehaviorTestEmotion1 implements Behavior {

    @Override
    public void run(Actions actions) {
		
		actions.setEmotion(Emotion.Normal);
		actions.wait(3);
		actions.setEmotion(Emotion.Happy);
		actions.wait(3);
		actions.setEmotion(Emotion.Angry);
		actions.wait(3);
		actions.setEmotion(Emotion.Laughing);
		actions.wait(3);
		actions.setEmotion(Emotion.Embarrased);
		actions.wait(3);
		actions.setEmotion(Emotion.Sad);
		actions.wait(3);
		actions.setEmotion(Emotion.Surprised);
		actions.wait(3);
		actions.setEmotion(Emotion.Symling);
		actions.wait(3);
		actions.setEmotion(Emotion.In_Love);
		actions.wait(3);
		actions.setEmotion(Emotion.Normal);
    }

}
