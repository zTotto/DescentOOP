package com.unibo.maps;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.unibo.model.items.Item;
import com.unibo.util.Pair;
import com.unibo.util.Position;
import com.unibo.view.CharacterView;

/**
 * Implementation of the map interface.
 */
public class MapImpl implements Map {

    private final MapLayer collisionLayer;
    private final MapLayer teleportLayer;
    private final TiledMap map;
    private final Position startingPosition;
    private final List<Pair<Item, Position>> itemList = new ArrayList<>();

    /**
     * Constructor for a map.
     * 
     * @param path        of the map
     * @param startingPos of the hero
     */
    public MapImpl(final String path, final Position startingPos) {
        super();
        this.map = new TmxMapLoader().load(path);
        this.collisionLayer = this.map.getLayers().get("objects");
        this.teleportLayer = this.map.getLayers().get("teleports");
        this.startingPosition = startingPos;
    }

    @Override
    public boolean validMovement(final CharacterView charView, final int newX, final int newY) {
        return polyScanner(charView, new Position(newX, newY), collisionLayer, false);
    }

    /**
     * Checks if a character is on a teleport tile.
     * 
     * @param charView
     * @return true if a player is teleported
     */
    public Boolean checkTeleport(final CharacterView charView) {
        return polyScanner(charView, new Position(charView.getCharacter().getPos().getxCoord(),
                charView.getCharacter().getPos().getyCoord()), teleportLayer, true);
    }

    private Boolean polyScanner(final CharacterView charView, final Position pos, final MapLayer layer,
            final Boolean teleportCharacter) {
        final Rectangle rect = charView.getCharRect();
        rect.setPosition(pos.getxCoord() - (int) (rect.getWidth() / 2), pos.getyCoord());
        Polygon poly = new Polygon(new float[] { rect.x, rect.y, rect.x + rect.width, rect.y, rect.x + rect.width,
                rect.y + rect.height, rect.x, rect.y + rect.height });
        for (final PolygonMapObject polyMapObj : layer.getObjects().getByType(PolygonMapObject.class)) {
            Polygon polyObj = new Polygon(polyMapObj.getPolygon().getTransformedVertices());
            var verts = polyObj.getTransformedVertices();
            for (int i = 0; i < verts.length; i++) {
                verts[i] /= 6;
            }
            if (Intersector.overlapConvexPolygons(poly, polyObj)) {
                if (teleportCharacter) {
                    charView.getCharacter().setPos((int) polyMapObj.getProperties().get("X"),
                            (int) polyMapObj.getProperties().get("Y"));
                }
                return teleportCharacter;
            }
        }
        return !teleportCharacter;
    }

    @Override
    public void addItem(final Item item, final Position pos) {
        itemList.add(new Pair<>(item, pos));
    }

    @Override
    public TiledMapTileLayer getLayer(final int layerNumber) {
        return (TiledMapTileLayer) map.getLayers().get(layerNumber);
    }

    @Override
    public TiledMapTileLayer getLayer(final String path) {
        return (TiledMapTileLayer) map.getLayers().get(path);
    }

    @Override
    public MapLayer getCollisionLayer() {
        return collisionLayer;
    }

    @Override
    public Position getStartingPosition() {
        return startingPosition;
    }

    @Override
    public TiledMap getTiledMap() {
        return this.map;
    }
}
