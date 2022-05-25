package com.unibo.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * 
 * Class that manages game levels.
 *
 */
public final class LevelsList {

    private static final int NUM_LEVELS = 3;
    private final List<Level> levels = new LinkedList<>();
    private int counter;

    /**
     * Constructor that creates 3 empty levels.
     */
    public LevelsList() {
        this.counter = 0;
        for (int i = 0; i < NUM_LEVELS; i++) {
            this.levels.add(new Level());
        }
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
        return this.levels.size() > this.counter + 1;
    }

    /**
     * 
     * @return the next level if there is one, NoSuchElementException otherwise
     */
    public Level getNextLevel() {
        if (this.hasNextLevel()) {
            this.counter++;
            return this.levels.get(this.counter);
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
