package com.unibo.model.items;

import com.unibo.util.Position;

/**
 * 
 * Item that increases maximum Health.
 *
 */
public class HealthRing extends Item {

    /**
     * Constructor for a Health ring.
     * 
     * @param name
     * @param id of the item
     */
    public HealthRing(final String name, final String id) {
        super(name, id);
    }

    @Override
    public final HealthRing setPos(final Position p) {
        if (this.isPosNull()) {
            this.resetPos();
        }
        this.getPos().setPosition(p);
        return this;
    }

}
