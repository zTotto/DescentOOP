package com.unibo.view;

import java.time.chrono.ThaiBuddhistEra;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.unibo.maps.Map;
import com.unibo.model.Character;
import com.unibo.model.Level;
import com.unibo.model.Mob;
import com.unibo.model.Movement;
import com.unibo.util.Direction;
import com.unibo.util.LineOfSight;
import com.unibo.util.Pathfinding;
import com.unibo.util.AI;

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
     * @param level 
     */
    public void moveAI(LevelView levelView, Level level) {
    	if (!LineOfSight.isHeroSeen(this, levelView, level.getMap().getFirst())) {
	    	final Movement move;
	    	if (moveBuffer <= 15) {
	    		moveBuffer++;
	    		move = new Movement(lastDir);
	    		move.executeCommand(this);
	    		return;
	    	}
	    	Direction newDir = AI.randomDirection(this, level.getMap().getFirst());
	    	move = new Movement(newDir);
	    	move.executeCommand(this);
	    	lastDir = newDir;
	    	moveBuffer = 0;
	    	return;
	    	}
    	else {
				Pathfinding.A(this, levelView, level.getMap().getFirst());
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
	
	public void update(LevelView level, Level currentLvl) {
		if (this.getCharacter().canHit(level.getHeroView().getCharacter())) {
			this.getCharacter().hitEnemy(level.getHeroView().getCharacter());
		}
		else {
			this.moveAI(level, currentLvl);
		}
	}
	
}
