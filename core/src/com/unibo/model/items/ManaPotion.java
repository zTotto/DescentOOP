package com.unibo.model.items;

import java.util.Objects;

import com.unibo.model.Character;
import com.unibo.util.Position;

/**
 * 
 * Consumable Item that increases maximum Mana.
 *
 */
public class ManaPotion extends ConsumableItem {

    /**
     * Constructor for a Mana potion.
     * 
     * @param name
     * @param id of the item
     * @param mod
     */
    public ManaPotion(final String name, final String id, final double mod) {
        super(name, id, mod);
    }

    /**
     * Uses the potion on the selected character.
     * 
     * @param pg the character the potion will be used on
     */
    @Override
    public void use(final Character pg) {
        pg.setMaxMana((int) (pg.getMaxMana() + this.getModifier() * pg.getMaxMana()));
    }

    /**
     * This potion can be always used.
     */
    @Override
    public Boolean canUse(final Character pg) {
        return true;
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
        final ManaPotion other = (ManaPotion) obj;
        return Objects.equals(this.getModifier(), other.getModifier());
    }

    @Override
    public ManaPotion setPos(final Position p) {
        if (this.isPosNull()) {
            this.resetPos();
        }
        this.getPos().setPosition(p);
        return this;
    }

}
