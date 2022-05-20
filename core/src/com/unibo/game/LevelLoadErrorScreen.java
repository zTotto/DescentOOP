package com.unibo.game;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.unibo.util.LevelListReader;

/**
 * Game over screen.
 */
public class LevelLoadErrorScreen implements Screen {

    private final Descent game;
    private final OrthographicCamera camera;
    private final Skin skin;
    private final Stage stage;
    private final Table table;
    private final List<String> msg;
    private final LevelListReader lvlReader;

    /**
     * Constructor for the game over menu.
     * 
     * @param game
     * @param errorMsg Message error
     * @param reader   LevelListReader for the game
     */
    public LevelLoadErrorScreen(final Descent game, final List<String> errorMsg, final LevelListReader reader) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, Descent.GAME_WIDTH, Descent.GAME_HEIGHT);
        this.skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        this.stage = new Stage(new ScreenViewport());
        this.table = new Table();
        this.msg = errorMsg;
        lvlReader = reader;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);

        final Label label = new Label(msg.toString(), skin);
        final TextButton mainMenu = new TextButton("Back to main menu", skin);
        final TextButton quit = new TextButton("Quit", skin);
        final TextButton loadGame = new TextButton("Continue", skin);

        mainMenu.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                Gdx.app.postRunnable(() -> dispose());
                game.setScreen(new MainMenu(game));
            }
        });
        quit.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                Gdx.app.postRunnable(() -> dispose());
                Gdx.app.exit();
            }
        });
        loadGame.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                Gdx.app.postRunnable(() -> dispose());
                game.setScreen(new GameScreen(game, lvlReader));
            }
        });

        table.add(label).spaceBottom(70).row();
        if (!msg.get(0).equals("NO VALID LEVELS!")) {
            table.add(loadGame).uniform().fill().spaceBottom(10).row();
        }
        table.add(mainMenu).uniform().fill().spaceBottom(10).row();
        table.add(quit).uniform().fill();

        stage.addActor(table);
    }

    @Override
    public void render(final float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(final int width, final int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
