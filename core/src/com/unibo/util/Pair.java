package com.unibo.util;

import java.util.Objects;

/**
 * Simple pair utility class.
 *
 * @param <X> First generic
 * @param <Y> Second generic
 */
public class Pair<X, Y> {

    private X first;
    private Y second;

    /**
     * Empty constructor for the pair.
     */
    public Pair() {
        // Empty Constructor
    }

    /**
     * Constructor for the pair.
     * 
     * @param first  First generic
     * @param second First generic
     */
    public Pair(final X first, final Y second) {
        this.first = first;
        this.second = second;
    }

    /**
     * First object getter.
     *
     * @return the first object of the pair
     */
    public X getFirst() {
        return first;
    }

    /**
     * Second object getter.
     * 
     * @return the second object of the pair
     */
    public Y getSecond() {
        return second;
    }

    /**
     * First object setter.
     * 
     * @param first sets the first object of the pair
     */
    public void setFirst(final X first) {
        this.first = first;
    }

    /**
     * Second object setter.
     * 
     * @param second sets the second object of the pair
     */
    public void setSecond(final Y second) {
        this.second = second;
    }

    /**
     * A function that checks whether the item is contained in the Pair.
     * 
     * @param obj
     * @return true if it's contained.
     */
    public Boolean contains(final Object obj) {
        if (this.first.equals(obj) || this.second.equals(obj)) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

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
        Pair<X, Y> other = (Pair<X, Y>) obj;
        return Objects.equals(first, other.first) && Objects.equals(second, other.second);
    }
}
