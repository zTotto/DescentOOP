package com.unibo.audio;
import java.util.HashMap;
import java.util.Map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Implementation of AudioManager interface that leverages the LibGdx sound framework
 * under the assumption that only one soundtrack is played at any given time 
 * (Typically the background song of a map).
 */
public class AudioManagerImpl implements AudioManager {
	
	private  Music soundtrack = null;
	private final Map<String, Sound> soundEffects = new HashMap<>();
	
	/**
	 * Constructor used for a level without a soundtrack,
	 * though one can be added with setMusic method.
	 */
	public AudioManagerImpl() {
		super();
	}
	
	
	/**
	 * Constructor that sets the background song.
	 * @param path	the path of the song file
	 */
	public AudioManagerImpl(final String path) {
		super();
		setMusic(path);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void stopMusic() {
		soundtrack.stop();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void pauseMusic() {
		soundtrack.pause();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void playMusic() {
		soundtrack.play();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void disposeMusic() {
		soundtrack.dispose();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void setMusic(final String path) {
		this.soundtrack = Gdx.audio.newMusic(Gdx.files.internal(path));
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void playSoundEffect(final String path, final Float volume) {
		if (soundEffects.containsKey(path)) {
			soundEffects.get(path).setVolume(0, volume);
			soundEffects.get(path).play();
		}	else {
			Sound sound = Gdx.audio.newSound(Gdx.files.internal(path));
			sound.setVolume(0, volume);
			soundEffects.put(path, sound);
			soundEffects.get(path).play();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void changeSoundVolume(final String path, final Float f) {
		if (soundEffects.containsKey(path)) {
			soundEffects.get(path).setVolume(0, f);
		}
	}

	/**
	 * Sets the current song's volume and whether or not it's looping.
	 * Should not be used if a song isn't already set.
	 */
	public void modifyMusic(final Boolean looping, final Float volume) {
		this.soundtrack.setVolume(volume);
		this.soundtrack.setLooping(looping);
	}


}
