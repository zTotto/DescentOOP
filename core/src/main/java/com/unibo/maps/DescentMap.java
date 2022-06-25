package com.unibo.maps;

import java.util.Optional;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.unibo.view.CharacterView;
import com.unibo.util.Position;

/**
 * Interface for a map created with Tiled
 */
public interface DescentMap {
    /**
     * Method to check whether a movement is valid.
     * 
     * @param charView view of the character
     * @param newX     x coordinate to test
     * @param newY     y coordinate to test
     * @return true if the movement is valid.
     */
    Boolean validMovement(CharacterView charView, float newX, float newY);

    /**
     * @return the starting position of the Hero.
     */
    Position getStartingPosition();

    /**
     * Getter for a specific layer in the TiledMap.
     * 
     * @param layerNumber number of the layer
     * @return a TiledMapLayer
     */
    TiledMapTileLayer getLayer(int layerNumber);

    /**
     * Getter for a specific layer in the TiledMap.
     * 
     * @param path a string indicating the name of the layer
     * @return a TiledMapLayer
     */
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

    /**
     * Getter for the Tiled Map set in MapImpl.
     * 
     * @return the Tiled Map
     */
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

    /**
     * Getter for the float number used to rescale the map to better fit the game
     * screen.
     * 
     * @return the scale of the map
     */
    Float getUnitScale();
    
    /**
     * Getter for the path to the background song of the map.
     * @return an optional of the song's path
     */
    String getBackgroundSong();
    
    /**
     * Setter for the path to the background song of the map.
     * @param path a String with the song's file path
     */
     void setBackgroundSong(String path);
}
