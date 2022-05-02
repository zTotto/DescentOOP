package com.unibo.view;

import com.badlogic.gdx.Gdx;
import com.unibo.maps.Map;
import com.unibo.model.Hero;
import com.unibo.util.Direction;
import com.unibo.util.KeyBindings;

/**
 * Class for the view of the hero.
 */
public class HeroView extends CharacterView {

    /**
     * Constructor for the view.
     * 
     * @param hero the hero model
     * @param path of the hero movement animation
     */
    public HeroView(final Hero hero, final String path) {
        super(hero, path, "audio/sounds/Hadouken.mp3");
    }

    /**
     * Moves the hero depending on the pressed key.
     */
    public void move() {
        int heroX = getCharacter().getPos().getxCoord();
        int heroY = getCharacter().getPos().getyCoord();
        int deltaMovement = (int) (getCharacter().getSpeed() * Gdx.graphics.getDeltaTime());
        Map map = getCharacter().getCurrentMap();
        setDir(Direction.STILL);
        if (Gdx.input.isKeyPressed(KeyBindings.MOVE_LEFT.getKey()) && map.validMovement(this, heroX - deltaMovement, heroY)) {         
            setDir(Direction.LEFT);
            getCharacter().setPos(heroX - deltaMovement, heroY);
        }
        if (Gdx.input.isKeyPressed(KeyBindings.MOVE_RIGHT.getKey()) && map.validMovement(this, heroX + deltaMovement, heroY)) {
            setDir(Direction.RIGHT);
            getCharacter().setPos(heroX + deltaMovement, heroY);
        }
        if (Gdx.input.isKeyPressed(KeyBindings.MOVE_UP.getKey()) && map.validMovement(this, heroX, heroY + deltaMovement)) {
            setDir(Direction.UP);
            getCharacter().setPos(heroX, heroY + deltaMovement);
        }
        if (Gdx.input.isKeyPressed(KeyBindings.MOVE_DOWN.getKey()) && map.validMovement(this, heroX, heroY - deltaMovement)) {
            setDir(Direction.DOWN);
            getCharacter().setPos(heroX, heroY - deltaMovement);
        }
    }
}
