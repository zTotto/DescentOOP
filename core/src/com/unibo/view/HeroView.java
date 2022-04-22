package com.unibo.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.unibo.maps.Map;
import com.unibo.model.Hero;
import com.unibo.util.Direction;

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
        Map map = getCharacter().getCurrentMap();
        setDir(Direction.STILL);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && map.validMovement(getCharacter(), Direction.LEFT)) {
            setDir(Direction.LEFT);
            getCharacter().setPos(heroX - (int) (getCharacter().getSpeed() * Gdx.graphics.getDeltaTime()), heroY);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && map.validMovement(getCharacter(), Direction.RIGHT)) {
            setDir(Direction.RIGHT);
            getCharacter().setPos(heroX + (int) (getCharacter().getSpeed() * Gdx.graphics.getDeltaTime()), heroY);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && map.validMovement(getCharacter(), Direction.UP)) {
            setDir(Direction.UP);
            getCharacter().setPos(heroX, heroY + (int) (getCharacter().getSpeed() * Gdx.graphics.getDeltaTime()));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && map.validMovement(getCharacter(), Direction.DOWN)) {
            setDir(Direction.DOWN);
            getCharacter().setPos(heroX, heroY - (int) (getCharacter().getSpeed() * Gdx.graphics.getDeltaTime()));
        }
    }
}
