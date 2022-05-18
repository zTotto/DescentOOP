package com.unibo.view;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.unibo.model.Level;
import com.unibo.model.Mob;
import com.unibo.model.items.Item;
import com.unibo.util.Pair;

/**
 * A Class to link items and characters with their textures.
 */
public class LevelView {

    private final List<Pair<Item, Texture>> itemTextures;
    private final List<MobView> mobTextures;

    /**
     * @param level Level from where to get the items and characters
     */
    public LevelView(final Level level) {
        this.itemTextures = new LinkedList<>();
        this.mobTextures = new LinkedList<>();
        this.loadItemTextures(level);
        this.loadMobTextures(level);
    }

    /**
     * Updates the level.
     * 
     * @param level New level
     */
    public void updateLevel(final Level level) {
        mobTextures.clear();
        itemTextures.clear();
        loadItemTextures(level);
        loadMobTextures(level);
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
        loadMobTextures(lvl);
    }

    private void loadItemTextures(final Level lvl) {
        for (Item i : lvl.getItems()) {
            itemTextures.add(
                    new Pair<>(i, new Texture("items/" + i.getClass().getSimpleName() + "/" + i.getName() + ".png")));
        }
    }

    private void loadMobTextures(final Level lvl) {
        for (Mob mob : lvl.getEnemies()) {
            mobTextures.add(new MobView(mob));
        }
    }

    /**
     * @return a list of pairs containing an item and its texture
     */
    public List<Pair<Item, Texture>> getItemTextures() {
        return this.itemTextures;
    }

    /**
     * @return a list containing mobviews of every mob in the level
     */
    public List<MobView> getMobTextures() {
        return this.mobTextures;
    }
}
