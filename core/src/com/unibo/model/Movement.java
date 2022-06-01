package com.unibo.model;

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
    public void executeCommand(final CharacterView characterView) {
        final Character character = characterView.getCharacter();
        final int xPosition = character.getPos().getxCoord();
        final int yPosition = character.getPos().getyCoord();
        final int deltaMovement = (int) (character.getSpeed() * Gdx.graphics.getDeltaTime());
        final Map map = character.getCurrentMap();
        characterView.setDir(this.direction);
        characterView.setIsMoving(true);

        if (this.direction == Direction.LEFT
                && map.validMovement(characterView, xPosition - deltaMovement, yPosition)) {
            character.setPos(xPosition - deltaMovement, yPosition);
        }

        if (this.direction == Direction.RIGHT
                && map.validMovement(characterView, xPosition + deltaMovement, yPosition)) {
            character.setPos(xPosition + deltaMovement, yPosition);
        }

        if (this.direction == Direction.UP && map.validMovement(characterView, xPosition, yPosition + deltaMovement)) {
            character.setPos(xPosition, yPosition + deltaMovement);
        }

        if (this.direction == Direction.DOWN
                && map.validMovement(characterView, xPosition, yPosition - deltaMovement)) {
            character.setPos(xPosition, yPosition - deltaMovement);
        }
    }

}
