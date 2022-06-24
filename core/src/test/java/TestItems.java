import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.unibo.model.Hero;
import com.unibo.model.items.ConsumableItem;
import com.unibo.model.items.ManaPotion;
import com.unibo.util.Position;

public class TestItems {
    private ConsumableItem i;
    private final Hero h;

    public TestItems() {
        this.i = new ManaPotion("Mana Potion", "0", 0.5);
        h = new Hero("Tester", 100, 100, 100, 100);
    }
    
    @Test
    public void testManaPotion() {
        assertEquals(0.5, i.getModifier());
        assertEquals("0", i.getId());
        assertEquals("Mana Potion", i.getName());
        assertTrue(i.isPosNull());
        
        i.setPos(new Position(100, 250));
        assertFalse(i.isPosNull());
        
        assertTrue(i.canUse(h));
        h.getInv().addItem(i);
        h.useManaPotion();
        assertEquals(150, h.getMaxMana());
    }
}
