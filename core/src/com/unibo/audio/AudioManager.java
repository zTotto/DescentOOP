package com.unibo.audio;

public interface AudioManager {
	
	public void startUp(String Path);
	
	public void stopMusic();
	
	public void pauseMusic();
	
	public void playMusic();
	
	public void disposeMusic();
	
	public void changeMusic(String path);
	
	public void playSoundEffect(String path);
	
	public void changeSoundVolume(float f, String path);
}
