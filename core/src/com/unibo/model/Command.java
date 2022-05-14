package com.unibo.model;

import com.unibo.view.CharacterView;

/**
 * Interface of a Command.
 *
 */
public interface Command {

    /**
     * Execute a command on a specific character.
     * 
     * @param characterView the character view on which the command will be executed
     */
    void executeCommand(CharacterView characterView);
}
