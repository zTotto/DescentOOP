package com.unibo.keybindings;

import com.unibo.view.CharacterView;

/**
 * Interface of a Command.
 *
 */
public interface Command {

    /**
     * Execute a command on a specific character.
     * 
     * @param character the character on which the command will be executed
     */
    void executeCommand(CharacterView character);
}
