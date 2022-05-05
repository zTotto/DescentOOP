package com.unibo.model;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * A class to model the Hero.
 */
@SuppressWarnings("PMD.MissingSerialVersionUID")
public class Hero extends Character {

    private final String name;
    private int range;

    /**
     * Constructor for the Hero.
     * 
     * @param name
     * @param maxHp
     * @param speed
     * @param startingWeapon
     */
    public Hero(final String name, final int maxHp, final int speed, final Weapon startingWeapon) {
        super(maxHp, speed, startingWeapon);
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
}
