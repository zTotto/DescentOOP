package com.unibo.view;

import com.unibo.model.Level;

/**
 * A Class to link items and characters with their textures.
 */
public class LevelView {

    private Level level;

    /**
     * @param level Level from where to get the items and characters
     */
    public LevelView(final Level level) {
        this.level = level;
    }

    /**
     * Updates the level.
     * @param level New level
     */
    public void updateLevel(final Level level) {
    }
}
