package com.unibo.util;

/**
 * Enumeration for different health potions.
 */
public enum HealthPotionStats {

    /**
     * Basic Health Potion: Heals 15 hp.
     */
    BASIC_HEALTH_POTION("Basic Health Potion", 15),
    /**
     * Basic Health Potion: Heals 15 hp.
     */
    MEDIUM_HEALTH_POTION("Medium Health Potion", 25),
    /**
     * Basic Health Potion: Heals 15 hp.
     */
    LARGE_HEALTH_POTION("Large Health Potion", 50);

    private final String name;
    private final double modifier;

    /**
     * Constructor for health potions.
     * 
     * @param name     Name of the potion
     * @param modifier Amount of hp healed
     */
    HealthPotionStats(final String name, final double modifier) {
        this.name = name;
        this.modifier = modifier;
    }

    /**
     * @return the potion name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the potion healing power
     */
    public double getModifier() {
        return modifier;
    }
}
