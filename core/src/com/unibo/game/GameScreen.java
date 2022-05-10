package com.unibo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.unibo.keybindings.InputHandler;
import com.unibo.keybindings.KeyBindings;
import com.unibo.keybindings.Movement;
import com.unibo.keybindings.SpeedUpSkill;
import com.unibo.maps.Map;
import com.unibo.maps.MapImpl;
import com.unibo.model.ConsumableItem;
import com.unibo.model.HealthPotion;
import com.unibo.model.Hero;
import com.unibo.model.Level;
import com.unibo.model.Weapon;
import com.unibo.util.Direction;
import com.unibo.util.Position;
import com.unibo.util.WeaponStats;
import com.unibo.view.Expbar;
import com.unibo.view.Healthbar;
import com.unibo.view.HeroView;
import com.unibo.view.Manabar;

/**
 * Game screen class.
 */
public class GameScreen implements Screen {
    private static final int MANA_UNIT = 1;
    private static final int MAX_SPEED = 200;
    private static final int MAX_HP = 100;
    private static final int MAX_MANA = 100;
    private static final double SPEED_MULTIPLAYER = 1.2;
    private final Descent game;
    private final PauseMenu menu;

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private HeroView heroView;
    // private CharacterView mobView;
    private OrthogonalTiledMapRenderer renderer;
    private final Map mappa = new MapImpl("maps/testmap.tmx", new Position(100, 900));

    private final Texture hpTexture;
    private final Image hpPotionIcon;
    private final Label potionQuantity;

    private final Music soundtrack;
    private float elapsedTime;
    private float attackTime;
    private Boolean isPaused = false;
    private final Level lvlTest = new Level();
    private final Healthbar hpbar;
    private final Manabar manabar;
    private final Expbar expbar;
    
    private final Label levelNumber;

    private final InputHandler input = new InputHandler(t -> {
        t.isAttacking = true;
        t.getAttackSound().play();
        t.attack();
    }, t -> t.getCharacter().pickUpfromLevel(lvlTest), t -> {}, // TODO (Weapon Switch)
    new Movement(Direction.UP), new Movement(Direction.RIGHT), new Movement(Direction.DOWN),
    new Movement(Direction.LEFT), t -> this.isPaused = !this.isPaused,
    t -> t.getCharacter().useItem((ConsumableItem) t.getCharacter().getInv().getInv().stream()
                                                    .map(p -> p.getFirst())
                                                    .filter(i -> i instanceof HealthPotion)
                                                    .findFirst().orElse(null)),
    new SpeedUpSkill(MANA_UNIT, MAX_SPEED, MAX_SPEED*SPEED_MULTIPLAYER)
    );

