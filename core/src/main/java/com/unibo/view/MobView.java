package com.unibo.view;

import com.unibo.ai.LineOfSight;
import com.unibo.ai.Pathfinding;
import com.unibo.ai.SimplePathfinding;
import com.unibo.audio.AudioManager;
import com.unibo.model.Level;
import com.unibo.model.Mob;
import com.unibo.model.Movement;
import com.unibo.util.Direction;

/**
 * Mob's view class.
 */
public class MobView extends CharacterView {
	private int moveBuffer = 0;
	private Direction lastDir = Direction.UP;
	private float attackTime = 0;
	private static final String DEFAULT_ATTACK_SOUND_PATH = "audio/sounds/KnifeStab.mp3";
	private final Pathfinding pathfinding = new SimplePathfinding();

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
	 * Method to be called each time the screen is rendered.
	 * It decides whether the mob attacks or moves and if it
	 * moves randomly or according to a pathfinding algorithm.
	 * 
	 * @param levelView		the levelview of the level the mob is in
	 * @param level		the level the mob is in
	 */
	public void update(final LevelView levelView, final Level level) {
		
		if (!this.getIsAttacking() && this.getCharacter().canHit(levelView.getHeroView().getCharacter())) {
			this.setIsAttacking(true);
			this.getCharacter().hitEnemy(levelView.getHeroView().getCharacter());
		}
		
		else if (!LineOfSight.isHeroSeen(this, levelView, level.getMap().getFirst())) {
			final Movement move;
			if (moveBuffer <= 15) {
				moveBuffer++;
				move = new Movement(lastDir);
				move.executeCommand(this);
				return;
				}
			Direction newDir = Pathfinding.randomDirection();
			move = new Movement(newDir);
			move.executeCommand(this);
			lastDir = newDir;
			moveBuffer = 0;
			return;
			} else {
				pathfinding.moveMob(this, levelView, level.getMap().getFirst());
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
