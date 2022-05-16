package com.unibo.model;

/**
 * 
 * Class to model a consumable, like a health potion or damage buff potion.
 */
public abstract class ConsumableItem extends Item {

    private final double modifier;

    /**
     * Constructor for a consumable item.
     * 
     * @param name of the item
     * @param id   of the item
     * @param mod  to apply to the user of the item
     */
    public ConsumableItem(final String name, final String id, final double mod) {
        super(name, id);
        this.modifier = mod;
    }

    /**
     * 
     * @return the value of the consumable (for example replenished Hp).
     */
    public double getModifier() {
        return modifier;
    }

    /**
     * Uses the consumable on the selected pg.
     * 
     * @param pg the character that gets the buff.
     */
    public abstract void use(Character pg);

    /**
     * Checks whether a character can use the consumable.
     * 
     * @param pg the character
     * @return true if the character is able to use it
     */
    public abstract Boolean canUse(Character pg);
}
