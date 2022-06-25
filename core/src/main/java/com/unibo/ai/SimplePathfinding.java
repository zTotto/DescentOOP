package com.unibo.ai;

import com.unibo.maps.DescentMap;
import com.unibo.model.Character;
import com.unibo.model.Movement;
import com.unibo.util.Direction;
import com.unibo.util.Position;
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
     * Moves the mob given as a parameter according to a simple pathfinding
     * algorithm. The mob follows the player only if he's in sight.
     * 
     * @param mob   Mobview
     * @param level LevelView
     * @param map   Map
     */
    public void moveMob(final MobView mob, final LevelView level, final DescentMap map) {

        final float heroX = level.getHeroView().getCharacter().getPos().getxCoord();
        final float heroY = level.getHeroView().getCharacter().getPos().getyCoord();
        final float mobX = mob.getCharacter().getPos().getxCoord();
        final float mobY = mob.getCharacter().getPos().getyCoord();
        final Character mobChar = mob.getCharacter();
        final Position mobPos = mobChar.getPos();


        if (!LineOfSight.isHeroSeen(mob, level, map)) {
            simpleMove(mob);
        }

        else if (heroX > mobX) {
            moveMob(mob, Direction.RIGHT);
            if (heroY > mobY && !hasCharacterMoved(mobPos, mobChar.getPos())) {
                move(mob, Direction.UP);
                if (!hasCharacterMoved(mobPos, mobChar.getPos())) {
                    unstuckMob(mob, map);
                }
            } else if (heroY < mobY && !hasCharacterMoved(mobPos, mobChar.getPos())) {
                move(mob, Direction.DOWN);
                if (!hasCharacterMoved(mobPos, mobChar.getPos())) {
                    unstuckMob(mob, map);
                }
            } else {
                unstuckMob(mob, map);
            }
        } else if (heroX < mobX) {
            move(mob, Direction.LEFT);
            if (heroY > mobY && !hasCharacterMoved(mobPos, mobChar.getPos())) {
                move(mob, Direction.UP);
                if (!hasCharacterMoved(mobPos, mobChar.getPos())) {
                    unstuckMob(mob, map);
                }
            } else if (heroY < mobY && !hasCharacterMoved(mobPos, mobChar.getPos())) {
                move(mob, Direction.DOWN);
                if (!hasCharacterMoved(mobPos, mobChar.getPos())) {
                    unstuckMob(mob, map);
                }
            } else {
                unstuckMob(mob, map);
            }
        } else {
            if (heroY > mobY) {
                move(mob, Direction.UP);
            } else if (heroY < mobY) {
                move(mob, Direction.DOWN);
            }
            if (!hasCharacterMoved(mobPos, mobChar.getPos())) {
                unstuckMob(mob, map);
            }
        }
    }
 
    /**
     * Method to move the mob when hero is not in sight.
     * @param mob Mobview of the mob.
     */
    private void simpleMove(MobView mob) {
        Movement move;
        if (mob.getMoveBuffer() <= 15) {
            mob.increaseMoveBuffer();
            ;
            move = new Movement(mob.getDir());
            move.executeCommand(mob);
            return;
        }
        Direction newDir = Pathfinding.randomDirection();
        move = new Movement(newDir);
        move.executeCommand(mob);
        mob.resetMoveBuffer();
    }

    /**
     * Getter for a specific layer in the TiledMap.
     * 
     * @param pos1 Starting position of a character
     * @param pos2 current positiong of a character
     * @return True if the 2 positions are different, False if they're equal
     */
    private Boolean hasCharacterMoved(final Position pos1, final Position pos2) {
        return pos1 != pos2;
    }

    /**
     * Method to move the mob in a certain direction.
     * 
     * @param mob Mobview of the mob we want to move
     * @param dir Direction in which to move it
     */
    private void move(final MobView mob, final Direction dir) {
        final Movement move = new Movement(dir);
        move.executeCommand(mob);
    }

    /**
     * Method that tries to move the mob in random directions until it's position
     * changes.
     * 
     * @param mob MobView of the mob that's stuck
     * @param map the DescentMap in which he is
     */
    private void unstuckMob(final MobView mob, final DescentMap map) {
        final Position startPos = new Position(mob.getCharacter().getPos());
        Position newPos;
        do {
            move(mob, Pathfinding.randomDirection());
            newPos = mob.getCharacter().getPos();
        } while (newPos == startPos);
    }
}
