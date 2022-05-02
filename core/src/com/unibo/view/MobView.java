package com.unibo.view;

import com.unibo.model.Mob;

/**
 * Mob's view class.
 */
public class MobView extends CharacterView {

    /**
     * Constructor for the Mob view.
     * 
     * @param mob             the mob model
     * @param path            path of the mob animation
     * @param attackSoundPath path of the mob attack sound
     */
    public MobView(final Mob mob, final String path, final String attackSoundPath) {
        super(mob, path, attackSoundPath);
    }

    /**
     * TO DO (pathfinding algorithm).
     */
    @Override
    public void move() {
    }

}
