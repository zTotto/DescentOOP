package com.unibo.util;

import com.badlogic.gdx.Input;

/**
 * Enumarator for the Key Bindings.
 *
 */
public enum KeyBindings {
    /**
     * Key to pause the game.
     */
    PAUSE(Input.Keys.ESCAPE),
    /**
     * Key to attack.
     */
    ATTACK(Input.Keys.SPACE),
    /**
     * Key to move up.
     */
    MOVE_UP(Input.Keys.UP),
    /**
     * Key to move down.
     */
    MOVE_DOWN(Input.Keys.DOWN),
    /**
     * Key to move right.
     */
    MOVE_RIGHT(Input.Keys.RIGHT),
    /**
     * Key to move left.
     */
    MOVE_LEFT(Input.Keys.LEFT),
    /**
     * Key to pick up items.
     */
    PICK_UP(Input.Keys.E),
    /**
     * Key to switch weapon.
     */
    SWITCH_WEAPON(Input.Keys.W);

    private int key;

    /**
     * Constructor for the KeyBindings enum.
     * 
     * @param key key associated to the action
     */
    KeyBindings(final int key) {
        this.key = key;
    }

    /**
     * @return the key associated to the action
     */
    public int getKey() {
        return this.key;
    }

    /**
     * Method used to change the KeyBindings, through the setting menu.
     * 
     * @param key the new key associated to the action
     */
    //TODO: write the edited key in a json file.
    public void changeKey(final int key) {
        this.key = key;
    }
}
