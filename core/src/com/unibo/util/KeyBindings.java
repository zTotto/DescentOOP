package com.unibo.util;

import com.badlogic.gdx.Input;

/**
 * Enumarator for the Key Bindings
 *
 */
public enum KeyBindings {
	PAUSE(Input.Keys.ESCAPE), 
	ATTACK(Input.Keys.SPACE),
	MOVE_UP(Input.Keys.UP),
	MOVE_DOWN(Input.Keys.DOWN),
	MOVE_RIGHT(Input.Keys.RIGHT),
	MOVE_LEFT(Input.Keys.LEFT),
	PICK_UP(Input.Keys.E),
	SWITCH_WEAPON(Input.Keys.W);
	
	private int key;
	
	/**
	 * Constructor for the KeyBindings enum
	 * 
	 * @param key
	 * 			key associated to the action
	 */
	private KeyBindings(int key) {
		this.key = key;
	}
	
	/**
	 * @return the key associated to the action
	 */
	public int getKey() {
		return this.key;
	}
	
	/**
	 * Method used to change the KeyBindings, through the setting menù
	 * 
	 * @param key
	 * 			the new key associated to the action
	 */
	public void changeKey(int key) {
		this.key = key;
	}
}
