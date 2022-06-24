import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.unibo.maps.DescentMap;
import com.unibo.maps.DescentMapImpl;
import com.unibo.model.Hero;
import com.unibo.util.Position;
import com.unibo.view.HeroView;

class Collision {
	
	DescentMap map = new DescentMapImpl(null, null, 0, null, null);
	Hero hero = new Hero(null, 0, 100, 0, 0);
	HeroView heroView = new HeroView(null, null, null);
	
	@Test
	void test() {
		Position startingPos = new Position((float) 100, (float) 100);
		Position arrivalPos = new Position((float) 200, (float) 100);
		map.validMovement(heroView, 100, 200);
		hero.moveRight();
		assert(hero.getPos().getxCoord() - startingPos.getxCoord() == 100);
		map.validMovement(null, 0, 0);
	}

}
