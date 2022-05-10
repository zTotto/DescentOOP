package com.unibo.keybindings;

import com.unibo.view.CharacterView;

public abstract class Skill implements Command {
    
    private final int manaCost;
    
    public Skill(final int manaCost) {
        this.manaCost = manaCost;
    }
    
    public int getManaCost() {
        return this.manaCost;
    }

    @Override
    public abstract void executeCommand(CharacterView character);
}