    /**
     * Main game scene.
     * 
     * @param game
     */
    public GameScreen(final Descent game) {
        this.game = game;

        // Menu
        menu = new PauseMenu(this);
        menu.getMenu().setVisible(true);

        // Health Bar
        hpbar = new Healthbar((int) (Gdx.graphics.getWidth() / 5f), (int) (Gdx.graphics.getHeight() / 20f));
        hpbar.setPosition(Gdx.graphics.getWidth() - hpbar.getWidth() - hpbar.getHeight(),
                Gdx.graphics.getHeight() - 2 * hpbar.getHeight());
        
        // Mana Bar
        manabar = new Manabar((int) (Gdx.graphics.getWidth() / 5f), (int) (Gdx.graphics.getHeight() / 20f));
        manabar.setPosition(Gdx.graphics.getWidth() - manabar.getWidth() - manabar.getHeight(),
                (Gdx.graphics.getHeight() - 2 * manabar.getHeight()) - hpbar.getHeight()*1.2f);
        
        // Exp Bar
        expbar = new Expbar((int) (Gdx.graphics.getWidth()), (int) (Gdx.graphics.getHeight() / 40f));
        expbar.setPosition(0,0);

        hpTexture = new Texture("hpPotion.png");
        final HealthPotion hp1 = new HealthPotion("Base Health Potion", "0", 15.0);
        final HealthPotion hp2 = new HealthPotion("Base Health Potion", "0", 15.0);
        final HealthPotion hp3 = new HealthPotion("Base Health Potion", "0", 15.0);
        hp1.setPos(new Position(100, 900));
        hp2.setPos(new Position(200, 1016));
        hp3.setPos(new Position(300, 1016));
        lvlTest.addConsumables(hp2, hp1, hp3);

        // Hp Potion Icon
        hpPotionIcon = new Image(hpTexture);
        hpPotionIcon.setPosition(manabar.getX(), manabar.getY() - manabar.getHeight()*1.2f);
        manabar.getStage().addActor(hpPotionIcon);

        heroView = new HeroView(new Hero("Ross", MAX_HP, MAX_SPEED, new Weapon(WeaponStats.LONGSWORD, "0"), MAX_MANA),
                "walkingAnim.png", this.input);
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

        // Hp Potion Quantity
        potionQuantity = new Label(": " + heroView.getHero().getInv().getPotionQuantity(),
                new Skin(Gdx.files.internal("skin/glassy-ui.json")));
        potionQuantity.setFontScale(0.5f);
        potionQuantity.setPosition(hpPotionIcon.getX() + hpPotionIcon.getWidth(),
                hpPotionIcon.getY() - hpPotionIcon.getHeight() / 2);
        manabar.getStage().addActor(potionQuantity);

        levelNumber =  new Label("Level: " + heroView.getHero().getLevel(),
                new Skin(Gdx.files.internal("skin/glassy-ui.json")));
        levelNumber.setFontScale(0.5f);
        levelNumber.setPosition(0, 0);
        expbar.getStage().addActor(this.levelNumber);
        
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
        final int heroX = heroView.getCharacter().getPos().getxCoord();
        final int heroTextureX = heroX - (int) (heroView.getWidth() / 2);
        final int heroY = heroView.getCharacter().getPos().getyCoord();

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
        this.input.handleInput(KeyBindings.PAUSE).ifPresent(t -> t.executeCommand(heroView));

        // Hp Potion rendering
        for (final ConsumableItem i : lvlTest.getConsumables()) {
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
            this.input.handleInput(KeyBindings.PICK_UP).ifPresent(t -> t.executeCommand(heroView));

            // Use potion
            this.input.handleInput(KeyBindings.USE_POTION).ifPresent(t -> t.executeCommand(heroView));

            // Attack Check
            if (!heroView.isAttacking) {
                this.input.handleInput(KeyBindings.ATTACK).ifPresent(t -> t.executeCommand(heroView));
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

        // Health bar and Potion Quantity
        potionQuantity.setText(": " + heroView.getHero().getInv().getPotionQuantity());
        hpbar.update(heroView.getHero());
        hpbar.getStage().act();
        hpbar.getStage().draw();
        
        manabar.update(heroView.getHero());
        manabar.getStage().act();
        manabar.getStage().draw();
        
        levelNumber.setText(": " + heroView.getHero().getLevel());
        expbar.update(heroView.getHero());
        expbar.getStage().act();
        expbar.getStage().draw();
        
        // Debug
        if (Gdx.input.isKeyJustPressed(Input.Keys.G)) {
            heroView.getHero().addExp(200);
            System.out.println("Hp: " + heroView.getHero().getCurrentHp() + " of " + heroView.getHero().getMaxHp());
            System.out.println("Exp: " + heroView.getHero().getExp() + " of " + heroView.getHero().getExpToLevelUp());
            System.out.println("Level: " + heroView.getHero().getLevel());
            System.out.println((float) heroView.getHero().getCurrentHp() / (float) heroView.getHero().getMaxHp());
            System.out.println(heroView.getHero().getInv().getPotionQuantity());
            System.out.println(heroView.getHero().getInv().toString());
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

    /**
     * @return the main game application
     */
    public Descent getGame() {
        return this.game;
    }
}
