package com.unibo.view;

import java.util.List;
import java.util.Random;

import com.unibo.model.Mob;
import com.unibo.model.Movement;
import com.unibo.util.Direction;
import com.unibo.util.PathfindingAlgorithm;

/**
 * Mob's view class.
 */
public class MobView extends CharacterView {
	
	private int moveBuffer = 0;
	private Direction lastDir = Direction.UP;
	private Boolean heroSight = false;

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
    public void moveAI(LevelView level) {
    	if (!heroSight) {
	    	final Movement move;
	    	if (moveBuffer <= 15) {
	    		moveBuffer++;
	    		move = new Movement(lastDir);
	    		move.executeCommand(this);
	    		return;
	    	}
	    	final Direction[] directions = Direction.values();
	    	final Random random = new Random();
	    	Direction newDir = directions[random.nextInt(directions.length)];
	    	move = new Movement(newDir);
	    	move.executeCommand(this);
	    	lastDir = newDir;
	    	moveBuffer = 0;
	    	return;
	    	}
    	else {
    		List<MobView> mobs = level.getMobTextures();
    		mobs.remove(this);
    		for (MobView mobView : mobs) {
				if (this.getCharRect().overlaps(mobView.getCharRect())) {
					return;
				}
				else PathfindingAlgorithm.A(this, level);
			}
    	}
    }

    @Override
    public void selfAttack() {
        // TODO Auto-generated method stub
    }

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}

}
