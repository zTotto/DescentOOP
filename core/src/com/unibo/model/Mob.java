package com.unibo.model;

import com.unibo.util.MobsStats;

public class Mob extends Character {

	private final String name;

    /**
     * Constructor for the Hero.
     * 
     * @param name
     * @param maxHp
     * @param speed
     * @param startingWeapon
     */
    public Mob(final MobsStats modType, final Weapon startingWeapon) {
        super(modType.getHp(), modType.getSpeed(), startingWeapon);
        this.name = modType.getName();
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
        String msg = "\nName: " + this.getName() + ", Current HP: " + this.getCurrentHp() + ", Weapon: " + this.getWeapons();
        return msg;
    }
}
