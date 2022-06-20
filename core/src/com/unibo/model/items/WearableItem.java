package com.unibo.model.items;

import java.util.Optional;

import com.unibo.model.Character;
import com.unibo.model.Hero;
import com.unibo.util.Position;

/**
 * 
 * Class to model a wearable item, like a ring, an amulet or an armor that gives some buff.
 *
 */
public final class WearableItem extends Item {

    /**
     * Constant that determinates how much buff to give.
     */
    protected static final int DIVISOR = 4;

    private final Character pg;
    private final Optional<Double> health;
    private final Optional<Double> power;
    private final Optional<Integer> exp;

    /**
     * 
     * @param name
     * @param id
     * @param pg
     * @param health
     * @param power
     * @param exp
     */
    private WearableItem(final String name, final String id, final Character pg,
            final Optional<Double> health, final Optional<Double> power, final Optional<Integer> exp) {
        super(name, id);
        this.pg = pg;
        this.health = health;
        this.power = power;
        this.exp = exp;
    }

    /**
     * The selected pg wear the item getting its buff.
     * 
     */
    public void wear() {
        if (this.health.isPresent()) {
            this.pg.setMaxHp(this.pg.getMaxHp() + (int) (this.pg.getMaxHp() * this.health.get()));
        }
        if (this.power.isPresent()) {
            final Weapon weapon = this.pg.getCurrentWeapon();
            weapon.applyDamageMod(weapon.getDamage() + (int) (weapon.getDamage() * this.power.get()));
        }
        if (this.exp.isPresent()) {
            ((Hero) this.pg).addExp(this.exp.get());
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
     *  Class to build a WearableItem.
     *
     */
    public static class Builder {

        private String name;
        private String id;
        private Character pg;
        private Optional<Double> health = Optional.empty();
        private Optional<Double> power = Optional.empty();
        private Optional<Integer> exp = Optional.empty();

        /**
         * 
         * @param name
         * @param id
         * @param pg the player that gets the buff
         */
        public Builder(final String name, final String id, final Character pg) {
            this.name = name;
            this.id = id;
            this.pg = pg;
        }

        /**
         * 
         * @param mod 
         * @return
         */
        public Builder health(final double mod) {
            this.health = Optional.of(mod);
            return this;
        }

        public Builder power(final double mod) {
            this.power = Optional.of(mod);
            return this;
        }

        /**
         * 
         * @param e
         * @return this
         */
        public Builder exp(final int e) {
            if (e != 0) {
                this.exp = Optional.of(e);
            }
            return this;
        }

        /**
         * 
         * @return a WearableItem
         * @throws IllegalStateException
         */
        public WearableItem build() throws IllegalStateException {
            if (this.name == null || this.id == null
                    || (this.health.isEmpty() && this.power.isEmpty() && this.exp.isEmpty())) {
                throw new IllegalStateException("No attributes added");
            }
            return new WearableItem(this.name, this.id, this.pg, this.health, this.power, this.exp);
        }
    }
}
