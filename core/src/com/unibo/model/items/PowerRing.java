package com.unibo.model.items;

import com.unibo.model.Character;
import com.unibo.util.Position;

/**
 * 
 * Item that increases damage.
 *
 */
public class PowerRing extends WearableItem {

    /**
     * Constructor for a power ring.
     * 
     * @param name of the item
     * @param id of the item
     */
    public PowerRing(final String name, final String id) {
        super(name, id);
    }

    /**
     * Icrease the pg damage.
     * 
     * @param pg the character that wear the item
     */
    @Override
    public void wear(final Character pg) {
        final Weapon weapon = pg.getCurrentWeapon();
        weapon.applyDamageMod(weapon.getDamage() + weapon.getDamage() / DIVISOR);
    }

    @Override
    public Item setPos(final Position p) {
        if (this.isPosNull()) {
            this.resetPos();
        }
        this.getPos().setPosition(p);
        return this;
    }

}
