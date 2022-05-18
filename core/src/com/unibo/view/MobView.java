package com.unibo.view;

import com.unibo.model.Mob;

/**
 * Mob's view class.
 */
public class MobView extends CharacterView {

    /**
     * Constructor for the Mob view.
     * 
     * @param mob the mob model
     */
    public MobView(final Mob mob) {
        super(mob, "characters/" + mob.getName() + ".png", "audio/sounds/Hadouken.mp3"); // TODO add sounds for mobs
    }

    /**
     * TO DO (pathfinding algorithm).
     */
    @Override
    public void move() {
    }

    @Override
    public void selfAttack() {
        // TODO Auto-generated method stub
    }

}
