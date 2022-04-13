package com.unibo.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Game class.
 */
public class Descent extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	public static int GAME_WIDTH = 900;
	public static int GAME_HEIGHT = 664;

	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont(); // use libGDX's default Arial font
		this.setScreen(new GameScreen(this));
	}

	public void render() {
		super.render(); // important!
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
	}
}