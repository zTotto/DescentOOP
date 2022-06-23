package com.unibo.audio;

/**
 * An interface for a class that handles the audio of the game.
 */
public interface AudioManager {
	
	/** 
	 * Stops the current song.
	 */
	void stopMusic();
	
	/** 
	 * Pauses the current song.
	 */
	void pauseMusic();
	
	/** 
	 * Starts playing the current song.
	 */
	void playMusic();
	
	/** 
	 * Deletes the current song.
	 */
	void disposeMusic();
	
	/**
	 * Change the current song to a new one.
	 * @param path	path of the song file
	 */
	void setMusic(String path);
	
	/**
	 * Changes whether or not the song is looping and it's volume.
	 * @param looping	True to loop the song, False otherwise.
	 * @param volume	 the volume of the song.
	 */
	void modifyMusic(Boolean looping, Float volume);
	
	/**
	 * Plays a sound effect once.
	 * @param path	path of the sound file
	 * @param volume	volume at which to play the sound
	 */
	void playSoundEffect(String path, Float volume);
	
	/**
	 * Changes the volume of a sound effect that was used previously.
	 * @param path	path of the sound file
	 * @param volume	volume at which to play the sound
	 */
	void changeSoundVolume(String path, Float volume);
}
