package com.unibo.model;

/**
 * 
 * Class to model a Weapon, a sub-class of Item.
 */
public class Weapon extends Item {

    private int damage;
    private int range;

    /**
     * Constructor for a weapon.
     * @param name
     * @param damage
     * @param range
     * @param id the weapon Id
     */
    public Weapon(final String name, final int damage, final int range, final String id) {
        super(name, id);
        this.damage = damage;
        this.range = range;
    }

    /**
     * 
     * @return the weapon damage.
     */
    public int getDamage() {
        return damage;
    }

    /**
     * 
     * @return the weapon range.
     */
    public int getRange() {
        return range;
    }

    /**
     * Applies a buff/debuff to the weapon damage.
     * @param damageMod the modifier
     */
    public void applyDamageMod(final int damageMod) {
        this.damage += damageMod;
    }

    /**
     * Applies a buff/debuff to the weapon range.
     * @param rangeMod the modifier
     */
    public void applyRangeMod(final int rangeMod) {
        this.range += rangeMod;
    }
}
