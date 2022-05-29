package com.unibo.model.items;

import com.unibo.model.Character;

/**
 * 
 * Class to model a wearable item, like a ring, an amulet or an armor that gives some buff.
 *
 */
public abstract class WearableItem extends Item {

    /**
     * Constant that determinates how much buff to give.
     */
    protected static final int DIVISOR = 4;

    /**
     * Constructor for a wearable item.
     * 
     * @param name of the item
     * @param id of the item
     */
    public WearableItem(final String name, final String id) {
        super(name, id);
    }

    /**
     * The selected pg wear the item getting its buff.
     * 
     * @param pg the character that wear the item
     */
    public abstract void wear(Character pg);
}
