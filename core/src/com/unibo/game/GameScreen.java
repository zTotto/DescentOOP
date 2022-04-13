package com.unibo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.unibo.maps.Map;
import com.unibo.maps.MapImpl;
import com.unibo.model.Hero;
import com.unibo.model.Position;
import com.unibo.model.Weapon;
import com.unibo.view.HeroView;

/**
 * Game screen class.
 */
public class GameScreen implements Screen {
    final Descent game;

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private HeroView heroView;
    private float elapsedTime;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private final Map mappa = new MapImpl("maps/testmap.tmx", new Position(0,0));
    

    public GameScreen(final Descent game) {
        this.game = game;
        heroView = new HeroView(new Hero("Ross", 100, 200, new Weapon("Longsword", 10, 64, "0")));
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Descent.GAME_WIDTH, Descent.GAME_HEIGHT);

        batch = new SpriteBatch();
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        renderer = new OrthogonalTiledMapRenderer(mappa.getTiledMap());
    }

    @Override
    public void render(final float delta) {
        elapsedTime += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.setView(camera);
		renderer.render();
        camera.position.set(heroView.getHero().getPos().getxCoord(), heroView.getHero().getPos().getyCoord(), 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            batch.draw(heroView.getAttackText(elapsedTime), heroView.getHero().getPos().getxCoord(),
                    heroView.getHero().getPos().getyCoord());
        } else {
            batch.draw(heroView.getAnimFromDir(heroView.getDir(), elapsedTime), heroView.getHero().getPos().getxCoord(),
                    heroView.getHero().getPos().getyCoord());
        }
        batch.end();

        heroView.move();
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            heroView.attack();
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width / 2.5f;
        camera.viewportHeight = height / 2.5f;
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        dispose();

    }

    @Override
    public void dispose() {
    }

}
