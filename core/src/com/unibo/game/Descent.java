package com.unibo.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Game class.
 */
public class Descent extends Game {
    private SpriteBatch batch;
    private BitmapFont font;
    /**
     * Game Width.
     */
    public static final int GAME_WIDTH = 1600;
    /**
     * Game Height.
     */
    public static final int GAME_HEIGHT = 900;

    /**
     * Constructor for this class.
     */
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont(); // use libGDX's default Arial font
        this.setScreen(new GameScreen(this));
    }

    /**
     * Render class for the application.
     */
    public void render() {
        super.render(); // important!
    }

    /**
     * Class to dispose textures, fonts, sounds etc.
     */
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
