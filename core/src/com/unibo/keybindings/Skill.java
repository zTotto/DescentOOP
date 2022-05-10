package com.unibo.keybindings;

import com.unibo.view.CharacterView;

/**
 * Skill abstract class
 */
public abstract class Skill implements Command {
    
    private final int manaCost;
    
    /**
     * Constructor for a generic Skill
     * 
     * @param manaCost value of how much mana will the skill cost every time the skill will be executed
     */
    public Skill(final int manaCost) {
        this.manaCost = manaCost;
    }
    
    /**
     * @return the mana cost of the skill
     */
    public int getManaCost() {
        return this.manaCost;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void executeCommand(CharacterView character);
}
