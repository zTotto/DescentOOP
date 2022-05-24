package com.unibo.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.unibo.maps.Map;
import com.unibo.model.items.ConsumableItem;
import com.unibo.model.items.Weapon;
import com.unibo.util.Pair;
import com.unibo.util.Position;

/**
 * 
 * Class that models a generic character, which has Health Points, speed,
 * position, inventory and a weapon list. The character can attack, move, pickup
 * and use items.
 */
public abstract class Character {

    private static final int LEVEL_TO_SKILL_1 = 2;
    private static final int LEVEL_TO_SKILL_2 = 4;

    private int level = 1;
    private int currentHp;
    private int currentMana;
    private int maxMana;
    private int maxHp;
    private final int initialSpeed;
    private int speed;
    private final Position pos = new Position(0, 0);
    private final List<Weapon> weapons;
    private int currentWeapon;
    private final Inventory inv;
    private Map currentMap;

    /**
     * Constructor for a character.
     * 
     * @param maxHp          Max health points of the character
     * @param speed          Speed of the character
     * @param startingWeapon Starting weapon of the character
     * @param maxMana        Max mana of the character
     */
    public Character(final int maxHp, final int speed, final Weapon startingWeapon, final int maxMana) {
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.maxMana = maxMana;
        this.setCurrentMana(maxMana);
        this.initialSpeed = speed;
        this.setSpeed(this.initialSpeed);
        this.currentWeapon = 0;
        this.inv = new Inventory();
        weapons = new LinkedList<>();
        weapons.add(startingWeapon);
    }

    // Health Point related

    /**
     * 
     * @return current health points.
     */
    public int getCurrentHp() {
        return currentHp;
    }

    /**
     * Sets current health points to the specified value.
     * 
     * @param currentHp
     */
    public void setCurrentHp(final int currentHp) {
        if (currentHp < 0) {
            this.currentHp = 0;
        } else if (currentHp > this.maxHp) {
            this.currentHp = this.maxHp;
        } else {
            this.currentHp = currentHp;
        }
    }

    /**
     * @return max health points.
     */
    public int getMaxHp() {
        return maxHp;
    }

    /**
     * @param maxHp new maxHp of the character
     */
    public void setMaxHp(final int maxHp) {
        this.maxHp = maxHp;
    }

    /**
     * @return current mana points
     */
    public int getCurrentMana() {
        return this.currentMana;
    }

    /**
     * Set the current mana to the specified value.
     * 
     * @param currentMana
     */
    public void setCurrentMana(final int currentMana) {
        if (currentMana < 0) {
            this.currentMana = 0;
        } else if (currentMana > this.maxMana) {
            this.currentMana = this.maxMana;
        } else {
            this.currentMana = currentMana;
        }
    }

    /**
     * Decreases the current mana of the character of the specified value.
     * 
     * @param mana
     */
    public void decreaseCurrentMana(final int mana) {
        this.setCurrentMana(this.currentMana - mana);
    }

    /**
     * Increases the current mana of the character of the specified value.
     * 
     * @param mana
     */
    public void increaseCurrentMana(final int mana) {
        this.setCurrentMana(this.currentMana + mana);
    }

    /**
     * @return max mana points.
     */
    public int getMaxMana() {
        return this.maxMana;
    }

    /**
     * @param maxMana new MaxMana of the character
     */
    public void setMaxMana(final int maxMana) {
        this.maxMana = maxMana;
    }

    /**
     * 
     * @return whether the character is dead.
     */
    public Boolean isDead() {
        return currentHp <= 0;
    }

    // Speed related

    /**
     * @return the initial speed of the character.
     */
    public int getInitialSpeed() {
        return initialSpeed;
    }
    
    /**
     * 
     * @return character speed.
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Sets character speed to the specified value.
     * 
     * @param speed
     */
    public void setSpeed(final int speed) {
        this.speed = speed;
    }

    /**
     * @return the character range when picking up items. (Mobs don't have a pickup
     *         range)
     */
    public abstract int getRange();

    /**
     * Sets the character pickup range. (Mobs can't have a pickup range)
     * 
     * @param range
     */
    public abstract void setRange(int range);

    // Weapon related
    /**
     * 
     * @return a list with all the weapons the character has.
     */
    public List<Weapon> getWeapons() {
        return weapons;
    }

    /**
     * Adds a new weapon to the character.
     * 
     * @param newWeapon
     */
    public void addWeapons(final Weapon newWeapon) {
        weapons.add(newWeapon);
    }

    /**
     * 
     * @return the weapon currently used by the character.
     */
    public Weapon getCurrentWeapon() {
        return this.weapons.get(currentWeapon);
    }

    /**
     * @return the current weapon index.
     */
    public int getCurrentWeaponIndex() {
        return this.currentWeapon;
    }

    /**
     * Sets the specified weapon index as current.
     * 
     * @param newWeaponIndex Index of the new current weapon
     */
    private void setCurrentWeapon(final int newWeaponIndex) {
        if (newWeaponIndex >= 0 && newWeaponIndex < weapons.size()) {
            this.currentWeapon = newWeaponIndex;
        }
    }

