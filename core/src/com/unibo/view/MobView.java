package com.unibo.view;

import com.unibo.audio.AudioManager;
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
    private float attackTime = 0;
    private static final String DEFAULT_ATTACK_SOUND_PATH = "audio/sounds/KnifeStab.mp3";

    /**
     * Constructor for the Mob view.
     * 
     * @param mob the mob model
     */
    public MobView(final Mob mob, final AudioManager audioManager) {
        super(mob, "characters/" + mob.getName() + ".png", DEFAULT_ATTACK_SOUND_PATH, audioManager); // TODO add sounds for mobs
    }

    /**
     * TO DO (pathfinding algorithm).
     * 
     * @param levelView View of the level
     * @param level     Model of the level
     */
    public void moveAI(final LevelView levelView, final Level level) {
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
        } else {
            Pathfinding.moveMob(this, levelView, level.getMap().getFirst());
        }
    }

    @Override
    public void move() {
        // TODO Auto-generated method stub

    }

    public void update(final LevelView level, final Level currentLvl) {
        if (!this.getIsAttacking() && this.getCharacter().canHit(level.getHeroView().getCharacter())) {
            this.setIsAttacking(true);
            this.getCharacter().hitEnemy(level.getHeroView().getCharacter());    
        	} 
        else {
        	this.moveAI(level, currentLvl);
        }
    }

    /**
     * @return the attack time
     */
    public float getAttackTime() {
        return attackTime;
    }

    /**
     * Sets the attack time to the input value.
     * 
     * @param attackTime New value
     */
    public void setAttackTime(final float attackTime) {
        this.attackTime = attackTime;
    }

    /**
     * @return Mob direction
     */
    public Direction getLastDir() {
        return lastDir;
    }

}
