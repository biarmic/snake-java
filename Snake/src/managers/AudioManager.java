package managers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioManager {
	private static ArrayList<Clip> clips = new ArrayList<Clip>();
	private static HashMap<String, File> soundList = new HashMap<String, File>();
	private static boolean SOUND_ON = true;

	public AudioManager() {
		soundList.put("click",new File("src/sounds/click.wav"));
	}
	
	public static boolean isSoundOn() {
		return SOUND_ON;
	}

	public static void switchSound() {
		SOUND_ON = !SOUND_ON;
		stopClips();
	}

	public static Clip playSound(String key) {
		if (SOUND_ON && soundList.containsKey(key)) {
			try {
				Clip clip = AudioSystem.getClip();
				clips.add(clip);
				clip.open(AudioSystem.getAudioInputStream(soundList.get(key)));
				clip.addLineListener(new LineListener() {
					@Override
					public void update(LineEvent event) {
						if (event.getType() == LineEvent.Type.STOP)
							clips.remove(((Clip) event.getSource()));
					}
				});
				clip.start();
				return clip;
			} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void refresh() {
		if (!SOUND_ON)
			stopClips();
	}

	private static void stopClips() {
		while (clips.size() > 0)
			clips.remove(0).stop();
	}
}
