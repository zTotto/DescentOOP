package com.unibo.model;

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

    @Override
    protected boolean executeSkill(final Character character) {
        if (character.getSpeed() < this.addedSpeed + this.initialSpeed) {
            return character.increaseSpeed(this.addedSpeed);
        }
        return false;
    }

    @Override
    protected void resetInitialState(final Character character) {
        character.setSpeed(initialSpeed);
    }
}
