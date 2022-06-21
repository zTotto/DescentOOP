package com.unibo.util;

import java.util.Random;

import com.unibo.maps.DescentMap;
import com.unibo.view.MobView;

public final class AI {
	public static Direction randomDirection(MobView mob, DescentMap map) {
		final Direction[] directions = Direction.values();
    	final Random random = new Random();
    	Direction newDir = directions[random.nextInt(directions.length)];
    	
    	return newDir;
	}
}
