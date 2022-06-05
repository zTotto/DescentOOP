package com.unibo.util;

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
    
    public Position(Position pos) {
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
