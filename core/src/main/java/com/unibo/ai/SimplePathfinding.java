package com.unibo.ai;

import com.badlogic.gdx.Gdx;
import com.unibo.maps.DescentMap;
import com.unibo.util.Direction;
import com.unibo.view.CharacterView;
import com.unibo.view.LevelView;
import com.unibo.view.MobView;

/**
 * Implements pathfinding interface with a very simple pathfinding algorithm
 */
public class SimplePathfinding implements Pathfinding {

    public SimplePathfinding() {
        super();
    }

    /**
     * Moves the mob given as a parameter according to a simple pathfinding algorithm.
     * @param mob   Mobview
     * @param level LevelView
     * @param map   Map
     */
    public void moveMob(final MobView mob, final LevelView level, final DescentMap map) {
    	
    	CharacterView hero = level.getHeroView();
        final float heroX = level.getHeroView().getCharacter().getPos().getxCoord();
        final float heroY = level.getHeroView().getCharacter().getPos().getyCoord();
        final float mobX = mob.getCharacter().getPos().getxCoord();
        final float mobY = mob.getCharacter().getPos().getyCoord();
        final float delta = Gdx.graphics.getDeltaTime();
        final float deltaMovement = mob.getCharacter().getSpeed() * delta;

        if (heroX > mobX && map.validMovement(mob, mobX+deltaMovement, mobY) && !isAlligned(mob, hero)){
        	updatePosition(mob, mobX+deltaMovement, mobY);
        	mob.setDir(Direction.RIGHT);
        }
        else if (heroX < mobX && map.validMovement(mob, mobX-deltaMovement, mobY) && !isAlligned(mob, hero)) {
        	updatePosition(mob, mobX-deltaMovement, mobY);
        	mob.setDir(Direction.LEFT);
        }
        else if (heroY > mobY && map.validMovement(mob, mobX, mobY+deltaMovement)) {
        	updatePosition(mob, mobX, mobY+deltaMovement);
        	mob.setDir(Direction.UP);
        }
        else if (heroY < mobY && map.validMovement(mob, mobX, mobY-deltaMovement)) {
        	updatePosition(mob, mobX, mobY-deltaMovement);
        	mob.setDir(Direction.DOWN);
        }
       
    }
    
    /**
     * Moves the mob given as a parameter according to a simple pathfinding algorithm.
     * @param mob   Mobview of the mob
     * @param newX  the mob's x coordinate after he moved
     * @param newY  the mob's y coordinate after he moved
     */
    private void updatePosition(final MobView mob,float newX, float newY){
    	mob.getCharacter().setPos(newX, newY);
    }
    
    /**
     * Checks if a character is vertically alligned with
     * another character accounting for their hitboxes
     * @param CharacterView of the character we want to check
     * @param CharacterView of the character we want to check against
     * @return True if the first character is vertically allined with the second, false otherwise
     */
    private boolean isAlligned(CharacterView mobView, CharacterView heroView) {
    	float heroX = heroView.getCharacter().getPos().getxCoord();
    	float mobX = mobView.getCharacter().getPos().getxCoord();
    	float hitbox = heroView.getWidth();
    	return (heroX-hitbox < mobX && mobX < heroX+hitbox);
    }

}
