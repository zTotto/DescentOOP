package com.unibo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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
    private final Descent game;

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private HeroView heroView;
    private float elapsedTime;
    private OrthogonalTiledMapRenderer renderer;
    private final Map mappa = new MapImpl("maps/testmap.tmx", new Position(64, 1016));
    private final Music soundtrack;
    private float attackTime;

    public GameScreen(final Descent game) {
        this.game = game;
        heroView = new HeroView(new Hero("Ross", 100, 200, new Weapon("Longsword", 10, 64, "0")));
        heroView.getHero().setAttackSound("audio/sounds/Hadouken.mp3");
        soundtrack = Gdx.audio.newMusic(Gdx.files.internal("audio/backgroundsong.mp3"));
        soundtrack.setLooping(true);
        soundtrack.play();
        soundtrack.setVolume(0.4f);
        heroView.getHero().setCurrentMap(mappa);
        heroView.getHero().setPos(heroView.getHero().getCurrentMap().getStartingPosition());
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
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !heroView.isAttacking) {
            heroView.isAttacking = true;
            heroView.getHero().getAttackSound().play();
            heroView.attack();
        }
        if (heroView.isAttacking) {
            attackTime += Gdx.graphics.getDeltaTime();
            batch.draw(heroView.getAttackText(elapsedTime),
                    heroView.getHero().getPos().getxCoord() - (int) (heroView.getWidth() / 2),
                    heroView.getHero().getPos().getyCoord());
        } else {
            batch.draw(heroView.getAnimFromDir(heroView.getDir(), elapsedTime),
                    heroView.getHero().getPos().getxCoord() - (int) (heroView.getWidth() / 2),
                    heroView.getHero().getPos().getyCoord());
        }
        if (heroView.isAttacking) {
            if (heroView.getAttackAnim().isAnimationFinished(attackTime)) {
                heroView.isAttacking = false;
                attackTime = 0;
            }
        }
        batch.end();

        heroView.move();
    }

    @Override
    public void resize(final int width, final int height) {
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
