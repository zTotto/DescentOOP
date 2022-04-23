package com.unibo.maps;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.unibo.model.Character;
import com.unibo.model.Item;
import com.unibo.util.Direction;
import com.unibo.util.Pair;
import com.unibo.util.Position;
import com.unibo.view.CharacterView;

/**
 * Implementation of the map interface.
 */
public class MapImpl implements Map {

    private final MapLayer collisionLayer;
    private final TiledMap map;
    private final Position startingPosition;
    private final List<Pair<Item, Position>> itemList = new ArrayList<Pair<Item, Position>>();

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
        this.startingPosition = startingPos;
    }

    @Override
    public boolean validMovement(final CharacterView charView, final int newX, final int newY) {
        Rectangle rect = charView.getCharRect();
        rect.setPosition(newX - (int) (charView.getWidth() / 2), newY);
        for (RectangleMapObject rectObj : collisionLayer.getObjects().getByType(RectangleMapObject.class)) {
            if (Intersector.overlaps(rect, rectObj.getRectangle())) {
                return false;
            }
        }
        return true;
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
