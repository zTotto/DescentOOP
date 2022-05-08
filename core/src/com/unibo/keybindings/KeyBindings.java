package com.unibo.keybindings;

import com.badlogic.gdx.Input;

/**
 * Enumeration for the Key Bindings.
 *
 */
public enum KeyBindings {
    /**
     * Key to pause the game.
     */
    PAUSE(Input.Keys.ESCAPE, "Pause"),
    /**
     * Key to attack.
     */
    ATTACK(Input.Keys.SPACE, "Attack"),
    /**
     * Key to move up.
     */
    MOVE_UP(Input.Keys.UP, "MoveUp"),
    /**
     * Key to move down.
     */
    MOVE_DOWN(Input.Keys.DOWN, "MoveDown"),
    /**
     * Key to move right.
     */
    MOVE_RIGHT(Input.Keys.RIGHT, "MoveRight"),
    /**
     * Key to move left.
     */
    MOVE_LEFT(Input.Keys.LEFT, "MoveLeft"),
    /**
     * Key to pick up items.
     */
    PICK_UP(Input.Keys.E, "PickUp"),
    /**
     * Key to switch weapon.
     */
    SWITCH_WEAPON(Input.Keys.W, "SwitchWeapon"),
    /**
     * Key to use a potion.
     */
    USE_POTION(Input.Keys.F, "UsePotion"),
    /**
     * Key to use skill: Increases Speed
     */
    INCREASES_SPEED(Input.Keys.NUM_1, "IncreasesSpeed");

    private int key;
    private final String name;

    /**
     * Constructor for the KeyBindings enum.
     * 
     * @param key  key associated to the action
     * @param name name associated to the action
     */
    KeyBindings(final int key, final String name) {
        this.key = key;
        this.name = name;
    }

    /**
     * @return the key associated to the action
     */
    public int getKey() {
        return this.key;
    }

    /**
     * @return the name of the associated action
     */
    public String getName() {
        return this.name;
    }

    /**
     * Method used to change the KeyBindings, through the setting menu.
     * 
     * @param key the new key associated to the action
     */
    // TODO: write the edited key in a json file.
    public void changeKey(final int key) {
        this.key = key;
    }
}
