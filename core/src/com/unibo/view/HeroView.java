package com.unibo.view;

import com.unibo.model.Hero;

/**
 * Class for the view of the hero.
 */
public class HeroView extends CharacterView {

    /**
     * Constructor for the view.
     * @param hero the hero model
     * @param path of the hero movement animation
     */
    public HeroView(final Hero hero, final String path) {
        super(hero, path);
    }

}
