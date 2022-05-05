package com.unibo.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Class that models a level with a list of ConsumableItems, Weapons and
 * Enemies.
 */
public class Level {

    private final List<ConsumableItem> consumables;
    private final List<Weapon> weapons;
    private final List<Character> enemies;

    /**
     * Empty constructor for a level.
     */
    public Level() {
        consumables = new LinkedList<>();
        weapons = new LinkedList<>();
        enemies = new LinkedList<>();
    }

    /**
     * Adds consumables to the level.
     * 
     * @param items
     */
    public void addConsumables(final ConsumableItem... items) {
        for (final ConsumableItem i : items) {
            if (i.getPos() != null) {
                consumables.add(i);
            }
        }
    }

    /**
     * Adds enemies to the level.
     * 
     * @param enemies
     */
    public void addEnemies(final Character... enemies) {
        for (final Character c : enemies) {
            this.enemies.add(c);
        }
    }

    /**
     * Adds weapons to pickup to the level.
     * 
     * @param weapons
     */
    public void addWeapons(final Weapon... weapons) {
        for (final Weapon w : weapons) {
            if (w.getPos() != null) {
                this.weapons.add(w);
            }
        }
    }

    /**
     * Removes a consumable item from the level.
     * 
     * @param item
     */
    public void removeConsumable(final ConsumableItem item) {
        consumables.remove(item);
    }

    /**
     * Removes a consumable item at a specified index from the level.
     * 
     * @param index of the item
     */
    public void removeConsumableAtIndex(final int index) {
        consumables.remove(index);
    }

    /**
     * Removes a weapon from the level.
     * 
     * @param weapon
     */
    public void removeWeapon(final Weapon weapon) {
        weapons.remove(weapon);
    }

    /**
     * Removes an enemy item from the level.
     * 
     * @param enemy
     */
    public void removeCharacter(final Character enemy) {
        enemies.remove(enemy);
    }

    /**
     * @return a list containing all the consumables
     */
    public List<ConsumableItem> getConsumables() {
        return new LinkedList<>(consumables);
    }

    /**
     * @return a list containing all the weapons
     */
    public List<Weapon> getWeapons() {
        return new LinkedList<>(weapons);
    }

    /**
     * @return a list containing all the enemies
     */
    public List<Character> getEnemies() {
        return new LinkedList<>(enemies);
    }

    /**
     * @return a string with all the content of the level
     */
    public String toString() {
        int i = 0;
        String msg = "\nLEVEL: \n";
        if (!enemies.isEmpty()) {
            msg += "Enemies:";
            for (final Character e : enemies) {
                msg += "\n" + ++i + ": [" + e.toString() + "]\nPosition: " + e.getPos().toString();
            }
        } else {
            msg += "No Enemies";
        }
        i = 0;
        if (!consumables.isEmpty()) {
            msg += "\nItems:";
            for (final ConsumableItem item : consumables) {
                msg += "\n" + ++i + ": [ " + item.getName() + " ], Position: " + item.getPos().toString();
            }
        } else {
            msg += "\nNo Items";
        }
        i = 0;
        if (!weapons.isEmpty()) {
            msg += "\nWeapons:";
            for (final Weapon w : weapons) {
                msg += "\n" + ++i + ": [" + w.toString() + "]" + ", Position: " + w.getPos().toString();
            }
        } else {
            msg += "\nNo Weapons";
        }
        return msg;
    }
}
