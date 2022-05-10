package com.unibo.keybindings;

import com.unibo.view.CharacterView;
import com.unibo.model.Character;

public class SpeedUpSkill extends Skill {
    private final int initialSpeed;
    private final int addedSpeed;
    
    public SpeedUpSkill(final int manaCost, final int initialSpeed, final double addedSpeed) {
        super(manaCost);
        this.initialSpeed = initialSpeed;
        this.addedSpeed = (int) addedSpeed;
    }
    
    @Override
    public void executeCommand(final CharacterView characterView) {
        final Character character = characterView.getCharacter();
        boolean isSkillUsed = true;
        
        if (character.getCurrentMana() >= this.getManaCost() && character.getSpeed() <= this.addedSpeed + this.initialSpeed) {
            isSkillUsed = character.increaseSpeed((int) addedSpeed);
        } else {
            character.setSpeed(initialSpeed);
        }
        
        if (isSkillUsed) {
            character.decreaseCurrentMana(this.getManaCost());
        }
    }
}
