package com.unibo.maps;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.unibo.model.Character;
import com.unibo.model.Item;
import com.unibo.util.Direction;
import com.unibo.util.Position;
import com.unibo.view.CharacterView;

/**
 * Interface for a map.
 */
public interface Map {
    /**
     * Method to check whether a movement is valid.
     * @param charView view of the character
     * @param newX x coordinate to test
     * @param newY y coordinate to test
     * @return true if the movement is valid.
     */
    //boolean validMovement(Character character, Direction dir);
    boolean validMovement(CharacterView charView, int newX, int newY);

    void addItem(Item item, Position pos); // come segnalare posizione invalida?

    // void addItem(Item item);

    Position getStartingPosition();

    TiledMapTileLayer getLayer(int layerNumber);

    TiledMapTileLayer getLayer(String path);

    MapLayer getCollisionLayer();

    TiledMap getTiledMap();
}
