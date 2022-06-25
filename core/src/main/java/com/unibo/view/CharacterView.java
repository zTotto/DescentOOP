package com.unibo.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.unibo.util.Direction;
import com.unibo.audio.AudioManager;
import com.unibo.model.Character;

/**
 * Class for the view of a generic character.
 */
public abstract class CharacterView {

    private final Character character;

    private TextureRegion still;
    private Animation<TextureRegion> animationLeft;
    private Animation<TextureRegion> animationRight;
    private Animation<TextureRegion> animationUp;
    private Animation<TextureRegion> animationDown;
    private Animation<TextureRegion> animationAttackLeft;
    private Animation<TextureRegion> animationAttackRight;
    private Animation<TextureRegion> animationAttackUp;
    private Animation<TextureRegion> animationAttackDown;
    private Direction dir = Direction.STILL;
    private final Rectangle charRect;
    private String attackSoundPath;
    private static final Float ATTACK_VOLUME = (float) 1.0;
    private AudioManager audioManager;
    private Boolean isAttacking = false;
    private boolean isMoving;

    /**
     * Constructor for this class.
     * 
     * @param character
     * @param texturePath     path of the character movement animation
     * @param attackSoundPath path of the attack sound
     */
    public CharacterView(final Character character, final String texturePath, final String attackSoundPath, final AudioManager manager) {
        this.character = character;
        this.attackSoundPath = attackSoundPath;
        this.createTextures(texturePath);
        this.audioManager = manager;
        this.charRect = new Rectangle(this.character.getPos().getxCoord(), this.character.getPos().getyCoord(),
                still.getRegionWidth() * 0.66f, still.getRegionHeight() / 6);
    }

    /**
     * Changes the character animation according to the file path.
     * 
     * @param fileName
     */
    private void createTextures(final String fileName) {
        final Texture characterTextures = new Texture(fileName);
        final TextureRegion[] tmp = TextureRegion.split(characterTextures, characterTextures.getWidth() / 29,
                characterTextures.getHeight())[0];

        // Texture when still
        still = tmp[11];

        // Texture when moving right
        TextureRegion[] characterTextureRight = new TextureRegion[4];
        characterTextureRight[0] = tmp[6];
        characterTextureRight[1] = tmp[7];
        characterTextureRight[2] = tmp[8];
        characterTextureRight[3] = tmp[9];
        animationRight = new Animation<>(1f / 8f, characterTextureRight);

        // Texture when moving left
        TextureRegion[] characterTextureLeft = new TextureRegion[4];
        characterTextureLeft[0] = tmp[14];
        characterTextureLeft[1] = tmp[15];
        characterTextureLeft[2] = tmp[16];
        characterTextureLeft[3] = tmp[17];
        animationLeft = new Animation<>(1f / 8f, characterTextureLeft);

        // Texture when moving up
        TextureRegion[] characterTextureUp = new TextureRegion[6];
        characterTextureUp[0] = tmp[0];
        characterTextureUp[1] = tmp[1];
        characterTextureUp[2] = tmp[2];
        characterTextureUp[3] = tmp[3];
        characterTextureUp[4] = tmp[4];
        characterTextureUp[5] = tmp[5];
        animationUp = new Animation<>(1f / 15f, characterTextureUp);

        // Texture when moving down
        TextureRegion[] characterTextureDown = new TextureRegion[4];
        characterTextureDown[0] = tmp[10];
        characterTextureDown[1] = tmp[11];
        characterTextureDown[2] = tmp[12];
        characterTextureDown[3] = tmp[13];
        animationDown = new Animation<>(1f / 8f, characterTextureDown);

        // Textures when attacking
        TextureRegion[] characterTextureAttackUp = new TextureRegion[2];
        characterTextureAttackUp[0] = tmp[18];
        characterTextureAttackUp[1] = tmp[19];
        animationAttackUp = new Animation<>(1f / 4f, characterTextureAttackUp);

        TextureRegion[] characterTextureAttackDown = new TextureRegion[3];
        characterTextureAttackDown[0] = tmp[23];
        characterTextureAttackDown[1] = tmp[24];
        characterTextureAttackDown[2] = tmp[25];
        animationAttackDown = new Animation<>(1f / 4f, characterTextureAttackDown);

        TextureRegion[] characterTextureAttackRight = new TextureRegion[3];
        characterTextureAttackRight[0] = tmp[20];
        characterTextureAttackRight[1] = tmp[21];
        characterTextureAttackRight[2] = tmp[22];
        animationAttackRight = new Animation<>(1f / 4f, characterTextureAttackRight);

        TextureRegion[] characterTextureAttackLeft = new TextureRegion[3];
        characterTextureAttackLeft[0] = tmp[26];
        characterTextureAttackLeft[1] = tmp[27];
        characterTextureAttackLeft[2] = tmp[28];
        animationAttackLeft = new Animation<>(1f / 4f, characterTextureAttackLeft);
    }

