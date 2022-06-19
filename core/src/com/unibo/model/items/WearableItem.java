package com.unibo.model.items;

import java.util.Optional;

import com.unibo.model.Character;
import com.unibo.util.Position;

/**
 * 
 * Class to model a wearable item, like a ring, an amulet or an armor that gives some buff.
 *
 */
public class WearableItem extends Item {

    /**
     * Constant that determinates how much buff to give.
     */
    protected static final int DIVISOR = 4;

    private final Character pg;
    private boolean health = false;
    private boolean power = false;
    private boolean mana = false;

    /**
     * 
     * @param name
     * @param id
     * @param pg
     * @param health
     * @param power
     * @param mana
     */
    private WearableItem(final String name, final String id, final Character pg,
            final boolean health, final boolean power, final boolean mana) {
        super(name, id);
        this.pg = pg;
        this.health = health;
        this.power = power;
        this.mana = mana;
    }

    /**
     * The selected pg wear the item getting its buff.
     * 
     */
    public void wear() {
        if (this.health) {
            this.pg.setMaxHp(this.pg.getMaxHp() + this.pg.getMaxHp() / DIVISOR);
        }
        if (this.power) {
            final Weapon weapon = this.pg.getCurrentWeapon();
            weapon.applyDamageMod(weapon.getDamage() + weapon.getDamage() / DIVISOR);
        }
        if (this.mana) {
            //pg.set
        }
    }

    @Override
    public Item setPos(final Position p) {
        if (this.isPosNull()) {
            this.resetPos();
        }
        this.getPos().setPosition(p);
        return this;
    }
    
    /**
     * 
     * @author fraca
     *
     */
    public static class Builder {

        private String name;
        private String id;
        private Character pg;
        private boolean health = false;
        private boolean power = false;
        private boolean mana = false;

        /**
         * 
         * @param name
         * @param id
         * @param pg
         */
        public Builder(final String name, final String id, final Character pg) {
            this.name = name;
            this.id = id;
            this.pg = pg;
        }

        public Builder health() {
            this.health = true;
            return this;
        }

        public Builder power() {
            this.power = true;
            return this;
        }

        public Builder mana() {
            this.mana = true;
            return this;
        }

        /**
         * 
         * @return a WearableItem
         * @throws IllegalStateException
         */
        public WearableItem build() throws IllegalStateException {
            if (this.name == null || this.id == null
                    || (!this.health && !this.power && !this.mana)) {
                throw new IllegalStateException();
            }
            return new WearableItem(this.name, this.id, this.pg, this.health, this.power, this.mana);
        }
    }
}
