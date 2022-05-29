package com.unibo.model.items;

import com.unibo.model.Character;
import com.unibo.util.Position;

/**
 * 
 * Item that increases maximum Health.
 *
 */
public class HealthRing extends WearableItem {

    /**
     * Constructor for a health ring.
     * 
     * @param name of the item
     * @param id of the item
     */
    public HealthRing(final String name, final String id) {
        super(name, id);
    }

    @Override
    public void wear(final Character pg) {
        pg.setMaxHp(pg.getMaxHp() + pg.getMaxHp() / DIVISOR);
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
