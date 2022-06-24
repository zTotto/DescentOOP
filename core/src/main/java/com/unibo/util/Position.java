package com.unibo.util;

import java.util.Objects;

/**
 * 
 * A class to locate in space a certain entity.
 */
public class Position {

    private float x;
    private float y;

    /**
     * Empty constructor for a position.
     */
    public Position() {
        // Empty Constructor
    }

    /**
     * Constructor with x and y coordinates.
     * 
     * @param x coordinate
     * @param y coordinate
     */
    public Position(final float x, final float y) {
        this.setxCoord(x);
        this.setyCoord(y);
    }

    /**
     * Constructor with premade position.
     * @param pos
     */
    public Position(final Position pos) {
        this.setxCoord(pos.getxCoord());
        this.setyCoord(pos.getyCoord());
    }

    /**
     * Sets the position to the specified one.
     * 
     * @param p new position
     */
    public void setPosition(final Position p) {
        this.x = p.getxCoord();
        this.y = p.getyCoord();
    }

    /**
     * 
     * @return the y coordinate
     */
    public float getyCoord() {
        return y;
    }

    /**
     * 
     * @return the x coordinate
     */
    public float getxCoord() {
        return x;
    }

    /**
     * Sets the y coordinate to the specified one.
     * 
     * @param yCoord
     */
    public void setyCoord(final float yCoord) {
        this.y = yCoord;
    }

    /**
     * Sets the x coordinate to the specified one.
     * 
     * @param xCoord
     */
    public void setxCoord(final float xCoord) {
        this.x = xCoord;
    }

    /**
     * 
     * @return the position
     */
    public Position getPosition() {
        return new Position(x, y);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Position other = (Position) obj;
        return x == other.x && y == other.y;
    }

    /**
     * @return the position as a string.
     */
    public String toString() {
        return "X: " + x + ", Y: " + y;
    }
}
