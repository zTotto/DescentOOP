package com.unibo.model;

/**
 * Class for the auto heal skill.
 */
public class HealSkill extends Skill {

    private final int hp;

    /**
     * @param manaCost Cost of the skill
     * @param hp       Health point gained when using the skill
     */
    public HealSkill(final int manaCost, final int hp) {
        super(manaCost);
        this.hp = hp;
    }

    @Override
    protected boolean executeSkill(final Character character) {
        if (character.getCurrentHp() < character.getMaxHp()) {
            return character.heal(this.hp);
        }
        return false;
    }

    @Override
    protected void resetInitialState(final Character character) {

    }
}
