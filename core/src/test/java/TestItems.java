import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.unibo.model.Hero;
import com.unibo.model.items.ConsumableItem;
import com.unibo.model.items.ManaPotion;
import com.unibo.model.items.WearableItem;
import com.unibo.util.Pair;
import com.unibo.util.Position;

/**
 * 
 * Class to test some Items
 *
 */
public class TestItems {

    private final Hero hero;

    public TestItems() {
        this.hero = new Hero("Tester", 100, 100, 100, 100);
    }

    @Test
    public void testManaPotion() {
        final ConsumableItem manaPotion = new ManaPotion("Mana Potion", "0", 0.5);
        
        assertEquals("Mana Potion", manaPotion.getName());
        assertEquals("0", manaPotion.getId());
        assertEquals(0.5, manaPotion.getModifier());
        assertTrue(manaPotion.isPosNull());

        manaPotion.setPos(new Position(100, 250));
        assertFalse(manaPotion.isPosNull());
        assertEquals(new Position(100, 250), manaPotion.getPos());

        hero.getInv().addItem(manaPotion);
        assertTrue(hero.getInv().getInv().contains(new Pair<>(manaPotion, 1)));
        assertEquals(100, hero.getMaxMana());

        assertTrue(manaPotion.canUse(hero));
        hero.useManaPotion();
        assertEquals(150, hero.getMaxMana());
        assertFalse(hero.getInv().getInv().contains(new Pair<>(manaPotion, 1)));
    }

    @Test
    public void testWearableItem() {
        WearableItem wearable = new WearableItem.Builder("Wearable", "0")
                                                .health(0.5)
                                                .build();
        
        assertEquals("Wearable", wearable.getName());
        assertEquals("0", wearable.getId());
        assertTrue(wearable.isPosNull());
        
        wearable.setPos(new Position(25, 300));
        assertFalse(wearable.isPosNull());
        assertEquals(new Position(25, 300), wearable.getPos());
        
        assertEquals(100, hero.getMaxHp());
        wearable.wear(hero);
        assertEquals(150, hero.getMaxHp());

        wearable = new WearableItem.Builder("Wearable", "0")
                                   .power(0.25)
                                   .exp(50)
                                   .build();
        
        wearable.setPos(new Position(200, 1000));
        assertFalse(wearable.isPosNull());
        assertEquals(new Position(200, 1000), wearable.getPos());
        
        final int damage = hero.getCurrentWeapon().getDamage();
        wearable.wear(hero);
        assertTrue(damage < hero.getCurrentWeapon().getDamage());
        assertEquals(50, hero.getExp());

    }
    
    @Test
    public void testWearableItemBuilder() {
        assertThrows(IllegalStateException.class, () -> new WearableItem.Builder("Wearable", "0")
                                                                        .build());

        assertThrows(IllegalStateException.class, () -> new WearableItem.Builder("Wearable", "0")
                                                                        .health(0)
                                                                        .power(0)
                                                                        .exp(0)
                                                                        .build());
        
        assertThrows(IllegalStateException.class, () -> new WearableItem.Builder("Wearable", "0")
                                                                        .exp(10_000)
                                                                        .build());
        
        assertThrows(IllegalStateException.class, () -> new WearableItem.Builder("Wearable", "0")
                                                                        .power(5)
                                                                        .build());
    }
}
