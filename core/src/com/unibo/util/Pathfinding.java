package com.unibo.util;

import com.unibo.maps.Map;
import com.unibo.model.Movement;
import com.unibo.view.LevelView;
import com.unibo.view.MobView;

public final class Pathfinding {

	public static void A(MobView mob, LevelView level, Map map) {
		if (map.validMovement(mob, mob.getCharacter().getPos().getxCoord(), mob.getCharacter().getPos().getyCoord()+1)) {
	    	Movement move = new Movement(Direction.DOWN);
	    	move.executeCommand(mob);
		}
		
	}
	
	
}
	