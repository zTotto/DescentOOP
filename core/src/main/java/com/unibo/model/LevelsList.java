package com.unibo.model;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * 
 * Class that manages game levels.
 *
 */
public final class LevelsList {

    private final List<Level> levels;
    private int counter;

    /**
     * Constructor for a levels list.
     * 
     * @param levelsToAdd
     */
    public LevelsList(final List<Level> levelsToAdd) {
        this.counter = 0;
        this.levels = levelsToAdd;
    }

    /**
     * 
     * @return a list containing all the levels
     */
    public List<Level> getLevels() {
        return Collections.unmodifiableList(this.levels);
    }

    /**
     * 
     * @return the current level
     */
    public Level getCurrentLevel() {
        return this.levels.get(this.counter);
    }

    /**
     * 
     * @return true if there is at least another level
     */
    public boolean hasNextLevel() {
        return this.counter < this.levels.size() - 1;
    }

    /**
     * 
     * @return the next level if there is one
     * @throws NoSuchElementException if there are no more levels
     */
    public Level getNextLevel() throws NoSuchElementException {
        if (this.hasNextLevel()) {
            return this.levels.get(++this.counter);
        }
        throw new NoSuchElementException();
    }

    /**
     * 
     * @return true if there are no more levels
     */
    public boolean isGameOver() {
        return !this.hasNextLevel();
    }

    /**
     * 
     * @return a string describing the levels list
     */
    public String toString() {
        String s = "\nLevelList: ";
        if (!this.getLevels().isEmpty()) {
            s += "total levels = " + this.getLevels().size() + ", current level = "
                    + (this.getLevels().indexOf(this.getCurrentLevel()) + 1);
        } else {
            s += "no levels";
        }
        return s;
    }
}
