package com.unibo.model;

import java.util.LinkedList;
import java.util.List;

import com.unibo.util.Pair;

/**
 * Class to model a character's inventory.
 */
public class Inventory {

    private final List<Pair<Item, Integer>> inv = new LinkedList<>();

    /**
     * Empty constructor for the Inventory.
     */
    public Inventory() {
        // Empty Constructor
    }

    /**
     * Constructor which accepts multiple items.
     * 
     * @param item to add to the inventory
     */
    public Inventory(final Pair<Item, Integer>... item) {
        for (final Pair<Item, Integer> i : item) {
            inv.add(i);
        }
    }

    /**
     * 
     * @return the inventory.
     */
    public List<Pair<Item, Integer>> getInv() {
        return inv;
    }

    /**
     * Adds an item to the inventory if it's not present, otherwise it increases the
     * item quantity by 1.
     * 
     * @param item that will be added to the inventory
     */
    public void addItem(final Item item) {
        if (this.contains(item)) {
            final int i = this.indexOf(item);
            inv.get(i).setSecond(inv.get(i).getSecond() + 1);
            return;
        } else {
            inv.add(new Pair<>(item, 1));
        }
    }

    /**
     * Decreases the quantity of an item by 1, if final quantity is 0 it removes the
     * item.
     * 
     * @param item that will be removed from the inventory
     */
    public void removeItem(final Item item) {
        if (this.contains(item)) {
            final int i = this.indexOf(item);
            if (inv.get(i).getSecond() < 2) {
                inv.remove(i);
            } else {
                inv.get(i).setSecond(inv.get(i).getSecond() - 1);
            }
        }
    }

    /**
     * Checks whether an item is present in the inventory.
     * 
     * @param item
     * @return true if present
     */
    public Boolean contains(final Item item) {
        for (final Pair<Item, Integer> p : inv) {
            if (p.contains(item)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns how many potions the inventory contains.
     * @return the quantity of potions
     */
    public int getPotionQuantity() {
        for (final Pair<Item, Integer> p : inv) {
            if (p.getFirst() instanceof HealthPotion) {
                return p.getSecond();
            }
        }
        return 0;
    }

    /**
     * If an item is present in the inventory it returns its index.
     * 
     * @param item
     * @return the index
     */
    private int indexOf(final Item item) {
        int index = 0;
        if (this.contains(item)) {
            for (final Pair<Item, Integer> p : inv) {
                if (p.contains(item)) {
                    return index;
                }
                index++;
            }
        }
        return -1;
    }

    /**
     * @return the content of the inventory.
     */
    public String toString() {
        if (inv.isEmpty()) {
            return "No Inventory!";
        } else {
            String msg = "";
            for (final Pair<Item, Integer> p : inv) {
                msg += "Item: [ " + p.getFirst() + " ], Quantity: [ " + p.getSecond() + " ]";
            }
            return "\nInventory:\n" + msg + "\n";
        }
    }
}
