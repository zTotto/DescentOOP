package com.unibo.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.unibo.maps.Map;
import com.unibo.maps.MapImpl;
import com.unibo.model.ConsumableItem;
import com.unibo.model.HealthPotion;
import com.unibo.model.Hero;
import com.unibo.model.Level;
import com.unibo.model.Weapon;
import com.unibo.util.KeyBindings;
import com.unibo.util.Position;
import com.unibo.util.WeaponStats;
import com.unibo.view.CharacterView;
import com.unibo.view.HeroView;

/**
 * Game screen class.
 */
public class GameScreen implements Screen {
    private final static int MAX_SPEED = 200;
    private final static int MAX_HP = 100;
    private final Descent game;
    private final PauseMenu menu;

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private CharacterView heroView;
    // private CharacterView mobView;
    private OrthogonalTiledMapRenderer renderer;
    private final Map mappa = new MapImpl("maps/testmap.tmx", new Position(100, 900));
    private final Texture hpTexture;
    private final Music soundtrack;
    private float elapsedTime;
    private float attackTime;
    private Boolean isPaused = false;
    private final Level lvlTest;

    /**
     * Main game scene.
     * 
     * @param game
     */
    public GameScreen(final Descent game) {
        this.game = game;
        menu = new PauseMenu(this);
        menu.getMenu().setVisible(true);
        lvlTest = new Level();
        hpTexture = new Texture("hpPotion.png");
        HealthPotion hp1 = new HealthPotion("Base Health Potion", "0", 15.0);
        HealthPotion hp2 = new HealthPotion("Base Health Potion", "0", 15.0);
        HealthPotion hp3 = new HealthPotion("Base Health Potion", "0", 15.0);
        hp1.setPos(new Position(100, 900));
        hp2.setPos(new Position(200, 1016));
        hp3.setPos(new Position(300, 1016));
        lvlTest.addConsumables(hp2, hp1, hp3);

        heroView = new HeroView(new Hero("Ross", MAX_HP, MAX_SPEED, new Weapon(WeaponStats.LONGSOWRD, "0")),
                "walkingAnim.png");
        // mobView = new MobView(new Mob(MobsStats.ORC, new Weapon("Longsword", 10, 64,
        // "0")), "walkingAnim.png", "audio/sounds/Hadouken.mp3");
        soundtrack = Gdx.audio.newMusic(Gdx.files.internal("audio/backgroundsong.mp3"));
        soundtrack.setLooping(true);
        soundtrack.play();
        soundtrack.setVolume(0.4f);
        heroView.getCharacter().setCurrentMap(mappa);
        heroView.getCharacter().setPos(heroView.getCharacter().getCurrentMap().getStartingPosition());

        /*
         * mobView.getCharacter().setCurrentMap(mappa);
         * mobView.getCharacter().setPos(new Position(
         * mobView.getCharacter().getCurrentMap().getStartingPosition().getxCoord()+100,
         * mobView.getCharacter().getCurrentMap().getStartingPosition().getyCoord()-30
         * ));
         * 
         */

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

        // Hero Coordinates
        int heroX = heroView.getCharacter().getPos().getxCoord();
        int heroTextureX = heroX - (int) (heroView.getWidth() / 2);
        int heroY = heroView.getCharacter().getPos().getyCoord();

        // Camera and batch initial settings
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.input.setInputProcessor(menu.getStage());
        renderer.setView(camera);
        renderer.render();
        camera.position.set(heroX, heroY, 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // Pause Activation
        if (Gdx.input.isKeyJustPressed(KeyBindings.PAUSE.getKey())) {
            this.isPaused = !this.isPaused;
        }

        // Hp Potion rendering
        for (ConsumableItem i : lvlTest.getConsumables()) {
            batch.draw(hpTexture, i.getPos().getxCoord() - hpTexture.getWidth() / 2, i.getPos().getyCoord());
        }

        // Last hero direction and music stopped during pause
        if (this.isPaused) {
            this.soundtrack.pause();
            batch.draw(heroView.getAnimFromDir(heroView.getDir(), elapsedTime), heroTextureX, heroY);
        }

        if (!this.isPaused) {

            this.soundtrack.play();

            // Item pick up
            if (Gdx.input.isKeyJustPressed(KeyBindings.PICK_UP.getKey())) {
                heroView.getCharacter().pickUpfromLevel(lvlTest);
            }

            // Attack Check
            if (Gdx.input.isKeyJustPressed(KeyBindings.ATTACK.getKey()) && !heroView.isAttacking) {
                heroView.isAttacking = true;
                heroView.getAttackSound().play();
                heroView.attack();
            }

            if (heroView.isAttacking) {
                // Attack timer
                attackTime += Gdx.graphics.getDeltaTime();

                batch.draw(heroView.getAttackText(attackTime), heroTextureX, heroY);

                if (heroView.getAttackAnim().isAnimationFinished(attackTime)) {
                    heroView.isAttacking = false;
                    attackTime = 0;
                }
            } else {
                // Animation timer
                elapsedTime += Gdx.graphics.getDeltaTime();

                batch.draw(heroView.getAnimFromDir(heroView.getDir(), elapsedTime), heroTextureX, heroY);
            }

            heroView.move();
        }

        batch.end();

        // Pause Menu
        if (this.isPaused) {
            menu.getStage().act();
            menu.getStage().draw();
        }
    }

    @Override
    public void resize(final int width, final int height) {
        menu.getStage().getViewport().update(width, height, true);
        camera.viewportWidth = width / 2.5f;
        camera.viewportHeight = height / 2.5f;
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        dispose();

    }

    @Override
    public void dispose() {
    }

    /**
     * @return the main game scene.
     */
    public GameScreen getGameScreen() {
        return this;
    }

    /**
     * Unpauses the game.
     */
    public void disablePause() {
        this.isPaused = false;
    }
}
