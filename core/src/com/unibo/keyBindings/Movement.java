package com.unibo.keyBindings;

import com.badlogic.gdx.Gdx;
import com.unibo.maps.Map;
import com.unibo.util.Direction;
import com.unibo.view.CharacterView;

/**
 * Class that has an execute method, in order to move a character based on a
 * direction.
 *
 */
public class Movement implements Command {

    private final Direction direction;

    /**
     * Constructor for the Movement class.
     * 
     * @param direction movement direction of the character
     */
    public Movement(final Direction direction) {
        this.direction = direction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void executeCommand(final CharacterView character) {
        int xPosition = character.getCharacter().getPos().getxCoord();
        int yPosition = character.getCharacter().getPos().getyCoord();
        int deltaMovement = (int) (character.getCharacter().getSpeed() * Gdx.graphics.getDeltaTime());
        Map map = character.getCharacter().getCurrentMap();
        character.setDir(this.direction);

        if (this.direction == Direction.LEFT && map.validMovement(character, xPosition - deltaMovement, yPosition)) {
            character.getCharacter().setPos(xPosition - deltaMovement, yPosition);
        }

        if (this.direction == Direction.RIGHT && map.validMovement(character, xPosition + deltaMovement, yPosition)) {
            character.getCharacter().setPos(xPosition + deltaMovement, yPosition);
        }

        if (this.direction == Direction.UP && map.validMovement(character, xPosition, yPosition + deltaMovement)) {
            character.getCharacter().setPos(xPosition, yPosition + deltaMovement);
        }

        if (this.direction == Direction.DOWN && map.validMovement(character, xPosition, yPosition - deltaMovement)) {
            character.getCharacter().setPos(xPosition, yPosition - deltaMovement);
        }
    }

}
