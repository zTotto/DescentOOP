package com.unibo.util;

/**
 * Enum used to create different types of mobs.
 */
public enum MobStats {
    /**
     * Mob with 60 hp, a speed of 130 and 50 of mana, that will give 20 exp to the
     * hero.
     */
    ORC("Orc", 60, 130, 50, 20),
    /**
     * Mob with 30 hp, a speed of 170 and 20 of mana, that will give 15 exp to the
     * hero.
     */
    TROLL("Troll", 30, 170, 20, 12);

    private final String name;
    private final int hp;
    private final int speed;
    private final int expGiven;
    private final int mana;

    /**
     * Constructor for the enum MobsStats.
     * 
     * @param name     mob's name
     * @param hp       mob's health points
     * @param speed    mob's movement speed
     * @param mana     mob's mana
     * @param expGiven exp given when the mob is killed
     */
    MobStats(final String name, final int hp, final int speed, final int mana, final int expGiven) {
        this.name = name;
        this.hp = hp;
        this.speed = speed;
        this.expGiven = expGiven;
        this.mana = mana;
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

    /**
     * @return the exp the mob gives when killed
     */
    public int getExpGiven() {
        return this.expGiven;
    }

    /**
     * @return the mana of the mob
     */
    public int getMana() {
        return this.mana;
    }
}
