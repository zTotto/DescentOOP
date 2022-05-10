package com.unibo.model;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * A class to model the Hero.
 */
@SuppressWarnings("PMD.MissingSerialVersionUID")
public class Hero extends Character {

    private static final double EXP_ALG_DIVIDER = 2.5;
    private static final int MAX_LEVEL = 10;
    private static final int LEVEL_TO_SKILL_1 = 2;
    
    private final String name;
    private int range;
    private int level = 1;
    private long exp;
    private long expToLevelUp = 60;

    /**
     * Constructor for the Hero.
     * 
     * @param name
     * @param maxHp
     * @param speed
     * @param startingWeapon
     */
    public Hero(final String name, final int maxHp, final int speed, final Weapon startingWeapon, final int maxMana) {
        super(maxHp, speed, startingWeapon, maxMana);
        this.name = name;
        this.range = speed / 6;
    }

    /**
     * {@inheritDoc}
     */
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return "\nName: " + this.getName() + ", Current HP: " + this.getCurrentHp() + ", Weapon: " + this.getWeapons();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void useItem(final ConsumableItem item) {
        if (this.getInv().contains(item)) {
            item.use(this);
            this.getInv().removeItem(item);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean pickUpfromLevel(final Level lvl) {
        if (!this.isDead()) {
            final List<Item> items = new LinkedList<>();
            items.addAll(lvl.getConsumables());
            items.addAll(lvl.getWeapons());
            int consIndex = 0;
            for (final Item item : items) {
                if (Math.abs(item.getPos().getxCoord() - this.getPos().getxCoord()) < this.getRange()
                        && Math.abs(item.getPos().getyCoord() - this.getPos().getyCoord()) < this.getRange()) {
                    if (item instanceof Weapon) {
                        this.getWeapons().add((Weapon) item);
                        lvl.removeWeapon((Weapon) item);
                    } else {
                        this.getInv().addItem(item);
                        lvl.removeConsumableAtIndex(consIndex);
                    }
                    return true;
                }
                consIndex++;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean canPickUpItem(final Item item) {
        return item.getPos().equals(this.getPos());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRange() {
	return this.range;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRange(final int range) {
        this.range = range;
    }

    /**
     * @return the amount of exp the hero has.
     */
    public long getExp() {
        return exp;
    }

    /**
     * Add a specific amount of exp to the hero. If the hero reach the right amount of exp,
     * he will level up and the exp that exceeds the limit will be held by the hero.
     * 
     * @param exp to be added to the hero
     */
    public void addExp(final int exp) {
        if (this.level < MAX_LEVEL) {
            this.exp += exp;
            if (this.isExpEnough()) {
                this.levelUp();
            }
        }
    }

    /**
     * @return the level of the hero
     */
    public int getLevel() {
        return level;
    }
    
    /**
     * @return the exp the hero needs to level up
     */
    public long getExpToLevelUp() {
        return expToLevelUp;
    }

    /**
     * Change the amount of exp the hero needs to level up
     * 
     * @param expToLevelUp new amount of exp needed to the hero to level up
     */
    public void setExpToLevelUp(final long expToLevelUp) {
        this.expToLevelUp = expToLevelUp;
    }
    
    /*
     * Level up the hero, increases the amount of exp needed to level up again, his max hp and resets his hp.
     * If the actual exp exceeds the amount of exp needed for the next level, level up the hero again
     */
    //Could become public if there will be an item that will level up the hero.
    private void levelUp() {
        this.exp -= this.getExpToLevelUp();
        this.level++;
        if (this.level < MAX_LEVEL/2) {
            this.setExpToLevelUp(Math.round(this.getExpToLevelUp()*Math.log10(this.getExpToLevelUp()/EXP_ALG_DIVIDER)));
        } else {
            this.setExpToLevelUp(Math.round(this.getExpToLevelUp()*Math.log10(this.getExpToLevelUp()/(EXP_ALG_DIVIDER * this.level))));
        }
        this.setMaxHp(this.getMaxHp()+this.getMaxHp()/10);
        this.setCurrentHp(this.getMaxHp());
        if (this.isExpEnough()) { 
            this.levelUp();
        }
        
        if (this.level == MAX_LEVEL) {
            this.resetXP();
        }
    }

    private void resetXP() {
        this.exp = 0;
        this.setExpToLevelUp(0);        
    }

    private boolean isExpEnough() {
        return this.exp >= this.getExpToLevelUp() && this.level < MAX_LEVEL;
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean increaseSpeed(final int speed) {
        if (this.getLevel() >= LEVEL_TO_SKILL_1) {
            super.increaseSpeed(speed);
            return true;
        }
        return false;
    }
}
