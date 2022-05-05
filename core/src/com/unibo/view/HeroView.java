package com.unibo.view;

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

    /**
     * Constructor for the view.
     * 
     * @param hero  the hero model
     * @param path  of the hero movement animation
     * @param input handler for keyboard inputs
     */
    public HeroView(final Hero hero, final String path, final InputHandler input) {
        super(hero, path, "audio/sounds/Hadouken.mp3");
        this.hero = hero;
        this.input = input;
    }

    /**
     * Moves the hero depending on the pressed key.
     */
    public void move() {
        setDir(Direction.STILL);

        this.input.handleInput(KeyBindings.MOVE_LEFT).ifPresent(t -> t.executeCommand(this));

        this.input.handleInput(KeyBindings.MOVE_RIGHT).ifPresent(t -> t.executeCommand(this));

        this.input.handleInput(KeyBindings.MOVE_UP).ifPresent(t -> t.executeCommand(this));

        this.input.handleInput(KeyBindings.MOVE_DOWN).ifPresent(t -> t.executeCommand(this));
    }

    /**
     * @return The hero
     */
    public Hero getHero() {
        return this.hero;
    }
}
