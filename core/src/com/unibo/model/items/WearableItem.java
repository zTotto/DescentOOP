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
public class WearableItem extends Item {

    private final Optional<Double> health;
    private final Optional<Double> power;
    private final Optional<Integer> exp;

    /**
     * Constructor for a WearableItem.
     * 
     * @param name of the item
     * @param id of the item
     * @param health
     * @param power
     * @param exp
     */
    private WearableItem(final String name, final String id, final Optional<Double> health,
            final Optional<Double> power, final Optional<Integer> exp) {
        super(name, id);
        this.health = health;
        this.power = power;
        this.exp = exp;
    }

    /**
     * The selected pg wear the item getting its buffs.
     * 
     * @param pg the player that gets the buffs
     */
    public void wear(final Character pg) {
        if (this.health.isPresent()) {
            pg.setMaxHp(pg.getMaxHp() + (int) (pg.getMaxHp() * this.health.get()));
        }
        if (this.power.isPresent()) {
            final Weapon weapon = pg.getCurrentWeapon();
            weapon.applyDamageMod(weapon.getDamage() + (int) (weapon.getDamage() * this.power.get()));
        }
        if (this.exp.isPresent()) {
            ((Hero) pg).addExp(this.exp.get());
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
        private Optional<Double> health = Optional.empty();
        private Optional<Double> power = Optional.empty();
        private Optional<Integer> exp = Optional.empty();

        /**
         * Constructor for the WearableItem Builder.
         * 
         * @param name of the item
         * @param id of the item
         */
        public Builder(final String name, final String id) {
            this.name = name;
            this.id = id;
        }

        /**
         * Increase the pg maximum health.
         * 
         * @param mod to apply to the pg to increase maximum life ranging from 0 to 1 excluded
         * @return this
         */
        public Builder health(final double mod) {
            this.health = Optional.of(mod).filter(k -> k > 0 && k < 1);
            return this;
        }

        /**
         * Increase the damage of the player's current weapon.
         * 
         * @param mod to apply to the pg to icrease the damage ranging from 0 to 1 excluded
         * @return this
         */
        public Builder power(final double mod) {
            this.power = Optional.of(mod).filter(k -> k > 0 && k < 1);
            return this;
        }

        /**
         * Add experience to the pg.
         * 
         * @param e experience to add to the pg ranging from 1 to 999
         * @return this
         */
        public Builder exp(final int e) {
            this.exp = Optional.of(e).filter(k -> k > 0 && k < 1000);
            return this;
        }

        /**
         * Build a WearableItem with the chosen attributes.
         * 
         * @return a WearableItem
         * @throws IllegalStateException if no attributes are added
         */
        public WearableItem build() throws IllegalStateException {
            if (this.name == null || this.id == null
                    || (this.health.isEmpty() && this.power.isEmpty() && this.exp.isEmpty())) {
                throw new IllegalStateException("No attributes added");
            }
            return new WearableItem(this.name, this.id, this.health, this.power, this.exp);
        }
    }
}
