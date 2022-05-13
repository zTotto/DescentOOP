package com.unibo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.unibo.keybindings.KeyBindings;
import com.unibo.model.Character;

/**
 * Skill menu.
 *
 */
public class SkillMenu {

    private final Table menu;
    private final Stage stage;
    private final Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

    /**
     * Constructor for the skill menu
     * 
     * @param game the main game screen
     */
    /**
     * @param game
     * @param character
     */
    public SkillMenu(final GameScreen game, final Character character) {
        final GameScreen gameScreen = game;
        menu = new Table();
        menu.setFillParent(true);
        stage = new Stage(new ScreenViewport());

        final Label label = new Label("SKILLS", skin);
        label.setColor(Color.GREEN);
        label.setFontScale(1.2f);
        menu.add(label).spaceBottom(70).row();

        final TextButton speedUp = new TextButton("Speed Up: "
                + Input.Keys.toString(KeyBindings.INCREASES_SPEED.getKey()) + " Lvl: " + character.levelToSpeedUp(),
                skin);
        speedUp.setColor(Color.YELLOW);
        speedUp.setTouchable(Touchable.disabled);
        menu.add(speedUp).uniform().fill().spaceBottom(10);
        menu.row();

        final TextButton heal = new TextButton(
                "Heal: " + Input.Keys.toString(KeyBindings.HEAL.getKey()) + " Lvl: " + character.levelToHeal(), skin);
        heal.setColor(Color.YELLOW);
        heal.setTouchable(Touchable.disabled);
        menu.add(heal).uniform().fill().spaceBottom(50);
        menu.row();

        final TextButton resume = new TextButton("Resume Game", skin);
        menu.add(resume).uniform().fill();
        menu.row();

        resume.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                gameScreen.disablePause();
            }
        });

        stage.addActor(menu);
    }

    /**
     * @return the menu
     */
    public Table getMenu() {
        return menu;
    }

    /**
     * @return the stage
     */
    public Stage getStage() {
        return stage;
    }
}
