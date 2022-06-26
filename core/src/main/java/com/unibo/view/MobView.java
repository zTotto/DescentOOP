package com.unibo.view;

import com.unibo.ai.Pathfinding;
import com.unibo.ai.SimplePathfinding;
import com.unibo.audio.AudioManager;
import com.unibo.model.Level;
import com.unibo.model.Mob;
import com.unibo.util.Direction;

/**
 * Mob's view class.
 */
public class MobView extends CharacterView {
    private int moveBuffer = 0;
    private float attackTime = 0;
    private static final String DEFAULT_ATTACK_SOUND_PATH = "audio/sounds/KnifeStab.mp3";
    private final Pathfinding pathfinding = new SimplePathfinding();
    private Direction animDir = Direction.STILL;

    /**
     * Constructor for the Mob view.
     * 
     * @param mob          the mob model
     * @param audioManager for the mob's sound
     */
    public MobView(final Mob mob, final AudioManager audioManager) {
        super(mob, "characters/" + mob.getName() + ".png", DEFAULT_ATTACK_SOUND_PATH, audioManager);
    }

    @Override
    public void move() {

    }

    /**
     * Method to be called each time the screen is rendered. It decides whether the
     * mob attacks or moves and if it
     * 
     * @param levelView the levelview of the level the mob is in
     * @param level     the level the mob is in
     */
    public void update(final LevelView levelView, final Level level) {

        attack(levelView);

        if (!this.getIsAttacking()) {
            pathfinding.moveMob(this, levelView, level.getMap().getFirst());
        }
    }

    /**
     * Makes the mob attack the hero if able to do so
     * 
     * @param levelView the levelview of the level the mob is in
     */
    private void attack(LevelView levelView) {
        if (!this.getIsAttacking() && this.getCharacter().canHit(levelView.getHeroView().getCharacter())) {
            this.setIsAttacking(true);
            this.getCharacter().hitEnemy(levelView.getHeroView().getCharacter());
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
     * Sets the mob's moveBuffer to 0
     */
    public void resetMoveBuffer() {
        this.moveBuffer = 0;
    }

    /**
     * Increases the mob's moveBuffer by 1
     */
    public void increaseMoveBuffer() {
        this.moveBuffer++;
    }

    /**
     * Getter for the mob's moveBuffer
     * 
     * @return an integer
     */
    public int getMoveBuffer() {
        int answ = moveBuffer;
        return answ;
    }

    /**
     * @return the direction for the animation
     */
    public Direction getAnimDir() {
        return animDir;
    }

    /**
     * Setter for the animation direction.
     * 
     * @param animDir
     */
    public void setAnimDir(Direction animDir) {
        this.animDir = animDir;
    }

}