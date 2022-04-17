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
        int heroX = getHero().getPos().getxCoord();
        int heroY = getHero().getPos().getyCoord();
        Map map = getHero().getCurrentMap();
        setDir(Direction.STILL);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && map.validMovement(getHero(), Direction.LEFT)) {
            setDir(Direction.LEFT);
            getHero().setPos(heroX - (int) (getHero().getSpeed() * Gdx.graphics.getDeltaTime()), heroY);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && map.validMovement(getHero(), Direction.RIGHT)) {
            setDir(Direction.RIGHT);
            getHero().setPos(heroX + (int) (getHero().getSpeed() * Gdx.graphics.getDeltaTime()), heroY);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && map.validMovement(getHero(), Direction.UP)) {
            setDir(Direction.UP);
            getHero().setPos(heroX, heroY + (int) (getHero().getSpeed() * Gdx.graphics.getDeltaTime()));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && map.validMovement(getHero(), Direction.DOWN)) {
            setDir(Direction.DOWN);
            getHero().setPos(heroX, heroY - (int) (getHero().getSpeed() * Gdx.graphics.getDeltaTime()));
        }
    }
}
