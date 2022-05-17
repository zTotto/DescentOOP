package com.unibo.model;

import com.unibo.model.items.ConsumableItem;
import com.unibo.model.items.Weapon;
import com.unibo.util.MobStats;

/**
 * A class to model a mob.
 */
@SuppressWarnings("PMD.MissingSerialVersionUID")
public class Mob extends Character {

    private final String name;

    /**
     * Constructor for the mob.
     * 
     * @param modType        type of the created mob
     * @param startingWeapon starting weapon of the created mob
     */
    public Mob(final MobStats modType, final Weapon startingWeapon) {
        super(modType.getHp(), modType.getSpeed(), startingWeapon, modType.getMana());
        this.name = modType.getName();
    }

    /**
     * {@inheritDoc}
     */
    public String getName() {
        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return "\nName: " + this.getName() + ", Current HP: " + this.getCurrentHp() + ", Weapon: " + this.getWeapons();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void useItem(final ConsumableItem item) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean pickUpfromLevel(final Level lvl) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRange() {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRange(final int range) {
    }
}
