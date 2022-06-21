package com.unibo.audio;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class AudioManagerImpl implements AudioManager {
	
	private  Music soundtrack = null;
	
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

	
}