    /**
     * Switches the hero's current weapon to the next one. For example if the hero
     * has three weapon and the current one is the second, switching sets the third
     * weapon as current, if the third weapon is the current switching returns to
     * the first weapon.
     */
    public void switchWeapon() {
        if (this.currentWeapon < this.weapons.size() - 1) {
            this.currentWeapon += 1;
            this.setCurrentWeapon(this.currentWeapon);
        } else {
            this.setCurrentWeapon(0);
        }
    }

    /**
     * Checks whether the character can hit the specified enemy.
     * 
     * @param enemy
     * @return true if can hit.
     */
    public Boolean canHit(final Character enemy) {
        return Math.abs(enemy.getPos().getxCoord() - this.getPos().getxCoord()) <= this.getCurrentWeapon().getRange()
                && Math.abs(enemy.getPos().getyCoord() - this.getPos().getyCoord()) <= this.getCurrentWeapon()
                        .getRange();
    }

    /**
     * Hits an enemy (if in range).
     * 
     * @param enemy
     */
    public void hitEnemy(final Character enemy) {
        if (canHit(enemy) && !isDead()) {
            enemy.setCurrentHp(enemy.getCurrentHp() - this.getCurrentWeapon().getDamage());
        }
    }

    /**
     * If in range, hits an enemy on the level.
     * 
     * @param lvl
     * @return the position of the dead enemy
     */
    public Optional<Pair<Position, Integer>> hitEnemyFromLevel(final Level lvl) {
        if (!this.isDead()) {
            for (final Mob e : lvl.getEnemies()) {
                if (this.canHit(e)) {
                    e.setCurrentHp(e.getCurrentHp() - this.getCurrentWeapon().getDamage());
                    return e.isDead() ? Optional.of(new Pair<>(e.getPos(), e.getExpGiven())) : Optional.empty();
                }
            }
        }
        return Optional.empty();
    }

    // Inventory and Item related

    /**
     * 
     * @return the character inventory.
     */
    public Inventory getInv() {
        return inv;
    }

    /**
     * Uses the specified item (if present in the inventory). The mob can't use an
     * item (so this will be empty on a mob)
     * 
     * @param item
     */
    public abstract void useItem(ConsumableItem item);

    /**
     * If in range, picks up an item from the level. Mobs can't pick up items.
     * 
     * @param lvl
     * @return true if an item was picked up.
     */
    public abstract Boolean pickUpfromLevel(Level lvl);

    // Position related

    /**
     * 
     * @return the character position.
     */
    public Position getPos() {
        return pos;
    }

    /**
     * Sets the character to the specified position.
     * 
     * @param p the position
     * @return the character
     */
    public abstract Character setPos(Position p);

    /**
     * Sets the character to the specified coordinates.
     * 
     * @param xCoord
     * @param yCoord
     * @return the character
     */
    public abstract Character setPos(int xCoord, int yCoord);

    /**
     * Moves the character up (movement is speed-based).
     */
    public void moveUp() {
        this.setPos(this.pos.getxCoord(), this.pos.getyCoord() + this.speed);
    }

    /**
     * Moves the character down (movement is speed-based).
     */
    public void moveDown() {
        this.setPos(this.pos.getxCoord(), this.pos.getyCoord() - this.speed);
    }

    /**
     * Moves the character right (movement is speed-based).
     */
    public void moveRight() {
        this.setPos(this.pos.getxCoord() + this.speed, this.pos.getyCoord());
    }

    /**
     * Moves the character left (movement is speed-based).
     */
    public void moveLeft() {
        this.setPos(this.pos.getxCoord() - this.speed, this.pos.getyCoord());
    }

    /**
     * @return the description of the character.
     */
    public String toString() {
        return "Max HP: " + this.getMaxHp() + ", Weapon: " + this.getWeapons();
    }

    /**
     * @return the current map
     */
    public Map getCurrentMap() {
        return currentMap;
    }

    /**
     * Sets a map as current.
     * 
     * @param map
     */
    public void setCurrentMap(final Map map) {
        this.currentMap = map;
    }

    /**
     * @return the name of the character
     */
    public abstract String getName();

    /**
     * Skill: Increases the movement speed of the character.
     * 
     * @param speed the amount of speed added to the character
     * @return true if the character can use this skill
     */
    public boolean increaseSpeed(final int speed) {
        if (this.getLevel() >= this.levelToSpeedUp()) {
            this.setSpeed(this.getSpeed() + speed);
            return true;
        }
        return false;
    }

    /**
     * Skill: Heal the character.
     * 
     * @param hp the amount of hp added to the character
     * @return true if the character can use this skill
     */
    public boolean heal(final int hp) {
        if (this.getLevel() >= this.levelToHeal()) {
            this.setCurrentHp(this.getCurrentHp() + hp);
            return true;
        }
        return false;
    }

    /**
     * @return the level of the character
     */
    public int getLevel() {
        return level;
    }

    /**
     * Increment the level of the character.
     */
    public void incrementLevel() {
        this.level++;
    }

    /**
     * @return the level needed to use the skill speed up
     */
    public int levelToSpeedUp() {
        return LEVEL_TO_SKILL_1;
    }

    /**
     * @return the level needed to use the skill Heal
     */
    public int levelToHeal() {
        return LEVEL_TO_SKILL_2;
    }
}
