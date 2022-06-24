package com.unibo.model;

import java.util.List;

import com.unibo.model.items.ConsumableItem;
import com.unibo.model.items.HealthPotion;
import com.unibo.model.items.Item;
import com.unibo.model.items.ManaPotion;
import com.unibo.model.items.Weapon;
import com.unibo.model.items.WearableItem;
import com.unibo.util.Pair;
import com.unibo.util.Position;
import com.unibo.util.WeaponStats;

/**
 * 
 * A class to model the Hero.
 */
@SuppressWarnings("PMD.MissingSerialVersionUID")
public class Hero extends Character {

    private static final double HP_MANA_LEVELUP_MULTIPLAYER = 1.1;
    private static final double EXP_ALG_DIVIDER = 2.5;
    private static final int MAX_LEVEL = 10;

    private final String name;
    private Boolean key = false;
    private int range;

    private long exp;
    private long expToLevelUp;

    /**
     * Constructor for the Hero.
     * 
     * @param name    Name of the Hero
     * @param maxHp   Max health points of the Hero
     * @param speed   Speed of the Hero
     * @param maxMana Max mana of the Hero
     */
    public Hero(final String name, final int maxHp, final int speed, final int maxMana, final long expToLevelUp) {
        super(maxHp, speed, new Weapon(WeaponStats.FISTS, "0"), maxMana);
        this.name = name;
        this.range = speed / 6;
        this.expToLevelUp = expToLevelUp;
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
        if (this.getInv().contains(item) && item.canUse(this)) {
            item.use(this);
            this.getInv().removeItem(item);
        }
    }

    /**
     * If there's any, uses a health potion.
     */
    public void useHealthPotion() {
        HealthPotion pot = null;
        for (final Pair<Item, Integer> p : this.getInv().getInv()) {
            if (p.getFirst() instanceof HealthPotion) {
                pot = (HealthPotion) p.getFirst();
                break;
            }
        }
        if (pot != null && pot.canUse(this)) {
            pot.use(this);
            this.getInv().removeItem(pot);
        }
    }

    /**
     * If there's any, uses a mana potion.
     */
    public void useManaPotion() {
        ManaPotion pot = null;
        for (Pair<Item, Integer> p : this.getInv().getInv()) {
            if (p.getFirst() instanceof ManaPotion) {
                pot = (ManaPotion) p.getFirst();
                break;
            }
        }
        if (!(pot == null) && pot.canUse(this)) {
            pot.use(this);
            this.getInv().removeItem(pot);
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean pickUpfromLevel(final Level lvl) {
        if (!this.isDead()) {
            final List<Item> items = lvl.getItems();
            int index = 0;
            for (final Item item : items) {
                if (this.isInRange(item.getPos())) {
                    if (item instanceof Weapon) {
                        this.getWeapons().add((Weapon) item);
                    } else if (item instanceof ConsumableItem) {
                        this.getInv().addItem(item);
                    } else if (item instanceof WearableItem) {
                        ((WearableItem) item).wear(this);
                    } else {
                        this.key = true;
                    }
                    lvl.getItems().remove(index);
                    return true;
                }
                index++;
            }
        }
        return false;
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
     * Add a specific amount of exp to the hero. If the hero reach the right amount
     * of exp, he will level up and the exp that exceeds the limit will be held by
     * the hero.
     * 
     * @param exp to be added to the hero
     */
    public void addExp(final long exp) {
        if (this.getLevel() < MAX_LEVEL) {
            this.exp += exp;
            if (this.isExpEnough()) {
                this.levelUp();
            }
        }
        else {
            this.resetXP();
        }
    }

    /**
     * @return the exp the hero needs to level up
     */
    public long getExpToLevelUp() {
        return expToLevelUp;
    }

    /**
     * Change the amount of exp the hero needs to level up.
     * 
     * @param expToLevelUp new amount of exp needed to the hero to level up
     */
    public void setExpToLevelUp(final long expToLevelUp) {
        this.expToLevelUp = expToLevelUp;
    }

    /*
     * Level up the hero, increases the amount of exp needed to level up again, his
     * max hp and resets his hp. If the actual exp exceeds the amount of exp needed
     * for the next level, level up the hero again
     */
    // Could become public if there will be an item that will level up the hero.
    private void levelUp() {
        if (this.getLevel() < MAX_LEVEL) {
            this.exp -= this.getExpToLevelUp();
            this.incrementLevel();
            this.increaseExpToLevelUp();
            this.increaseStats();
            
            if (this.isExpEnough()) {
                this.levelUp();
            }
        } else {
            this.resetXP();
        }
    }

    private void increaseExpToLevelUp() {
        if (this.getLevel() < MAX_LEVEL / 2) {
            this.setExpToLevelUp(Math
                    .round(this.getExpToLevelUp() * Math.log10(this.getExpToLevelUp() / EXP_ALG_DIVIDER)));
        } else {
            this.setExpToLevelUp(Math.
                    round(this.getExpToLevelUp() * Math.log10(this.getExpToLevelUp() / (EXP_ALG_DIVIDER * this.getLevel()))));
        }        
    }

    private void increaseStats() {
        this.setMaxHp((int) (this.getMaxHp() * HP_MANA_LEVELUP_MULTIPLAYER));
        this.setMaxMana((int) (this.getMaxMana() * HP_MANA_LEVELUP_MULTIPLAYER));
        this.setCurrentHp(this.getMaxHp());
        this.setCurrentMana(this.getMaxMana());        
    }

    private void resetXP() {
        this.exp = 0;
        this.setExpToLevelUp(0);
    }

    private boolean isExpEnough() {
        return this.exp >= this.getExpToLevelUp() && this.getLevel() < MAX_LEVEL;
    }

    /**
     * @return true if the hero has the key.
     */
    public Boolean hasKey() {
        return this.key;
    }

    /**
     * Destroys the key.
     */
    public void resetKey() {
        this.key = false;
    }

    /**
     * 
     * @param door
     * @return true if the Hero has the key and is near the door
     */
    public Boolean canOpenDoor(final Position door) {
        return this.hasKey() && isInRange(door.getPosition());
    }

    /**
     * 
     * @param position
     * @return true if the Hero is near the position
     */
    private boolean isInRange(final Position position) {
        return Math.abs(position.getxCoord() - this.getPos().getxCoord()) < this.getRange()
                && Math.abs(position.getyCoord() - this.getPos().getyCoord()) < this.getRange();
    }

    @Override
    public Hero setPos(final Position p) {
        if (!isDead()) {
            getPos().setxCoord(p.getxCoord());
            getPos().setyCoord(p.getyCoord());
        }
        return this;
    }

    @Override
    public Hero setPos(final float xCoord, final float yCoord) {
        return this.setPos(new Position(xCoord, yCoord));
    }
}
