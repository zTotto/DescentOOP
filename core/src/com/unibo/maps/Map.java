package com.unibo.maps;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.unibo.model.Character;
import com.unibo.model.Item;
import com.unibo.model.Position;
import com.unibo.util.Direction;

/**
 * Interface for a map.
 */
public interface Map {
    boolean validMovement(Character character, Direction dir);

    void addItem(Item item, Position pos); // come segnalare posizione invalida?

    // void addItem(Item item);

    Position getStartingPosition();

    TiledMapTileLayer getLayer(int layerNumber);

    TiledMapTileLayer getLayer(String path);

    TiledMapTileLayer getCollisionLayer();

    TiledMap getTiledMap();
}
