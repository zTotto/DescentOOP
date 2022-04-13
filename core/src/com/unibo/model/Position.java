package com.unibo.model;

import java.util.Objects;

/**
 * 
 * A class to locate in space a certain entity.
 */
public class Position {

    private int x;
    private int y;

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
    public Position(final int x, final int y) {
        this.setxCoord(x);
        this.setyCoord(y);
    }

    /**
     * 
     * @return the y coordinate
     */
    public int getyCoord() {
        return y;
    }

    /**
     * 
     * @return the x coordinate
     */
    public int getxCoord() {
        return x;
    }

    /**
     * Sets the y coordinate to the specified one.
     * 
     * @param yCoord
     */
    public void setyCoord(final int yCoord) {
        this.y = yCoord;
    }

    /**
     * Sets the x coordinate to the specified one.
     * 
     * @param xCoord
     */
    public void setxCoord(final int xCoord) {
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
