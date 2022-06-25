package com.unibo.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.unibo.model.Hero;
import com.unibo.model.items.Weapon;
import com.unibo.util.Position;
import com.unibo.util.WeaponStats;

public class TestWeapons {

    private final Hero hero;
    private Weapon weapon;

    public TestWeapons() {
        this.hero = new Hero("Tester", 100, 100, 100, 100);
        this.weapon = hero.getCurrentWeapon();
    }
    
    @Test
    public void testFists() {        
        assertEquals("Fists", weapon.getName());
        assertEquals("0", weapon.getId());
        assertEquals(4, weapon.getDamage());
        assertEquals(48, weapon.getRange());
        assertTrue(weapon.isPosNull());
        
        weapon.setPos(new Position(100, 250));
        assertFalse(weapon.isPosNull());
        assertEquals(new Position(100, 250), weapon.getPos());
        
        assertEquals(1, hero.getWeapons().size());
        assertEquals(0, hero.getCurrentWeaponIndex());
    }
        
    @Test
    public void testWeapons() {
        weapon = new Weapon(WeaponStats.LONGSWORD, "0");
        
        assertEquals("Longsword", weapon.getName());
        assertEquals("0", weapon.getId());
        assertEquals(10, weapon.getDamage());
        assertEquals(80, weapon.getRange());
        assertTrue(weapon.isPosNull());
        
        weapon.setPos(new Position(100, 250));
        assertFalse(weapon.isPosNull());
        assertEquals(new Position(100, 250), weapon.getPos());
        
        hero.addWeapons(weapon);
        assertNotEquals(weapon, hero.getCurrentWeapon());
        hero.switchWeapon();
        assertEquals(weapon, hero.getCurrentWeapon());
        
        assertEquals(2, hero.getWeapons().size());
        assertEquals(1, hero.getCurrentWeaponIndex());
        
        weapon = new Weapon(WeaponStats.GREATAXE, "0");

        hero.addWeapons(weapon);
        assertNotEquals(weapon, hero.getCurrentWeapon());
        hero.switchWeapon();
        assertEquals(weapon, hero.getCurrentWeapon());
        
        assertEquals(3, hero.getWeapons().size());
        assertEquals(2, hero.getCurrentWeaponIndex());
    }
}
