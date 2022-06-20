package com.unibo.util;

import java.util.ArrayList;
import java.util.List;

import com.unibo.maps.DescentMap;
import com.unibo.model.Character;
import com.unibo.model.Mob;
import com.unibo.model.Movement;
import com.unibo.view.LevelView;
import com.unibo.view.MobView;

public final class Pathfinding {
	
	
	 /**
     * Moves the mob given as a parameter according to a simple
     * pathfinding algorithm.
     * The Algorithm takes care of line of sight and stuck mobs.
     * 
     * @param Mobview, LevelView, Map
     */
	public static void moveMob(MobView mob, LevelView level, DescentMap map) {
		
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
	    			if(!hasCharacterMoved(mobPos, mobChar.getPos())){
	    				unstuckMob(mob, map);
	    			}
				}
	    		else if  (heroY < mobY && !hasCharacterMoved(mobPos, mobChar.getPos())) {
	    			moveMob(mob, Direction.DOWN);
	    			if(!hasCharacterMoved(mobPos, mobChar.getPos())){
	    				unstuckMob(mob, map);
	    			}
	    		}   		
	    		else {
	    			unstuckMob(mob, map);
	    		}
	    	}
	    	
	    	else if (heroX < mobX) {
	    		moveMob(mob, Direction.LEFT);
	    		if (heroY > mobY && !hasCharacterMoved(mobPos, mobChar.getPos())) {
	    			moveMob(mob, Direction.UP);
	    			if(!hasCharacterMoved(mobPos, mobChar.getPos())){
	    				unstuckMob(mob, map);
	    			}
				}
	    		else if  (heroY < mobY && !hasCharacterMoved(mobPos, mobChar.getPos())) {
	    			moveMob(mob, Direction.DOWN);
	    			if(!hasCharacterMoved(mobPos, mobChar.getPos())){
	    				unstuckMob(mob, map);
	    			}
	    		}   		
	    		else {
	    			unstuckMob(mob, map);
	    		}
	    	}
	    	
	    	else {
	    		if (heroY > mobY) {
	    			moveMob(mob, Direction.UP);
				}
	    		else if  (heroY < mobY) {
	    			moveMob(mob, Direction.DOWN);
	    		}
	    		if (!hasCharacterMoved(mobPos, mobChar.getPos())) {
	    			unstuckMob(mob, map);
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
	
	
	 /**
     * Getter for a specific layer in the TiledMap.
     * 
     * @param Starting position of a character, current positiong of a character
     * @return True if the 2 positions are different, False if they're equal 
     */
	private static Boolean hasCharacterMoved(Position pos1, Position pos2) {
		return (pos1 != pos2);
	}
	
	
	 /**
     * Method to move the mob in a certain direction
     * 
     * @param The Mobview of the mob we want to move, Direction in which to move it
     */
	private static void moveMob (MobView mob, Direction dir) {
		final Movement move = new Movement(dir);
		move.executeCommand(mob);
	}
	
	 /**
     * Method that tries to move the mob in random directions
     * until it's position changes
     * 
     * @param Mobview of the mob that's stuck, the DescentMap in which he is
     */
	private static void unstuckMob(MobView mob, DescentMap map) {
     	final Position startPos = new Position(mob.getCharacter().getPos());
		Position newPos;
		do {
			moveMob(mob, AI.randomDirection(mob, map));
			newPos = mob.getCharacter().getPos();
		} while (newPos == startPos);
	}

}