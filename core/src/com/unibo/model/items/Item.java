package com.unibo.model.items;

import java.util.Objects;

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
        return this.pos;
    }

    /**
     * Sets the item position to the specified position.
     * 
     * @param p
     * @return the item
     */
    public abstract Item setPos(Position p);

    /**
     * Checks whether the item position is null.
     * 
     * @return true if the position is null
     */
    public Boolean isPosNull() {
        return this.pos == null;
    }

    /**
     * Resets the item position to [0, 0].
     */
    public void resetPos() {
        this.pos = new Position(0, 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
        return Objects.equals(id, other.id) && Objects.equals(name, other.name);
    }

    /**
     * @return the item name.
     */
    @Override
    public String toString() {
        return "\n" + this.name + " [" + this.pos + "]";
    }
}
