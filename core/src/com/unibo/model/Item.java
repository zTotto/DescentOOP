package com.unibo.model;

import com.unibo.util.Position;

/**
 * 
 * Basic class modeling a generic Item.
 */
public abstract class Item {

    private final String name;
    private final String id;
    private Position pos;

    /**
     * Constructor to build an item.
     * 
     * @param name The item name.
     * @param id   The item ID.
     */
    public Item(final String name, final String id) {
        this.name = name;
        this.id = id;
    }

    /**
     * 
     * @return the item name.
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @return the item ID.
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @return the item position.
     */
    public Position getPos() {
        return new Position(pos.getxCoord(), pos.getyCoord());
    }

    /**
     * Sets the item position to the specified position.
     * 
     * @param p
     */
    public void setPos(final Position p) {
        this.pos = p;
    }

    /**
     * @return the item name.
     */
    @Override
    public String toString() {
        return this.name;
    }
}
