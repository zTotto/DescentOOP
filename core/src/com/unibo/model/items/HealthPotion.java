package com.unibo.model.items;

import java.util.Objects;

import com.unibo.model.Character;
import com.unibo.util.HealthPotionStats;
import com.unibo.util.Position;

/**
 * Consumable Item that replenishes health points.
 */
public class HealthPotion extends ConsumableItem {

    /**
     * Constructor for a health potion.
     * 
     * @param potion from the enum
     * @param id     of the item
     */
    public HealthPotion(final HealthPotionStats potion, final String id) {
        super(potion.getName(), id, potion.getModifier());
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

    @Override
    public Boolean canUse(final Character pg) {
        return pg.getCurrentHp() < pg.getMaxHp();
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

    @Override
    public HealthPotion setPos(final Position p) {
        this.pos = p;
        return this;
    }
}
