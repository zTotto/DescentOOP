package com.unibo.game;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.unibo.keybindings.InputHandler;
import com.unibo.keybindings.KeyBindings;
import com.unibo.maps.Map;
import com.unibo.maps.MapImpl;
import com.unibo.model.HealSkill;
import com.unibo.model.Hero;
import com.unibo.model.Level;
import com.unibo.model.LevelsList;
import com.unibo.model.Mob;
import com.unibo.model.Movement;
import com.unibo.model.SpeedUpSkill;
import com.unibo.model.items.DoorKey;
import com.unibo.model.items.HealthPotion;
import com.unibo.model.items.Item;
import com.unibo.model.items.Weapon;
import com.unibo.util.Direction;
import com.unibo.util.HealthPotionStats;
import com.unibo.util.MobStats;
import com.unibo.util.Pair;
import com.unibo.util.Position;
import com.unibo.util.WeaponStats;
import com.unibo.view.Expbar;
import com.unibo.view.Healthbar;
import com.unibo.view.HeroView;
import com.unibo.view.LevelView;
import com.unibo.view.Manabar;
import com.unibo.view.MobView;

/**
 * Game screen class.
 */
public class GameScreen implements Screen {
    private static final int MANA_UNIT = 1;
    private static final int MAX_SPEED = 200;
    private static final int MAX_HP = 100;
    private static final int MAX_MANA = 100;
    private static final double SPEED_MULTIPLAYER = 0.75;
    private static final double TEN_PERCENT_MULTIPLAYER = 0.2;
    private final Descent game;
    private final PauseMenu menu;
    private final SkillMenu skillMenu;

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private HeroView heroView;
    private OrthogonalTiledMapRenderer renderer;
    // private Map mappa = new MapImpl("maps/testmap.tmx", new Position(100, 900));
    private Map mappa = new MapImpl("testMap/Cave.tmx", new Position(930, 1262));

    private final Texture hpTexture;
    private final Image hpPotionIcon;
    private final TextureRegion[] bloodAnim;
    private final Label potionQuantity;

    private final Music soundtrack;

    private float elapsedTime;
    private float attackTime;
    private float gameTime;
    private int startingGameTime;

    private boolean isPaused;
    private boolean isSkillMenuOpen;

    private final LevelsList lvlList;
    private Level currentLvl;
    private final LevelView lvlView;

    private final Healthbar hpbar;
    private final Manabar manabar;
    private final Expbar expbar;

    private List<Pair<Position, Float>> lastDeadEnemies = new LinkedList<>();

    private final Label levelNumber;

