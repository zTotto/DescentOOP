package com.unibo.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.unibo.model.Hero;
import com.unibo.model.Level;
import com.unibo.model.items.DoorKey;
import com.unibo.util.Position;

/**
 *  Class test for the door key
 */
public class TestDoorKey {

    private final Hero hero;
    private final Level lvl;
    private final DoorKey key;

    /**
     * Initialize the hero, the level and the door key in order to test the key
     */
    public TestDoorKey() {
        this.hero = new Hero("Tester", 100, 100, 100, 100);
        this.lvl = new Level(null);
        this.key = new DoorKey();
    }
    
    /**
     * Test if the key works as wanted
     */
    @Test
    public void testDoorkey() {
        assertTrue(key.isPosNull());
        key.setPos(new Position(100, 100));
        assertFalse(key.isPosNull());
        assertEquals(new Position(100, 100), key.getPos());
        
        lvl.setDoorPosition(new Position(50, 50));
        lvl.addItems(key);
        
        assertFalse(hero.hasKey());
        hero.setPos(new Position(100, 100));
        hero.pickUpfromLevel(lvl);
        assertTrue(hero.hasKey());
        
        assertFalse(hero.canOpenDoor(new Position(50, 50)));
        hero.setPos(new Position(50, 50));
        assertTrue(hero.canOpenDoor(new Position(50, 50)));        
    }
}
