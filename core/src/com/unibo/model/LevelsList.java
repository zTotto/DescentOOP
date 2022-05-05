package com.unibo.model;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * Class that menages the levels.
 *
 */
public final class LevelsList {

    private static final int NUM_LEVELS = 3;
    private final List<Level> levels = new LinkedList<>();
    private int counter;

    public LevelsList() {
        this.counter = 0;
        for (int i = 0; i < NUM_LEVELS; i++) {
            this.levels.add(new Level());
        }
    }

    public Level getCurrentLevel() {
        return levels.get(counter);
    }

    public boolean hasNextLevel() {
        return levels.size() > counter;
    }

    public Level getNextLevel() {
        counter++;
        return levels.get(counter);
    }

    public boolean isGameOver() {
        return levels.isEmpty();
    }

}
