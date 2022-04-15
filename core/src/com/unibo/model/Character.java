package com.unibo.model;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
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
    private int range;
    private final Position pos = new Position(0, 0);
    private final List<Weapon> weapons;
    private Weapon currentWeapon;
    private final Inventory inv;
    private Map currentMap;
    private Sound attackSound;
    private Sound defaultSound = Gdx.audio.newSound(Gdx.files.internal("audio/sounds/Morgan che succede.mp3")); // to
                                                                                                                // fix,
                                                                                                                // it
    // attack sound
    // needs to be
    // in a
    // constructor
    // or something
    // similar

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
        this.setRange(speed / 6);
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
     * @return the character range when picking up items.
     */
    public int getRange() {
        return range;
    }

    /**
     * Sets the character pickup range.
     * 
     * @param range
     */
    public void setRange(final int range) {
        this.range = range;
    }

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
            for (Character e : lvl.getEnemies()) {
                if (this.canHit(e)) {
                    e.setCurrentHp(e.getCurrentHp() - this.getCurrentWeapon().getDamage());
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
     * 
     * @param item
     */
    public void useItem(final ConsumableItem item) {
        if (this.inv.contains(item)) {
            item.use(this);
            this.inv.removeItem(item);
        }
    }

    /**
     * If in range, picks up an item from the level.
     * 
     * @param lvl
     * @return true if an item was picked up.
     */
    public Boolean pickUpfromLevel(final Level lvl) {
        if (!this.isDead()) {
            List<Item> items = new LinkedList<>();
            items.addAll(lvl.getConsumables());
            items.addAll(lvl.getWeapons());
            for (Item item : items) {
                if (Math.abs(item.getPos().getxCoord() - this.getPos().getxCoord()) <= this.range
                        && Math.abs(item.getPos().getyCoord() - this.getPos().getyCoord()) <= this.range) {
                    if (item instanceof Weapon) {
                        weapons.add((Weapon) item);
                        lvl.removeWeapon((Weapon) item);
                    } else {
                        inv.addItem(item);
                        lvl.removeConsumable((ConsumableItem) item);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks whether an item can be picked up.
     * 
     * @param item to be checked
     * @return true if the item can be picked up
     */
    public Boolean canPickUpItem(final Item item) {
        return item.getPos().equals(this.getPos());
    }

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
     * @return the description of the hero.
     */
    public String toString() {
        String msg = "Max HP: " + this.getMaxHp() + ", Weapon: " + this.getWeapons();
        return msg;
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
     * Sets a sound as the attack sound.
     * 
     * @param path of the sound
     */
    public void setAttackSound(final String path) {
        attackSound = Gdx.audio.newSound(Gdx.files.internal(path));
    }

    /**
     * @return the attack sound
     */
    public Sound getAttackSound() {
        if (attackSound != null) {
            return attackSound;
        }
        return defaultSound;
    }
}
