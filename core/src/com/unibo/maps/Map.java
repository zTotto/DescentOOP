package com.unibo.maps;

import java.util.Optional;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.unibo.view.CharacterView;
import com.unibo.model.items.Item;
import com.unibo.util.Position;

/**
 * Interface for a map.
 */
public interface Map {
    /**
     * Method to check whether a movement is valid.
     * 
     * @param charView view of the character
     * @param newX     x coordinate to test
     * @param newY     y coordinate to test
     * @return true if the movement is valid.
     */
    Boolean validMovement(CharacterView charView, int newX, int newY);

    void addItem(Item item, Position pos); // come segnalare posizione invalida?

    Position getStartingPosition();

    TiledMapTileLayer getLayer(int layerNumber);

    TiledMapTileLayer getLayer(String path);

    /**
     * Getter for collision objects.
     * 
     * @return the collision layer
     */
    MapLayer getCollisionLayer();

    /**
     * Getter for teleporting tiles.
     * 
     * @return the teleport tiles layer
     */
    Optional<MapLayer> getTeleportLayer();

    /**
     * Getter for special tiles (like slowing or damaging tiles).
     * 
     * @return the special tiles layer
     */
    Optional<MapLayer> getSpecialTilesLayer();

    TiledMap getTiledMap();

    /**
     * Checks if a character is in a teleport tile.
     * 
     * @param charView
     * @return true if the character is in a teleport tile
     */
    Boolean checkTeleport(CharacterView charView);

    /**
     * Checks if a character is on a special tile.
     * 
     * @param charView
     * @return true if a player is damaged
     */
    Boolean checkDamageTile(CharacterView charView);
}
