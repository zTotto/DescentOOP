package com.unibo.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.unibo.model.Hero;

/**
 * Healthbar for the main character.
 */
public class Healthbar extends ProgressBar {

    private final Stage stage;

    /**
     * Constructor for the healthbar.
     * 
     * @param width
     * @param height
     */
    public Healthbar(final int width, final int height) {
        super(0f, 1f, 0.01f, false, new ProgressBarStyle());
        stage = new Stage(new ScreenViewport());

        getStyle().background = this.getColoredDrawable(width, height, Color.RED);
        getStyle().knob = this.getColoredDrawable(0, height, Color.GREEN);
        getStyle().knobBefore = this.getColoredDrawable(width, height, Color.GREEN);

        setWidth(width);
        setHeight(height);
        this.setValue(1f);
        stage.addActor(this);
    }

    private Drawable getColoredDrawable(final int width, final int height, final Color color) {
        Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();

        return drawable;
    }

    /**
     * Updates the health bar.
     * @param hero
     */
    public void update(final Hero hero) {
        this.setValue((float) hero.getCurrentHp() / (float) hero.getMaxHp());
    }

    /**
     * @return the healthbar stage
     */
    public Stage getStage() {
        return this.stage;
    }
}
