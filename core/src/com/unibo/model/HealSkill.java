package com.unibo.model;

public class HealSkill extends Skill {

    private final int hp;
    
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
