package com.unibo.ai;

import java.util.Random;

import com.unibo.maps.DescentMap;
import com.unibo.util.Direction;
import com.unibo.view.LevelView;
import com.unibo.view.MobView;

/**
 * An interface for pathfinding algorithms
 */
public interface Pathfinding {
	
	/**
	 * Moves the mob according to the implemented pathfinding algorithm
	 * @param mob	{@link  com.unibo.view.MobView} of the mob
	 * @param level	{@link  com.unibo.view.LevelView} of the mob
	 * @param map    the {@link  com.unibo.maps.DescentMap} in which to mob is
	 */
	 void moveMob(final MobView mob, final LevelView level, final DescentMap map);
	 
	 /**
	  * @return a random direction
	  */
	 public static Direction randomDirection() {
	        final Direction[] directions = Direction.values();
	        final Random random = new Random();
	        return directions[random.nextInt(directions.length)];
	    }
}
