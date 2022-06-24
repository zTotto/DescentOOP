package com.unibo.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.unibo.audio.AudioManager;
import com.unibo.keybindings.InputHandler;
import com.unibo.keybindings.KeyBindings;
import com.unibo.model.Hero;
import com.unibo.util.Direction;

/**
 * Class for the view of the hero.
 */
public class HeroView extends CharacterView {

    private final InputHandler input;
    private final Hero hero;
    private static final String WALK_SOUND = "audio/sounds/run.wav";
    private static final float WALK_VOLUME = (float) 1;

    /**
     * Constructor for the view.
     * 
     * @param hero  the hero model
     * @param input handler for keyboard inputs
     * @param audioManager AudioManager for the hero's sounds
     */
    public HeroView(final Hero hero, final InputHandler input, final AudioManager audioManager) {
        super(hero, "characters/hero" + hero.getCurrentWeapon().getName() + ".png", "audio/sounds/weaponSound.mp3", audioManager);
        this.hero = hero;
        this.input = input;
    }

    /**
     * Moves the hero depending on the pressed key, and speeds it up if pressed the
     * SpeedUp skill key.
     */
    public void move() {
        setDir(Direction.STILL);
        this.setIsMoving(false);

        this.input.handleInput(KeyBindings.INCREASES_SPEED).ifPresentOrElse(t -> t.executeCommand(this),
                () -> this.getHero().setSpeed(this.getHero().getInitialSpeed()));

        this.input.handleInput(KeyBindings.MOVE_LEFT).ifPresent(t -> t.executeCommand(this));

        this.input.handleInput(KeyBindings.MOVE_RIGHT).ifPresent(t -> t.executeCommand(this));

        this.input.handleInput(KeyBindings.MOVE_UP).ifPresent(t -> t.executeCommand(this));

        this.input.handleInput(KeyBindings.MOVE_DOWN).ifPresent(t -> t.executeCommand(this));
        
        if (this.getIsMoving()) {
        	musicNotifyAudiomanager(WALK_SOUND, false, WALK_VOLUME);
        }

    }

    /**
     * @return The hero
     */
    public Hero getHero() {
        return this.hero;
    }

    /**
     * Switches the hero weapon and updates its texture.
     */
    public void switchWeapon() {
        this.hero.switchWeapon();
        this.recreateTextures("characters/hero" + this.hero.getCurrentWeapon().getName() + ".png");
    }

}
