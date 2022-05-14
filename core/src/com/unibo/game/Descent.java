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
    public static final int GAME_WIDTH = 1300; // 1600;
    /**
     * Game Height.
     */
    public static final int GAME_HEIGHT = 600; // 900;

    /**
     * Constructor for this class.
     */
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont(); // use libGDX's default Arial font
        this.setScreen(new MainMenu(this));
    }

    /**
     * Render class for the application.
     */
    @Override
    public void render() {
        super.render(); // important!
    }

    /**
     * Class to dispose textures, fonts, sounds etc.
     */
    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
