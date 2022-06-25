package com.unibo.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.unibo.model.Hero;

/**
 *  Class test for experience, leveling and skills of hero
 */
public class ExperienceLevelUp {
    
    private Hero hero;
    
    public ExperienceLevelUp(){
        this.hero = new Hero("Test", 100, 200, 100, 60);
    }
    
    /**
     * Test the experience and leveling system of hero
     */
    @Test
    public void testAddExperienceLevelUp()
    {
        assertEquals(0,this.hero.getExp());
        assertEquals(60, this.hero.getExpToLevelUp());
        assertEquals(1, this.hero.getLevel());
        
        this.hero.setExpToLevelUp(90);
        assertEquals(90, this.hero.getExpToLevelUp());
        
        this.hero.addExp(30);
        assertEquals(30, this.hero.getExp());
        assertEquals(90, this.hero.getExpToLevelUp());
        assertEquals(1, this.hero.getLevel());
        
        this.hero.addExp(60);
        assertEquals(0, this.hero.getExp());
        assertEquals(2, this.hero.getLevel());
        
        this.hero.setExpToLevelUp(120);
        assertEquals(120, this.hero.getExpToLevelUp());
        
        this.hero.addExp(130);
        assertEquals(3, this.hero.getLevel());
        assertEquals(10, this.hero.getExp());
        
        this.hero = new Hero("Test2", 100, 200, 100, 50);
        assertEquals(0,this.hero.getExp());
        assertEquals(50, this.hero.getExpToLevelUp());
        assertEquals(1, this.hero.getLevel());
        
        this.hero.incrementLevel();
        assertEquals(2, this.hero.getLevel());
        
        final long expToOneLevelUp = this.hero.getExpToLevelUp();
        final long expToSecondLevelUp = Math.round(expToOneLevelUp * Math.log10(expToOneLevelUp / 2.5));
        final long expToThirdLevelUp = Math.round(expToSecondLevelUp * Math.log10(expToSecondLevelUp / 2.5));
        this.hero.addExp(120);
        
        assertEquals(120 - expToSecondLevelUp - expToOneLevelUp, this.hero.getExp());
        assertEquals(4, this.hero.getLevel());
        assertEquals(expToThirdLevelUp, this.hero.getExpToLevelUp());
        
        for (int i = 0; i < 15; i++) {
            this.hero.addExp(this.hero.getExpToLevelUp());
        }
        
        assertEquals(10, this.hero.getLevel());
        assertEquals(0, this.hero.getExp());
        
        this.hero.incrementLevel();
        assertEquals(11, this.hero.getLevel());
        this.hero.addExp(10);
        assertEquals(0, this.hero.getExp());
        assertEquals(0, this.hero.getExpToLevelUp());
        
    }
    
    /**
     * Test if the hero can use the skill
     */
    @Test
    public void testSkills() {
        this.hero = new Hero("TestSkill", 100, 200, 100, 60);
        assertFalse(this.hero.increaseSpeed(10));
        assertFalse(this.hero.heal(10));
        
        this.hero.incrementLevel();
        assertEquals(2, this.hero.getLevel());
        assertTrue(this.hero.increaseSpeed(10));
        assertFalse(this.hero.heal(10));
        
        this.hero.incrementLevel();
        assertEquals(3, this.hero.getLevel());
        assertTrue(this.hero.increaseSpeed(10));
        assertFalse(this.hero.heal(10));
        
        this.hero.incrementLevel();
        assertEquals(4, this.hero.getLevel());
        assertTrue(this.hero.increaseSpeed(10));
        assertTrue(this.hero.heal(10));    
    }
}
