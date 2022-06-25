package com.unibo.tests;
import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import com.unibo.model.Level;
import com.unibo.model.LevelsList;

/**
 * 
 * Class to test LevelsList
 *
 */
public class TestLevelsList {

    private LevelsList l;

    /**
     * Initialize the levels list
     */
    public TestLevelsList() {
        final List<Level> list = new LinkedList<>(List.of(new Level(null),
                new Level(null), new Level(null)));
        this.l = new LevelsList(list);
    }
    
    /**
     * Test a levels list
     */
    @Test
    public void testGenericLevelsList() {
        assertEquals(l.getCurrentLevel(), l.getLevels().get(0));
        assertTrue(l.hasNextLevel());
        assertEquals(3, l.getLevels().size());
        assertFalse(l.isGameOver());
        
        assertEquals(l.getNextLevel(), l.getLevels().get(1));
        assertTrue(l.hasNextLevel());
        assertEquals(3, l.getLevels().size());
        assertEquals(l.getNextLevel(), l.getLevels().get(2));
        
        assertFalse(l.hasNextLevel());
        assertEquals(l.getCurrentLevel(), l.getLevels().get(2));
          
        assertThrows(NoSuchElementException.class, () -> l.getNextLevel());
        assertThrows(UnsupportedOperationException.class, () -> l.getLevels().add(new Level(null)));
        assertTrue(l.isGameOver());
        assertFalse(l.getLevels().isEmpty());
    }
    
    /**
     * Test an epty level list
     */
    @Test
    public void TestEmptyLevelsList() {
        this.l = new LevelsList(List.of());
        
        assertTrue(l.getLevels().isEmpty());
        assertFalse(l.hasNextLevel());
    }
    
}
