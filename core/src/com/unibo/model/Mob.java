package com.unibo.model;

import com.unibo.util.MobsStats;

/**
 * A class to model a mob
 */
public class Mob extends Character {

	private final String name;

    /**
     * Constructor for the mob.
     * 
     * @param modType
     * 			type of the created mob
     * @param startingWeapon
     * 			starting weapon of the created mob
     */
    public Mob(final MobsStats modType, final Weapon startingWeapon) {
        super(modType.getHp(), modType.getSpeed(), startingWeapon);
        this.name = modType.getName();
    }

    /**
     * 
     * @return this mob's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return the description of this mob.
     */
    public String toString() {
        String msg = "\nName: " + this.getName() + ", Current HP: " + this.getCurrentHp() + ", Weapon: " + this.getWeapons();
        return msg;
    }    
}
