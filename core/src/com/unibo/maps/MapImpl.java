package com.unibo.maps;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.unibo.model.Character;
import com.unibo.model.Item;
import com.unibo.model.Position;
import com.unibo.util.Direction;
import com.unibo.util.Pair;

/**
 * Implementation of the map interface.
 */
public class MapImpl implements Map {

    private final TiledMapTileLayer collisionLayer;
    private final TiledMap map;
    private final int tileSize;
    private final int height;
    private final int width;
    private final Position startingPosition;
    private final List<Pair<Item, Position>> itemList = new ArrayList<Pair<Item, Position>>();

    /**
     * Constructor for a map.
     * @param path of the map
     * @param startingPos
     */
    public MapImpl(final String path, final Position startingPos) {
        super();
        this.map = new TmxMapLoader().load(path);
        this.collisionLayer = (TiledMapTileLayer) this.map.getLayers().get("Collision"); // by convention the collision
                                                                                         // layer is layer 0
        this.startingPosition = startingPos;
        tileSize = collisionLayer.getTileHeight();
        height = collisionLayer.getHeight();
        width = collisionLayer.getWidth();
    }

    @Override
    public boolean validMovement(final Character character, final Direction dir) {
        final Pair<Integer, Integer> pair = projectedMovement(character, dir);
        int convertedX = pair.getFirst();
        int convertedY = pair.getSecond();
        if (isOutOfBounds(pair)) {
            return false;
        } else {
            return collisionLayer.getCell(convertedX / tileSize, convertedY / tileSize).getTile().getProperties()
                    .containsKey("walkable");
        }
    }

    private boolean isOutOfBounds(final Pair<Integer, Integer> pair) {
        int x = pair.getFirst();
        int y = pair.getSecond();
        return (x >= width * tileSize || x <= 0 || y >= height * tileSize || y <= 0);
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
    public TiledMapTileLayer getCollisionLayer() {
        return collisionLayer;
    }

    private Pair<Integer, Integer> projectedMovement(final Character character, final Direction direction) {
        Pair<Integer, Integer> pair;
        int oldX = character.getPos().getxCoord();
        int oldY = character.getPos().getyCoord();
        switch (direction) {
        case RIGHT:
            pair = new Pair<>(oldX + 1, oldY);
            break;
        case LEFT:
            pair = new Pair<>(oldX - 1, oldY);
            break;
        case UP:
            pair = new Pair<>(oldX, oldY + 1);
            break;
        case DOWN:
            pair = new Pair<>(oldX, oldY - 1);
            break;
        default:
            throw new RuntimeException("invalid direction");
        }
        return pair;
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
