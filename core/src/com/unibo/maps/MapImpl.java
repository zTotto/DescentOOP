package com.unibo.maps;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.unibo.model.Character;
import com.unibo.model.Item;
import com.unibo.model.Position;
import com.unibo.util.Direction;
import com.unibo.util.Pair;

public class MapImpl implements Map {
	private final TiledMapTileLayer collisionLayer;
	private final TiledMap map;
	private final static int TILE_SIZE = 64; 
	private final Position startingPosition;
	
	
	public MapImpl(final String path,final Position startingPos) {
			super();
			this.map = new TmxMapLoader().load(path);
			this.collisionLayer = (TiledMapTileLayer) this.map.getLayers().get("Collision"); // by convention the collision layer is layer 0
			this.startingPosition = startingPos;
		}
	
	
	@Override
	public boolean validMovement(Character character, Direction dir) {
		final Pair<Integer, Integer> pair = coordinatesConverter(character, dir);
		int convertedX = pair.getFirst();
		int convertedY = pair.getSecond();
		return collisionLayer.getCell(convertedX/TILE_SIZE, convertedY/TILE_SIZE).
				getTile().getProperties().containsKey("walkable");
	}

	@Override
	public void addItem(Item item, Position pos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addItem(Item item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Position getStartingPosition() {
		return startingPosition;
	}

	@Override
	public TiledMapTileLayer getLayer(int layerNumber) {
		return (TiledMapTileLayer) map.getLayers().get(layerNumber);
	}

	@Override
	public TiledMapTileLayer getCollisionLayer() {
		return collisionLayer;
	}
	
	private Pair<Integer, Integer> coordinatesConverter (final Character character, final Direction Direction){
		Pair<Integer, Integer> pair;
		int oldX = character.getPos().getxCoord();
		int oldY = character.getPos().getyCoord();
		switch (Direction) {
		case RIGHT:
			pair = new Pair<>(oldX+1, oldY);
			break;
		case LEFT:
			pair = new Pair<>(oldX-1, oldY);
			break;
		case UP:
			pair = new Pair<>(oldX, oldY+1);
			break;
		case DOWN:
			pair = new Pair<>(oldX, oldY-1);
			break;
		default:
			throw new RuntimeException("invalid direction");
		}
		return pair;
	}
}
