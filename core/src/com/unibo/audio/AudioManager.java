package com.unibo.audio;

/**
 * An interface for a class that handles the audio of the game.
 */
public interface AudioManager {
	
	/** 
	 * Stops the specified song.
	 * @param path	path of the song's file
	 */
	void stopMusic(String path);
	
	/** 
	 * Pauses the specified song.
	 * @param path	path of the song's file
	 */
	void pauseMusic(String path);
	
	/** 
	 * Starts playing the specified song.
	 * The song starts only if there isn't another instance of the 
	 * same song already playing.
	 * @param path	path of the song's file
	 * @param looping	True to loop the song, False otherwise.
	 * @param volume	 the volume of the song.
	 */
	void playMusic(String path, Boolean looping, float volume);
	
	/** 
	 * Deletes the specified song.
	 * @param path	path of the song's file
	 */
	void disposeMusic(String path);
	
	/**
	 * Changes whether or not the song is looping and it's volume.
	 * @param path	path of the song's file
	 * @param looping	True to loop the song, False otherwise.
	 * @param volume	 the volume of the song.
	 */
	void modifyMusic(String path, Boolean looping, float volume);
	
	/**
	 * Plays a sound effect once.
	 * @param path	path of the sound file
	 * @param volume	volume at which to play the sound
	 */
	void playSoundEffect(String path, float volume);
	
	/**
	 * Changes the volume of a sound effect that was used previously.
	 * @param path	path of the sound file
	 * @param volume	volume at which to play the sound
	 */
	void changeSoundVolume(String path, float volume);
}
