package com.unibo.util;

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
 *
 */
public final class LineOfSight {

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

    public static Vector2 characterVector(final Character character) {
        final float x = character.getPos().getxCoord();
        final float y = character.getPos().getyCoord();
        return new Vector2(x, y);
    }
}
