package com.unibo.audio;
import java.util.HashMap;
import java.util.Map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioManagerImpl implements AudioManager {
	
	private  Music soundtrack = null;
	
	public AudioManagerImpl() {
		super();
	}
	
	public AudioManagerImpl(Music soundtrack) {
		super();
		this.soundtrack = soundtrack;
	}
	
	private final Map<String, Sound> soundEffects = new HashMap<>();
	
	public void startUp(String path) {
		soundtrack = Gdx.audio.newMusic(Gdx.files.internal(path));
        soundtrack.setLooping(true);
        soundtrack.play();
        soundtrack.setVolume(0.4f);
	}
	
	public void stopMusic() {
		soundtrack.stop();
	}
	
	public void pauseMusic() {
		soundtrack.pause();
	}

	public void playMusic() {
		soundtrack.play();
	}
	
	public void disposeMusic() {
		soundtrack.dispose();
	}
	
	public void changeMusic(String path) {
		this.soundtrack = Gdx.audio.newMusic(Gdx.files.internal(path));
	}

	public void playSoundEffect(String path, Float volume) {
		if (soundEffects.containsKey(path)) {
			soundEffects.get(path).setVolume(0, volume);
			soundEffects.get(path).play();
		}
		
		else {
			Sound sound = Gdx.audio.newSound(Gdx.files.internal(path));
			sound.setVolume(0, volume);
			soundEffects.put(path, sound);
			soundEffects.get(path).play();
		}
	}
	
	public void changeSoundVolume(String path, Float f) {
		if (soundEffects.containsKey(path)) {
			soundEffects.get(path).setVolume(0, f);
		}
	}


}
