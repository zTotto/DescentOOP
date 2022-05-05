package com.unibo.model;

/**
 * 
 * A class to model the Hero.
 */
@SuppressWarnings("PMD.MissingSerialVersionUID")
public class Hero extends Character {

    private final String name;

    /**
     * Constructor for the Hero.
     * 
     * @param name
     * @param maxHp
     * @param speed
     * @param startingWeapon
     */
    public Hero(final String name, final int maxHp, final int speed, final Weapon startingWeapon) {
        super(maxHp, speed, startingWeapon);
        this.name = name;
    }

    /**
     * 
     * @return the Hero's name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the description of the hero.
     */
    public String toString() {
        return "\nName: " + this.getName() + ", Current HP: " + this.getCurrentHp() + ", Weapon: " + this.getWeapons();
    }
}
