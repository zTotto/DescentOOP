package com.unibo.model;

import com.unibo.model.items.ConsumableItem;
import com.unibo.model.items.Weapon;
import com.unibo.util.MobStats;
import com.unibo.util.Position;
import com.unibo.util.WeaponStats;

/**
 * A class to model a mob.
 */
@SuppressWarnings("PMD.MissingSerialVersionUID")
public class Mob extends Character {

    private final String name;

    /**
     * Constructor for the mob.
     * 
     * @param mobType      type of the created mob
     * @param weaponDamage additional damage to apply to this mob (default damage is
     *                     4);
     */
    public Mob(final MobStats mobType, final int weaponDamage) {
        super(mobType.getHp(), mobType.getSpeed(), new Weapon(WeaponStats.FISTS, "0"), mobType.getMana());
        this.getCurrentWeapon().applyDamageMod(weaponDamage);
        this.name = mobType.getName();
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

    @Override
    public Mob setPos(final Position p) {
        if (!isDead()) {
            getPos().setxCoord(p.getxCoord());
            getPos().setyCoord(p.getyCoord());
        }
        return this;
    }

    @Override
    public Mob setPos(final int xCoord, final int yCoord) {
        return this.setPos(new Position(xCoord, yCoord));
    }
}
