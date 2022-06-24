package com.unibo.view;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.unibo.audio.AudioManager;
import com.unibo.model.Level;
import com.unibo.model.Mob;
import com.unibo.model.items.Item;
import com.unibo.util.Pair;

/**
 * A Class to link items and characters with their textures.
 */
public class LevelView {

    private final List<Pair<Item, Animation<TextureRegion>>> itemTextures;
    private final List<MobView> mobTextures;
    private final List<Healthbar> hpBars;
    private AudioManager audioManager;
    private HeroView heroview;

    /**
     * @param level Level from where to get the items and characters
     */
    public LevelView(final Level level, final AudioManager audioManager) {
        this.itemTextures = new LinkedList<>();
        this.mobTextures = new LinkedList<>();
        this.hpBars = new LinkedList<>();
        this.audioManager = audioManager;
        this.loadItemTextures(level);
        this.loadMobTextures(level, this.audioManager);
        this.loadMobHpBars();
    }

    /**
     * Updates the level.
     * 
     * @param level New level
     */
    public void updateLevel(final Level level) {
        mobTextures.clear();
        itemTextures.clear();
        hpBars.clear();
        loadItemTextures(level);
        loadMobTextures(level, this.audioManager);
        loadMobHpBars();
    }

    /**
     * Updates the textures of the items in the current level.
     * 
     * @param lvl
     */
    public void updateItems(final Level lvl) {
        itemTextures.clear();
        loadItemTextures(lvl);
    }

    /**
     * Updates the textures of the mobs in the current level.
     * 
     * @param lvl
     */
    public void updateMobs(final Level lvl) {
        mobTextures.clear();
        loadMobTextures(lvl, this.audioManager);
    }

    /**
     * Updates health bars for this level mobs.
     */
    public void updateMobHpBars() {
        hpBars.clear();
        loadMobHpBars();
    }

    private void loadItemTextures(final Level lvl) {
        for (final Item i : lvl.getItems()) {
            final var text = new Texture("items/" + i.getClass().getSimpleName() + "/" + i.getName() + " Anim.png");
            itemTextures.add(new Pair<>(i,
                    new Animation<>(1f / 8f, TextureRegion.split(text, text.getWidth() / 6, text.getHeight())[0])));
        }
    }

    private void loadMobTextures(final Level lvl, AudioManager manager) {
        for (Mob mob : lvl.getEnemies()) {
            mobTextures.add(new MobView(mob, manager));
        }
    }

    private void loadMobHpBars() {
        for (final MobView m : this.mobTextures) {
            this.hpBars.add(new Healthbar((int) m.getWidth(), (int) (m.getHeight() / 10f)));
        }
    }

    /**
     * @return a list of pairs containing an item and its texture
     */
    public List<Pair<Item, Animation<TextureRegion>>> getItemTextures() {
        return this.itemTextures;
    }

    /**
     * @return a list containing mobviews of every mob in the level
     */
    public List<MobView> getMobTextures() {
        return this.mobTextures;
    }

    /**
     * @return a list containing the hp bar for each mob in the level
     */
    public List<Healthbar> getMobHpBars() {
        return this.hpBars;
    }

    /**
     * @return the Hero View
     */
    public HeroView getHeroView() {
        return heroview;
    }

    /**
     * Sets the level Hero View.
     * 
     * @param heroView
     */
    public void setHeroView(final HeroView heroView) {
        this.heroview = heroView;
    }

}
