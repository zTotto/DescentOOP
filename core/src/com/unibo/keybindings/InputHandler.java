package com.unibo.keybindings;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.badlogic.gdx.Gdx;
import com.unibo.model.Command;

/**
 * Class that handles the input, in order to give a Command object, referred to
 * the action the player want to execute (based on what he pressed).
 *
 */
public class InputHandler {

    private final Map<String, Command> commands = new HashMap<>();

    /**
     * Add a new command on the list.
     * 
     * @param key        the keyBinding for the new command
     * @param newCommand the new command we want to add
     * @return the input handler, allowing the method to be reused
     */
    public InputHandler addCommand(final KeyBindings key, final Command newCommand) {
        this.commands.put(key.getName(), newCommand);
        return this;
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
        if (key.getName().contains("Move") || key.getName().contains("Hold")) {
            return Gdx.input.isKeyPressed(key.getKey()) ? Optional.ofNullable(this.commands.get(key.getName()))
                    : Optional.empty();
        }

        return Gdx.input.isKeyJustPressed(key.getKey()) ? Optional.ofNullable(this.commands.get(key.getName()))
                : Optional.empty();
    }
}
