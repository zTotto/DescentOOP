package com.unibo.util;

import java.util.ArrayList;
import java.util.List;

import com.unibo.maps.Map;
import com.unibo.model.Character;
import com.unibo.model.Movement;
import com.unibo.view.LevelView;
import com.unibo.view.MobView;

public final class Pathfinding {

	public static void A(MobView mob, LevelView level, Map map) {
		
		Movement move;
		final int heroX = level.getHeroView().getCharacter().getPos().getxCoord();
		final int heroY = level.getHeroView().getCharacter().getPos().getyCoord();
		final int mobX = mob.getCharacter().getPos().getxCoord();
		final int mobY = mob.getCharacter().getPos().getyCoord();
		final Character mobChar = mob.getCharacter();
		final Position mobPos = mobChar.getPos();
		
	    	
	    	if (heroX > mobX) {
	    		moveMob(mob, Direction.RIGHT);
	    		if (heroY > mobY && !hasCharacterMoved(mobPos, mobChar.getPos())) {
	    			moveMob(mob, Direction.UP);
				}
	    		else if  (heroY < mobY && !hasCharacterMoved(mobPos, mobChar.getPos())) {
	    			moveMob(mob, Direction.DOWN);
	    		}   		
	    		else {
	    			moveMob(mob, AI.randomDirection(mob, map));
	    		}
	    	}
	    	
	    	else if (heroX < mobX) {
	    		moveMob(mob, Direction.LEFT);
	    		if (heroY > mobY && !hasCharacterMoved(mobPos, mobChar.getPos())) {
	    			moveMob(mob, Direction.UP);
				}
	    		else if  (heroY < mobY && !hasCharacterMoved(mobPos, mobChar.getPos())) {
	    			moveMob(mob, Direction.DOWN);
	    		}   		
	    		else {
	    			moveMob(mob, AI.randomDirection(mob, map));
	    		}
	    	}
	    	
	    	else {
	    		if (heroY > mobY) {
	    			moveMob(mob, Direction.UP);
				}
	    		else if  (heroY < mobY && !hasCharacterMoved(mobPos, mobChar.getPos())) {
	    			moveMob(mob, Direction.DOWN);
	    		}   		
	    		else {	
	    			moveMob(mob, AI.randomDirection(mob, map));
	    		}
	    	}
		}		
		
	

	private static Boolean mobCollides(MobView mob, LevelView level) {
		final List<MobView> mobs = new ArrayList<>(level.getMobTextures());
		mobs.remove(mob);
		for (MobView mobView : mobs) {
			if (mob.getCharRect().overlaps(mobView.getCharRect())) {
				return true;
				}
			}
		return false;
	}
	
	private static Boolean hasCharacterMoved(Position pos1, Position pos2) {
		return (pos1 == pos2);
	}
	
	private static void moveMob (MobView mob, Direction dir) {
		final Movement move = new Movement(dir);
		move.executeCommand(mob);
	}
}