    /**
     * Recreates moving and attacking textures.
     * 
     * @param fileName
     */
    public void recreateTextures(final String fileName) {
        this.createTextures(fileName);
    }

    /**
     * Moves the character depending on the pressed key.
     */
    public abstract void move();

    /**
     * @return the still character texture
     */
    public TextureRegion getStillTexture() {
        return still;
    }

    /**
     * Getter for a frame of the attack animation from a given direction.
     * 
     * @param time
     * @param dir  Character direction
     * @return the attack animation frame
     */
    public TextureRegion getAttackFrame(final float time, final Direction dir) {
        return this.getAttackAnim(dir).getKeyFrame(time, false);
    }

    /**
     * Getter for the attack animation from a given direction.
     * 
     * @param dir Character direction
     * @return the attack animation
     */
    public Animation<TextureRegion> getAttackAnim(final Direction dir) {
        switch (dir) {
        case DOWN:
            return animationAttackDown;
        case LEFT:
            return animationAttackLeft;
        case RIGHT:
            return animationAttackRight;
        case UP:
            return animationAttackUp;
        default:
            return animationAttackDown;
        }
    }

    /**
     * Method to check whether the character is currently attacking.
     * 
     * @return true if the character is attacking
     */
    public Boolean getIsAttacking() {
        return isAttacking;
    }

    /**
     * Method to set the attacking status.
     * 
     * @param isAttacking
     */
    public void setIsAttacking(final Boolean isAttacking) {
        this.isAttacking = isAttacking;
        if (isAttacking.booleanValue()) {
        	soundNotifyAudiomanager(attackSoundPath, ATTACK_VOLUME);
        }
    }

    /**
     * @return the character model class
     */
    public Character getCharacter() {
        return character;
    }

    /**
     * @return the character direction
     */
    public Direction getDir() {
        return dir;
    }

    /**
     * Sets the character direction to the specified one.
     * 
     * @param dir the direction
     */
    public void setDir(final Direction dir) {
        this.dir = dir;
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
            return still;
        }
    }

    /**
     * @return the character rectangle for collisions.
     */
    public Rectangle getCharRect() {
        return charRect;
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

    /**
     * @return true if the hero is moving
     */
    public boolean getIsMoving() {
        return isMoving;
    }

    /**
     * Sets whether the hero is moving or not.
     * 
     * @param isMoving
     */
    public void setIsMoving(final boolean isMoving) {
        this.isMoving = isMoving;
    }
    
    /**
     * Sets the audioManager for this character
     * 
     * @param manager	the audioManager
     */
    public void setAudioManager(AudioManager manager) {
    	this.audioManager = manager;
    }
    
    /**
     * notifies the audioManager that an action occurred
     * and sends the path of  that action's sound effect.
     * 
     * @param path the path of the sound effect's file
     * @param volume the desired volume
     */
    public void soundNotifyAudiomanager(String path, float volume) {
    	audioManager.playSoundEffect(path, volume);
    }
    
    /**
     * notifies the audioManager that an action occurred
     * and sends the path of that action's Music.
     * 
     * @param path the path of the Music's file
     * @param volume the desired volume
     */
    public void musicNotifyAudiomanager(String path, Boolean looping, float volume) {
    	audioManager.playMusic(path, looping, volume);
    }
}
