package com.unibo.key_bindings;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.badlogic.gdx.Gdx;

/**
 * Class that handles the input, in order to give a Command object, referred to
 * the action the player want to execute (based on what he pressed).
 *
 */
public class InputHandler {

    private final Map<String, Command> commands = new HashMap<>();

    /**
     * Constructor of the InputHandler.
     * 
     * @param attack       command that execute the attack action
     * @param pickUp       command that execute the pick up action
     * @param switchWeapon command that execute the weapon's switch action
     * @param moveUp       command that move up a character
     * @param moveRight    command that move right a character
     * @param moveDown     command that move down a character
     * @param moveLeft     command that move left a character
     * @param pause        command that pause the game
     * @param usePotion    command that uses a potion
     */
    public InputHandler(final Command attack, final Command pickUp, final Command switchWeapon, final Movement moveUp,
            final Movement moveRight, final Movement moveDown, final Movement moveLeft, final Command pause, final Command usePotion) {
        this.commands.put(KeyBindings.ATTACK.getName(), attack);
        this.commands.put(KeyBindings.PICK_UP.getName(), pickUp);
        this.commands.put(KeyBindings.SWITCH_WEAPON.getName(), switchWeapon);
        this.commands.put(KeyBindings.MOVE_UP.getName(), moveUp);
        this.commands.put(KeyBindings.MOVE_RIGHT.getName(), moveRight);
        this.commands.put(KeyBindings.MOVE_DOWN.getName(), moveDown);
        this.commands.put(KeyBindings.MOVE_LEFT.getName(), moveLeft);
        this.commands.put(KeyBindings.PAUSE.getName(), pause);
        this.commands.put(KeyBindings.USE_POTION.getName(), usePotion);
    }

    /**
     * Add a new command on the list.
     * 
     * @param key        the keyBinding for the new command
     * @param newCommand the new command we want to add
     */
    public void addCommand(final KeyBindings key, final Command newCommand) {
        this.commands.put(key.getName(), newCommand);
    }

    /**
     * Return what command to use based on the pressed key.
     * 
     * @param key the key that the user pressed (or better, the action the user want
     *            to perform)
     * @return an optional of the command related to the action the user want do
     *         perform
     */
    public Optional<Command> handleInput(final KeyBindings key) {
        if (key.getName().contains("Move")) {
            return Gdx.input.isKeyPressed(key.getKey()) ? Optional.ofNullable(this.commands.get(key.getName()))
                    : Optional.empty();
        }

        return Gdx.input.isKeyJustPressed(key.getKey()) ? Optional.ofNullable(this.commands.get(key.getName()))
                : Optional.empty();
    }
}
