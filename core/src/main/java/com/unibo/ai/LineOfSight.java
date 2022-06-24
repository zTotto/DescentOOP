package com.unibo.ai;

import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.unibo.maps.DescentMap;
import com.unibo.model.Character;
import com.unibo.view.HeroView;
import com.unibo.view.LevelView;
import com.unibo.view.MobView;

/**
 * Utility class to determine a mob's line of sight
 */
public final class LineOfSight {
	
	private LineOfSight() {
		
	}
	
	/**
	 * Determines if the hero is seen by a particular mob.
	 * It does so by drawing a direct line between the mob and the hero
	 * and checking if that line intersects with a polygon, which Descentmap
	 * uses as walls or other material objects.
	 * Lineofsight starts from the center of the bottom of a mob's hitbox
	 * (most likely their feet).
	 * @param mob	mobview of the mob
	 * @level	levelview of the level the mob and hero are in
	 * @map		Descentmap in which the mob and hero are
	 * @return True if the mob can see the hero, false otherwise
	 */
    public static boolean isHeroSeen(final MobView mob, final LevelView level, final DescentMap map) {
        final HeroView hero = level.getHeroView();
        final Vector2 heroVector = characterVector(mob.getCharacter());
        final Vector2 mobVector = characterVector(hero.getCharacter());

        for (final PolygonMapObject polyMapObj : map.getCollisionLayer().getObjects()
                .getByType(PolygonMapObject.class)) {
            final Polygon polyObj = new Polygon(polyMapObj.getPolygon().getTransformedVertices());
            var verts = polyObj.getTransformedVertices();
            for (int i = 0; i < verts.length; i++) {
                verts[i] *= map.getUnitScale();
            }
            if (Intersector.intersectSegmentPolygon(mobVector, heroVector, polyObj)) {
                return false;
            }
        }
        return true;
    }
    
    /**
	 * @param Character		the character
	 * @return a Vector2 with the character's position as components
	 */
    public static Vector2 characterVector(final Character character) {
        final float x = character.getPos().getxCoord();
        final float y = character.getPos().getyCoord();
        return new Vector2(x, y);
    }
}
