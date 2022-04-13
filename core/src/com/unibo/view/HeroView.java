package com.unibo.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.unibo.maps.Map;
import com.unibo.model.Hero;
import com.unibo.util.Direction;

/**
 * Class for the view of the Hero model.
 */
public class HeroView {

    private final Hero hero;

    private Texture heroTextures;
    private TextureRegion still;
    private Animation<TextureRegion> animationLeft;
    private Animation<TextureRegion> animationRight;
    private Animation<TextureRegion> animationUp;
    private Animation<TextureRegion> animationDown;
    private Animation<TextureRegion> animationAttack;
    private Direction dir = Direction.STILL;

    /**
     * Constructor for this class.
     * @param hero
     */
    public HeroView(final Hero hero) {
        this.hero = hero;
        this.createTextures("walkingAnim.png");
    }

    private void createTextures(final String fileName) {
        heroTextures = new Texture(fileName);
        TextureRegion[][] tmp = TextureRegion.split(heroTextures, heroTextures.getWidth() / 3,
                heroTextures.getHeight() / 4);

        // Texture when still
        still = tmp[2][1];

        // Texture when moving right
        TextureRegion[] heroTextureRight = new TextureRegion[3];
        heroTextureRight[0] = tmp[1][0];
        heroTextureRight[1] = tmp[1][1];
        heroTextureRight[2] = tmp[1][2];
        animationRight = new Animation<>(1f / 8f, heroTextureRight);

        // Texture when moving left
        TextureRegion[] heroTextureLeft = new TextureRegion[3];
        heroTextureLeft[0] = tmp[3][2];
        heroTextureLeft[1] = tmp[3][1];
        heroTextureLeft[2] = tmp[3][0];
        animationLeft = new Animation<>(1f / 8f, heroTextureLeft);

        // Texture when moving up
        TextureRegion[] heroTextureUp = new TextureRegion[3];
        heroTextureUp[0] = tmp[0][0];
        heroTextureUp[1] = tmp[0][1];
        heroTextureUp[2] = tmp[0][2];
        animationUp = new Animation<>(1f / 8f, heroTextureUp);

        // Texture when moving down
        TextureRegion[] heroTextureDown = new TextureRegion[3];
        heroTextureDown[0] = tmp[2][0];
        heroTextureDown[1] = tmp[2][1];
        heroTextureDown[2] = tmp[2][2];
        animationDown = new Animation<>(1f / 8f, heroTextureDown);

        // Texture when attacking
        //TODO: CHANGE WHEN SPRITES ARE READY
        TextureRegion[] heroTextureAttack = new TextureRegion[3];
        heroTextureAttack[0] = tmp[1][0];
        heroTextureAttack[1] = tmp[1][1];
        heroTextureAttack[2] = tmp[1][2];
        animationAttack = new Animation<>(1f / 3f, heroTextureAttack);
    }

    /**
     * Moves the character depending on the pressed key.
     */
    public void move() {
        dir = Direction.STILL;
        Map map = hero.getCurrentMap();
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && map.validMovement(hero, Direction.LEFT)) {
            dir = Direction.LEFT;
            hero.setPos(hero.getPos().getxCoord() - (int) (hero.getSpeed() * Gdx.graphics.getDeltaTime()),
                    hero.getPos().getyCoord());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && map.validMovement(hero, Direction.RIGHT)) {
            dir = Direction.RIGHT;
            hero.setPos(hero.getPos().getxCoord() + (int) (hero.getSpeed() * Gdx.graphics.getDeltaTime()),
                    hero.getPos().getyCoord());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && map.validMovement(hero, Direction.UP)) {
            dir = Direction.UP;
            hero.setPos(hero.getPos().getxCoord(),
                    hero.getPos().getyCoord() + (int) (hero.getSpeed() * Gdx.graphics.getDeltaTime()));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && map.validMovement(hero, Direction.DOWN)) {
            dir = Direction.DOWN;
            hero.setPos(hero.getPos().getxCoord(),
                    hero.getPos().getyCoord() - (int) (hero.getSpeed() * Gdx.graphics.getDeltaTime()));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && Gdx.input.isKeyPressed(Input.Keys.UP)
                || Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            dir = Direction.STILL;
        }
    }

    /**
     * @return the still character texture
     */
    public TextureRegion getStillTexture() {
        return still;
    }

    /**
     * 
     * @param time
     * @return the attack animation 
     */
    public TextureRegion getAttackText(final float time) {
        return animationAttack.getKeyFrame(time, true);
    }

    /**
     * Makes the character attack.
     */
    public void attack() {
        System.out.println("Attack!");
    }

    /**
     * @return the hero model class
     */
    public Hero getHero() {
        return hero;
    }

    /**
     * @return the character direction
     */
    public Direction getDir() {
        return dir;
    }

    /**
     * Returns the animation corresponding to a direction.
     * 
     * @param dir  the direction
     * @param time for the animation duration
     * @return the animation
     */
    public TextureRegion getAnimFromDir(final Direction dir, final float time) {
        switch (dir) {
        case LEFT:
            return animationLeft.getKeyFrame(time, true);
        case RIGHT:
            return animationRight.getKeyFrame(time, true);
        case UP:
            return animationUp.getKeyFrame(time, true);
        case DOWN:
            return animationDown.getKeyFrame(time, true);
        default:
            return new TextureRegion(still);
        }
    }

    /**
     * @return the sprite height
     */
    public float getHeight() {
        return still.getRegionHeight();
    }

    /**
     * @return the sprite width
     */
    public float getWidth() {
        return still.getRegionWidth();
    }
}
