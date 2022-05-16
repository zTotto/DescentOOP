package com.unibo.model;

import java.util.Objects;

/**
 * Consumable Item that replenishes health points.
 */
public class HealthPotion extends ConsumableItem {

    /**
     * Constructor for a health potion.
     * 
     * @param name     of the potion
     * @param id       of the item
     * @param modifier of the potion (hp)
     */
    public HealthPotion(final String name, final String id, final double modifier) {
        super(name, id, modifier);
    }

    /**
     * Uses the potion on the selected character.
     * 
     * @param pg the character the potion will be used on
     */
    @Override
    public void use(final Character pg) {
        pg.setCurrentHp(pg.getCurrentHp() + (int) this.getModifier());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final HealthPotion other = (HealthPotion) obj;
        return Objects.equals(this.getModifier(), other.getModifier());
    }
}
