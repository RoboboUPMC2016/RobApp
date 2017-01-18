package com.robapp.behaviors.natives;

import robdev.Actions;
import robdev.Behavior;
import robdev.Emotion;
import robdev.Event;

public class AngryRobotBehavior implements Behavior {

	@Override
	public void run(Actions actions) {


		final Actions act = actions;
		Runnable run = new Runnable() {
			@Override
			public void run() {
				act.setEmotion(Emotion.Surprised);
				act.wait(5);
				act.setEmotion(Emotion.Angry);
				act.speak("Leave me alone!");
			}
		};

		actions.when(Event.SHOCK_DETECTED,run);
		actions.setEmotion(Emotion.Angry);

		while(true) {
			try {
				actions.setEmotion(Emotion.Angry);
				Thread.sleep(3);
				actions.setEmotion(Emotion.Happy);
				Thread.sleep(3);
				actions.setEmotion(Emotion.Laughing);
				Thread.sleep(3);
				actions.setEmotion(Emotion.Sad);
				Thread.sleep(3);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}
