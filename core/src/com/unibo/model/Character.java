package com.unibo.model;

import java.util.LinkedList;
import java.util.List;

import com.unibo.maps.Map;
import com.unibo.util.Position;

/**
 * 
 * Class that models a generic character, which has Health Points, speed,
 * position, inventory and a weapon list. The character can attack, move, pickup
 * and use items.
 */
public abstract class Character {

    private int currentHp;
    private final int maxHp;
    private int speed;
    private final Position pos = new Position(0, 0);
    private final List<Weapon> weapons;
    private Weapon currentWeapon;
    private final Inventory inv;
    private Map currentMap;

    /**
     * Constructor for a character.
     * 
     * @param maxHp
     * @param speed
     * @param startingWeapon
     */
    public Character(final int maxHp, final int speed, final Weapon startingWeapon) {
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.setSpeed(speed);
        this.currentWeapon = startingWeapon;
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
     * 
     * @return max health points.
     */
    public int getMaxHp() {
        return maxHp;
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
     * @return the character range when picking up items. (Mobs don't have a pickup range)
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
        return currentWeapon;
    }

    /**
     * Sets the specified weapon as current.
     * 
     * @param currentWeapon
     */
    public void setCurrentWeapon(final Weapon currentWeapon) {
        this.currentWeapon = currentWeapon;
    }

    /**
     * Checks whether the character can hit the specified enemy.
     * 
     * @param enemy
     * @return true if can hit.
     */
    public Boolean canHit(final Character enemy) {
        if (Math.abs(enemy.getPos().getxCoord() - this.getPos().getxCoord()) <= this.currentWeapon.getRange()
                && Math.abs(enemy.getPos().getyCoord() - this.getPos().getyCoord()) <= this.currentWeapon.getRange()) {
            return true;
        }
        return false;
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
     * @return true if an enemy was hit
     */
    public Boolean hitEnemyFromLevel(final Level lvl) {
        if (!this.isDead()) {
            for (final Character e : lvl.getEnemies()) {
                if (this.canHit(e)) {
                    e.setCurrentHp(e.getCurrentHp() - this.getCurrentWeapon().getDamage());
                    return true;
                }
            }
        }
        return false;
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
     * Uses the specified item (if present in the inventory).
     * The mob can't use an item (so this will be empty on a mob)
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

    /**
     * Checks whether an item can be picked up. Mobs can't pick up items.
     * 
     * @param item to be checked
     * @return true if the item can be picked up
     */
    public abstract Boolean canPickUpItem(Item item);

    /**
     * Pick ups the specified item.
     * 
     * @param item
     */
    public void pickUpItem(final Item item) {
        if (this.canPickUpItem(item)) {
            if (item instanceof Weapon) {
                weapons.add((Weapon) item);
            } else {
                inv.addItem(item);
            }
        }
    }

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
     */
    public void setPos(final Position p) {
        if (!isDead()) {
            this.pos.setxCoord(p.getxCoord());
            this.pos.setyCoord(p.getyCoord());
        }
    }

    /**
     * Sets the character to the specified coordinates.
     * 
     * @param xCoord
     * @param yCoord
     */
    public void setPos(final int xCoord, final int yCoord) {
        if (!isDead()) {
            this.pos.setxCoord(xCoord);
            this.pos.setyCoord(yCoord);
        }
    }

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
}