    private final InputHandler input = new InputHandler();

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
                (Gdx.graphics.getHeight() - 2 * manabar.getHeight()) - hpbar.getHeight() * 1.2f);

        // Exp Bar
        expbar = new Expbar(Gdx.graphics.getWidth(), (int) (Gdx.graphics.getHeight() / 40f));
        expbar.setPosition(0, 0);

        // World Items
        hpTexture = new Texture("items/HealthPotion/Basic Health Potion.png");

        // Level
        lvlList = new LevelsList();
        currentLvl = lvlList.getCurrentLevel();
        currentLvl
                .addItems(new Weapon(WeaponStats.GREATAXE, "1").setPos(new Position(400, 1016)),
                        new Weapon(WeaponStats.LONGSWORD, "2").setPos(new Position(500, 1016)),
                        new HealthPotion(HealthPotionStats.MEDIUM_HEALTH_POTION, "0").setPos(new Position(100, 900)),
                        new HealthPotion(HealthPotionStats.BASIC_HEALTH_POTION, "0").setPos(new Position(200, 1016)),
                        new HealthPotion(HealthPotionStats.BASIC_HEALTH_POTION, "0").setPos(new Position(300, 1016)),
                        new DoorKey().setPos(new Position(600, 1016)))
                .addEnemies(
                        new Mob(MobStats.TROLL, new Weapon(WeaponStats.LONGSWORD, "2")).setPos(new Position(200, 900)))
                .setDoorPosition(new Position(700, 1016));
        lvlView = new LevelView(currentLvl);

        // Hp Potion Icon
        hpPotionIcon = new Image(hpTexture);
        hpPotionIcon.setPosition(manabar.getX(), manabar.getY() - manabar.getHeight() * 1.2f);
        manabar.getStage().addActor(hpPotionIcon);

        // Blood Animation
        Texture bloodTexture = new Texture("characters/bloodMob.png");
        bloodAnim = TextureRegion.split(bloodTexture, bloodTexture.getWidth() / 12, bloodTexture.getHeight())[0];

        heroView = new HeroView(new Hero("Ross", MAX_HP, MAX_SPEED, MAX_MANA), this.input);
        this.skillMenu = new SkillMenu(this, heroView.getCharacter());
        this.skillMenu.getMenu().setVisible(true);

        soundtrack = Gdx.audio.newMusic(Gdx.files.internal("audio/backgroundsong.mp3"));
        soundtrack.setLooping(true);
        soundtrack.play();
        soundtrack.setVolume(0.4f);
        heroView.getCharacter().setCurrentMap(mappa);
        heroView.getCharacter().setPos(heroView.getCharacter().getCurrentMap().getStartingPosition());

        // Hp Potion Quantity
        potionQuantity = new Label(": " + heroView.getHero().getInv().getHealthPotionQuantity(),
                new Skin(Gdx.files.internal("skin/glassy-ui.json")));
        potionQuantity.setFontScale(0.5f);
        potionQuantity.setPosition(hpPotionIcon.getX() + hpPotionIcon.getWidth(),
                hpPotionIcon.getY() - hpPotionIcon.getHeight() / 2);
        manabar.getStage().addActor(potionQuantity);

        levelNumber = new Label("Level: " + heroView.getHero().getLevel(),
                new Skin(Gdx.files.internal("skin/glassy-ui.json")));
        levelNumber.setFontScale(0.5f);
        levelNumber.setPosition(0, 0);
        expbar.getStage().addActor(this.levelNumber);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Descent.GAME_WIDTH, Descent.GAME_HEIGHT);
        batch = new SpriteBatch();

        this.input.addCommand(KeyBindings.ATTACK, t -> {
            t.setIsAttacking(true);
            t.getAttackSound().play();
            // t.selfAttack();
            var last = t.getCharacter().hitEnemyFromLevel(currentLvl);
            if (last.isPresent()) {
                this.lastDeadEnemies.add(new Pair<>(last.get(), 0f));
            }
            last = Optional.empty();
            currentLvl.removeDeadMobs();
            lvlView.updateMobs(currentLvl);
        }).addCommand(KeyBindings.PICK_UP, t -> {
            t.getCharacter().pickUpfromLevel(currentLvl);
            lvlView.updateItems(currentLvl);
        }).addCommand(KeyBindings.SWITCH_WEAPON, t -> ((HeroView) t).switchWeapon())
                .addCommand(KeyBindings.MOVE_UP, new Movement(Direction.UP))
                .addCommand(KeyBindings.MOVE_RIGHT, new Movement(Direction.RIGHT))
                .addCommand(KeyBindings.MOVE_DOWN, new Movement(Direction.DOWN))
                .addCommand(KeyBindings.MOVE_LEFT, new Movement(Direction.LEFT)).addCommand(KeyBindings.PAUSE, t -> {
                    Gdx.input.setInputProcessor(menu.getStage());
                    this.isPaused = !this.isPaused;
                    this.menu.getMenu().setVisible(isPaused);
                    this.isSkillMenuOpen = false;
                }).addCommand(KeyBindings.USE_POTION, t -> ((Hero) t.getCharacter()).usePotion())
                .addCommand(KeyBindings.INCREASES_SPEED,
                        new SpeedUpSkill(MANA_UNIT, MAX_SPEED, MAX_SPEED * SPEED_MULTIPLAYER))
                .addCommand(KeyBindings.HEAL, new HealSkill(MANA_UNIT * 50, (int) (MAX_HP * TEN_PERCENT_MULTIPLAYER)))
                .addCommand(KeyBindings.SKILL_MENU, t -> {
                    Gdx.input.setInputProcessor(skillMenu.getStage());
                    this.isSkillMenuOpen = !this.isSkillMenuOpen;
                    this.skillMenu.getMenu().setVisible(isSkillMenuOpen);
                    this.isPaused = false;
                }).addCommand(KeyBindings.USE_KEY, t -> {
                    if (((Hero) t.getCharacter()).canOpenDoor(this.currentLvl.getDoorPosition())) {
                        if (this.lvlList.hasNextLevel()) {
                            this.currentLvl = this.lvlList.getNextLevel();
                            System.out.println("NEXT LEVEL");

                            Gdx.app.postRunnable(() -> {
                                mappa = new MapImpl("maps/testmap.tmx", new Position(100, 900));
                                renderer.getMap().dispose();
                                renderer.setMap(mappa.getTiledMap());
                                // this.show();
                            });
                        } else {
                            this.soundtrack.pause();
                            this.soundtrack.dispose();
                            game.setScreen(new GameOverMenu(game));
                        }
                    }
                });
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        renderer = new OrthogonalTiledMapRenderer(mappa.getTiledMap(), 1 / 4f);
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
        renderer.setView(camera);
        renderer.render();
        camera.position.set(heroX, heroY, 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // Pause Activation
        this.input.handleInput(KeyBindings.PAUSE).ifPresent(t -> t.executeCommand(heroView));

        // Pause Activation
        this.input.handleInput(KeyBindings.SKILL_MENU).ifPresent(t -> t.executeCommand(heroView));

        // Item Rendering
        for (final Pair<Item, Texture> p : lvlView.getItemTextures()) {
            batch.draw(p.getSecond(), p.getFirst().getPos().getxCoord() - p.getSecond().getWidth() / 2,
                    p.getFirst().getPos().getyCoord());
        }
        // Mob Rendering
        int barIndex = 0;
        for (final MobView m : lvlView.getMobTextures()) {
            batch.draw(m.getAttackText(elapsedTime), m.getCharacter().getPos().getxCoord() - (int) (m.getWidth() / 2),
                    m.getCharacter().getPos().getyCoord());
            Healthbar mobBar = lvlView.getMobHpBars().get(barIndex);
            mobBar.update(m.getCharacter());
            mobBar.setPosition(m.getCharacter().getPos().getxCoord() - m.getWidth() / 2f,
                    m.getCharacter().getPos().getyCoord() + m.getHeight() * 1.1f);
            mobBar.draw(batch, 1);
            barIndex++;
        }

        // Last hero direction and music stopped during any kind of pause
        if (this.isPaused || this.isSkillMenuOpen) {
            this.soundtrack.pause();
            batch.draw(heroView.getAnimFromDir(heroView.getDir(), elapsedTime), heroTextureX, heroY);
        }

        if (!this.isPaused && !this.isSkillMenuOpen) {

            this.soundtrack.play();
            gameTime += Gdx.graphics.getDeltaTime();
            if ((int) gameTime == startingGameTime + 1) {
                startingGameTime++;
                heroView.getHero().increaseCurrentMana(10);
            }

            // Item pick up
            this.input.handleInput(KeyBindings.PICK_UP).ifPresent(t -> t.executeCommand(heroView));

            // Use potion
            this.input.handleInput(KeyBindings.USE_POTION).ifPresent(t -> t.executeCommand(heroView));

            // Use skill heal
            this.input.handleInput(KeyBindings.HEAL).ifPresent(t -> t.executeCommand(heroView));

            // Switch weapon
            this.input.handleInput(KeyBindings.SWITCH_WEAPON).ifPresent(t -> t.executeCommand(heroView));

            // Use door key
            this.input.handleInput(KeyBindings.USE_KEY).ifPresent(t -> t.executeCommand(heroView));

            // Attack Check
            if (!heroView.getIsAttacking()) {
                this.input.handleInput(KeyBindings.ATTACK).ifPresent(t -> t.executeCommand(heroView));
            }

            if (heroView.getIsAttacking()) {

                batch.draw(heroView.getAttackText(attackTime), heroTextureX, heroY);

                // Attack timer
                attackTime += Gdx.graphics.getDeltaTime();

                if (heroView.getAttackAnim().isAnimationFinished(attackTime)) {
                    heroView.setIsAttacking(false);
                    attackTime = 0;
                }
            } else {
                batch.draw(heroView.getAnimFromDir(heroView.getDir(), elapsedTime), heroTextureX, heroY);

                // Animation timer
                elapsedTime += Gdx.graphics.getDeltaTime();
            }

            // Blood animation on dead mob
            var temp = new LinkedList<>(lastDeadEnemies);
            for (Pair<Position, Float> p : temp) {
                var anim = new Animation<>(1f / 12f, bloodAnim);
                batch.draw(anim.getKeyFrame(p.getSecond(), false),
                        p.getFirst().getxCoord() - anim.getKeyFrame(p.getSecond(), false).getRegionHeight() / 2f,
                        p.getFirst().getyCoord());

                // Timer
                p.setSecond(p.getSecond() + Gdx.graphics.getDeltaTime());

                if (anim.isAnimationFinished(p.getSecond())) {
                    lastDeadEnemies.remove(p);
                }
            }

            heroView.move();
        }

        batch.end();

        // Pause Menu
        if (this.isPaused) {
            menu.getStage().act();
            menu.getStage().draw();
        } else if (this.isSkillMenuOpen) {
            skillMenu.getStage().act();
            skillMenu.getStage().draw();
        }

        // Health bar and Potion Quantity
        potionQuantity.setText(": " + heroView.getHero().getInv().getHealthPotionQuantity());
        hpbar.update(heroView.getHero());
        hpbar.getStage().act();
        hpbar.getStage().draw();

        manabar.update(heroView.getHero());
        manabar.getStage().act();
        manabar.getStage().draw();

        levelNumber.setText("Level " + heroView.getHero().getLevel());
        expbar.update(heroView.getHero());
        expbar.getStage().act();
        expbar.getStage().draw();

        // Debug
        if (Gdx.input.isKeyJustPressed(Input.Keys.G)) {
            // heroView.getHero().addExp(200);
            System.out.println("\n\nHp: " + heroView.getHero().getCurrentHp() + " of " + heroView.getHero().getMaxHp());
            System.out.println(heroView.getHero().getInv().toString());
            System.out.println(heroView.getHero().getCurrentWeapon());
            System.out.println(heroView.getHero().getPos());
        }
    }

    @Override
    public void resize(final int width, final int height) {
        menu.getStage().getViewport().update(width, height, true);
        skillMenu.getStage().getViewport().update(width, height, true);
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
        this.isSkillMenuOpen = false;
    }

    /**
     * @return the main game application
     */
    public Descent getGame() {
        return this.game;
    }
}
