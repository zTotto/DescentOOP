package com.unibo.keybindings;

import com.unibo.view.CharacterView;
import com.unibo.model.Character;

/**
 * SpeedUpSkill class
 */
public class SpeedUpSkill extends Skill {
    private final int initialSpeed;
    private final int addedSpeed;
    
    /**
     * Constructor for the SpeedUp skill
     * 
     * @param manaCost value of how much mana will the skill cost every time the skill will be executed
     * @param initialSpeed the initial speed of the character
     * @param addedSpeed the value of speed to add to the hero in order to speed it up
     */
    public SpeedUpSkill(final int manaCost, final int initialSpeed, final double addedSpeed) {
        super(manaCost);
        this.initialSpeed = initialSpeed;
        this.addedSpeed = (int) addedSpeed;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void executeCommand(final CharacterView characterView) {
        final Character character = characterView.getCharacter();
        
        if (character.getCurrentMana() >= this.getManaCost() && character.getSpeed() <= this.addedSpeed + this.initialSpeed) {
            character.increaseSpeed((int) this.addedSpeed);
            character.decreaseCurrentMana(this.getManaCost());
        } else {
            character.setSpeed(initialSpeed);
        }
    }
}
