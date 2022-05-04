package com.unibo.keyBindings;

import com.badlogic.gdx.Gdx;
import com.unibo.maps.Map;
import com.unibo.util.Direction;
import com.unibo.view.CharacterView;

public class Movement implements Command {
	
	private final Direction direction;
	
	public Movement(Direction direction) {
		this.direction = direction;
	}

	@Override
	public void Execute(CharacterView character) {
		int heroX = character.getCharacter().getPos().getxCoord();
        int heroY = character.getCharacter().getPos().getyCoord();
        int deltaMovement = (int) (character.getCharacter().getSpeed() * Gdx.graphics.getDeltaTime());
        Map map = character.getCharacter().getCurrentMap();
		character.setDir(this.direction);
		
		if (this.direction == Direction.LEFT && map.validMovement(character, heroX - deltaMovement, heroY)) {
			character.getCharacter().setPos(heroX - deltaMovement, heroY);
		}
		
		if (this.direction == Direction.RIGHT && map.validMovement(character, heroX + deltaMovement, heroY)) {
			character.getCharacter().setPos(heroX + deltaMovement, heroY);
		}
		
		if (this.direction == Direction.UP && map.validMovement(character, heroX, heroY + deltaMovement)) {
			character.getCharacter().setPos(heroX, heroY + deltaMovement);
		}
		
		if (this.direction == Direction.DOWN && map.validMovement(character, heroX, heroY - deltaMovement)) {
			character.getCharacter().setPos(heroX, heroY - deltaMovement);
		}		
	}

}
