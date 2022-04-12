package com.unibo.model;

/**
 * Consumable Item that replenishes health points.
 */
public class HealthPotion extends ConsumableItem {

    public HealthPotion(final String name, final String id, final double mod) {
        super(name, id, mod);
    }

    @Override
    public void use(final Character pg) {
        pg.setCurrentHp(pg.getCurrentHp() + (int) this.getModifier());
    }
}
