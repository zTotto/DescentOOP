package com.unibo.game;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.Gdx;

/**
 * Pause Menu for the main game.
 */
public class PauseMenu {

    private final Table menu;
    private final Stage stage;
    private final Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

    /**
     * Constructor for the pause menu.
     * 
     * @param game the main game screen
     */
    public PauseMenu(final GameScreen game) {
        final GameScreen gameScreen = game;
        menu = new Table();
        menu.setFillParent(true);
        stage = new Stage(new ScreenViewport());

        TextButton resume = new TextButton("Resume Game", skin);
        resume.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                gameScreen.disablePause();
                menu.setVisible(false);
            }
        });
        TextButton mainMenu = new TextButton("Main Menu", skin);
        mainMenu.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                game.getGame().setScreen(new MainMenu(game.getGame()));
            }
        });
        TextButton save = new TextButton("Save Game", skin);
        TextButton quit = new TextButton("Quit Game", skin);

        menu.add(resume).uniform().fill().spaceBottom(10);
        menu.row();
        menu.add(save).uniform().fill().spaceBottom(10);
        menu.row();
        menu.add(mainMenu).uniform().fill().spaceBottom(10);
        menu.row();
        quit.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                Gdx.app.exit();
            }
        });
        menu.add(quit).uniform().fill();

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
