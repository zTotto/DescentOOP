package com.unibo.model.items;

import com.unibo.util.Position;
import com.unibo.util.WeaponStats;

/**
 * 
 * Class to model a Weapon, a sub-class of Item.
 */
public class Weapon extends Item {

    private int damage;
    private int range;

    /**
     * Constructor for a weapon.
     * 
     * @param weapon from the enum
     * @param id     the weapon Id
     */
    public Weapon(final WeaponStats weapon, final String id) {
        super(weapon.getName(), id);
        this.damage = weapon.getDamage();
        this.range = weapon.getRange();
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
     * 
     * @param damageMod the modifier
     */
    public void applyDamageMod(final int damageMod) {
        this.damage += damageMod;
    }

    /**
     * Applies a buff/debuff to the weapon range.
     * 
     * @param rangeMod the modifier
     */
    public void applyRangeMod(final int rangeMod) {
        this.range += rangeMod;
    }

    /**
     * @return the weapon description
     */
    public String toString() {
        String msg = "";
        msg += this.getName() + ", Damage: " + this.getDamage() + ", Range: " + this.getRange();
        return msg;
    }

    @Override
    public Weapon setPos(final Position p) {
        if (this.isPosNull()) {
            this.resetPos();
        }
        this.getPos().setPosition(p);
        return this;
    }
}
