package com.unibo.util;

/**
 * Enum used to create different types of mobs.
 */
public enum MobStats {
    /**
     * Mob with 60 hp and a speed of 130.
     */
    ORC("Orc", 60, 130),
    /**
     * Mob with 30 hp and a speed of 170.
     */
    TROLL("Troll", 30, 170);

    private final String name;
    private final int hp;
    private final int speed;

    /**
     * Constructor for the enum MobsStats.
     * 
     * @param name  mob's name
     * @param hp    mob's health points
     * @param speed mob's movement speed
     */
    MobStats(final String name, final int hp, final int speed) {
        this.name = name;
        this.hp = hp;
        this.speed = speed;
    }

    /**
     * @return the mob's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return the mob's health points
     */
    public int getHp() {
        return this.hp;
    }

    /**
     * @return the mob's movement speed
     */
    public int getSpeed() {
        return this.speed;
    }
}
