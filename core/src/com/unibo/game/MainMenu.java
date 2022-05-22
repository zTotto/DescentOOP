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
 * Main Menu to star the game, open the settings or quit.
 */
public class MainMenu implements Screen {

    private final Descent game;
    private final OrthographicCamera camera;
    private final Skin skin;
    private final Stage stage;
    private final Table table;

    /**
     * Constructor for the main menu.
     * 
     * @param game
     */
    public MainMenu(final Descent game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, Descent.GAME_WIDTH, Descent.GAME_HEIGHT);
        this.skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        this.stage = new Stage(new ScreenViewport());
        this.table = new Table();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);

        final Label label = new Label("DESCENT", skin);
        final TextButton play = new TextButton("Play", skin);
        final TextButton customLevels = new TextButton("Custom Levels", skin);
        final TextButton settings = new TextButton("Settings", skin);
        final TextButton quit = new TextButton("Quit", skin);

        play.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                Gdx.app.postRunnable(() -> dispose());

                LevelListReader reader = new LevelListReader(Gdx.files.internal("levels/"), false);
                if (reader.getLevels().size() == 0) {
                    game.setScreen(new LevelLoadErrorScreen(game, List.of("NO VALID LEVELS!"), reader));
                } else if (!reader.getErrorList().isEmpty()) {
                    game.setScreen(new LevelLoadErrorScreen(game, reader.getErrorList(), reader));
                } else {
                    game.setScreen(new GameScreen(game, reader));
                }
            }
        });
        customLevels.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                game.setScreen(new CustomLevelsScreen(game));
            }
        });
        settings.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                game.setScreen(new SettingsMenu(game));
            }
        });
        quit.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                Gdx.app.exit();
            }
        });

        table.add(label).spaceBottom(70).row();
        table.add(play).uniform().fill().spaceBottom(10).row();
        table.add(customLevels).uniform().fill().spaceBottom(10).row();
        table.add(settings).uniform().fill().spaceBottom(10).row();
        table.add(quit).uniform().fill().spaceBottom(10);

        stage.addActor(table);
    }

    @Override
    public void render(final float delta) {
        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
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

    /**
     * @return an already instanced main menu
     */
    public MainMenu getMainMenu() {
        return this;
    }

}
