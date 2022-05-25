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
     * Constructor for a level list.
     * 
     * @param levelsToAdd
     */
    public LevelsList(final List<Level> levelsToAdd) {
        this.counter = 0;
        levels = levelsToAdd;
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
        return counter < levels.size() - 1;
    }

    /**
     * 
     * @return the next level if there is one, NoSuchElementException otherwise
     */
    public Level getNextLevel() {
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

}
