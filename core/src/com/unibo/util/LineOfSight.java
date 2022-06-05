package com.unibo.util;

import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.unibo.maps.Map;
import com.unibo.model.Character;
import com.unibo.view.HeroView;
import com.unibo.view.LevelView;
import com.unibo.view.MobView;

public final class LineOfSight {
	
	public static boolean isHeroSeen(MobView mob, LevelView level, Map map) {
		final HeroView hero = level.getHeroView();
		Vector2 heroVector = characterVector(mob.getCharacter());
		Vector2 mobVector = characterVector(hero.getCharacter());
		
		for (final PolygonMapObject polyMapObj : map.getCollisionLayer().getObjects().getByType(PolygonMapObject.class)) {
			Polygon polyObj = new Polygon(polyMapObj.getPolygon().getTransformedVertices());
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
	
	public static Vector2 characterVector(Character character) {
		final int x = character.getPos().getxCoord();
		final int y = character.getPos().getyCoord();
		return new Vector2(x,y);
	}
}
