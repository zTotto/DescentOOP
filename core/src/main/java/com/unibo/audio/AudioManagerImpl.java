package com.unibo.audio;
import java.util.HashMap;
import java.util.Map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Implementation of AudioManager interface that leverages the LibGdx sound framework.
 */
public class AudioManagerImpl implements AudioManager {
	
	private final Map<String, Sound> soundEffects = new HashMap<>();
	private final Map<String, Music> songs = new HashMap<>();
	
	/**
	 * Constructor
	 */
	public AudioManagerImpl() {
		super();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void stopMusic(final String path) {
		if (isAlreadyUsed(path)) {
			getSong(path).stop();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void pauseMusic(final String path) {
		if (isAlreadyUsed(path)) {
			getSong(path).pause();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void playMusic(final String path, final Boolean looping, final float volume) {
		if (isAlreadyUsed(path) && !getSong(path).isPlaying()) {
			getSong(path).setLooping(looping);
			getSong(path).setVolume(volume);
			getSong(path).play();
		} else if (!isAlreadyUsed(path)) {
			Music music = Gdx.audio.newMusic(Gdx.files.internal(path));
			songs.put(path, music);
			getSong(path).setLooping(looping);
			getSong(path).setVolume(volume);
			getSong(path).play();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void disposeMusic(final String path) {
		if (isAlreadyUsed(path)) {
			getSong(path).dispose();
		}
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	public void playSoundEffect(final String path, final float volume) {
		if (isAlreadyUsed(path)) {
			getSound(path).setVolume(0, volume);
			getSound(path).play();
		}	else {
			Sound sound = Gdx.audio.newSound(Gdx.files.internal(path));
			sound.setVolume(0, volume);
			soundEffects.put(path, sound);
			getSound(path).play();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void changeSoundVolume(final String path, final float f) {
		if (soundEffects.containsKey(path)) {
			soundEffects.get(path).setVolume(0, f);
		}
	}

	/**
	 * Sets the current song's volume and whether or not it's looping.
	 * Should not be used if a song isn't already set.
	 * 
	 * @param path		the path of the song's file
	 * @param looping	True if song needs to loop, false otherwise
	 * @param volume	the volume of the song
	 */
	public void modifyMusic(final String path, final Boolean looping, final float volume) {
		if (isAlreadyUsed(path)) {
			getSong(path).setLooping(looping);
			getSong(path).setVolume(volume);
		}
	}
	
	/**
	 * Checks if the file is already present in either map.
	 * 
	 * @param path	path of the song's file
	 * @return True if the file was already present in the songs or the sounds map,
	 * false otherwise.
	 */
	private Boolean isAlreadyUsed(final String path) {
		return (this.songs.containsKey(path) || this.soundEffects.containsKey(path));
	}
	
	/**
	 * Getter for a Music from the songs map, should be used
	 * together with isAlreadyUsed.
	 * 
	 * @param path	path of the song's file
	 * @return a Music
	 */
	private Music getSong(final String path) {
		return songs.get(path);
	}
	
	/**
	 * Getter for a Sound from the soundEffects map, should be used
	 * together with isAlreadyUsed.
	 * 
	 * @param path	path of the sound's file
	 * @return a Sound
	 */
	private Sound getSound(final String path) {
		return soundEffects.get(path);
	}

	
}
