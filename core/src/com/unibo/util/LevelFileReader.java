package com.unibo.util;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.unibo.model.Mob;
import com.unibo.model.items.DoorKey;
import com.unibo.model.items.HealthPotion;
import com.unibo.model.items.Weapon;

/**
 * Class to read level data from text files.
 */
public class LevelFileReader {

    private final List<HealthPotion> potions;
    private final List<DoorKey> keys;
    private final List<Mob> mobs;
    private final List<Weapon> weapons;

    /**
     * Reads a file.
     * 
     * @param levelFilePath
     */
    public LevelFileReader(final String levelFilePath) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        FileHandle handle = Gdx.files.internal(levelFilePath);
        List<String> linesList = Arrays.asList(handle.readString().split("\\r?\\n")).stream()
                .filter(l -> !l.contains("//")).collect(Collectors.toList());
        System.out.println(linesList);
        potions = new LinkedList<>();
        keys = new LinkedList<>();
        mobs = new LinkedList<>();
        weapons = new LinkedList<>();
        readPotions(linesList.stream().filter(s -> s.contains("HealthPotionStats")).collect(Collectors.toList()));
        readKey(linesList.stream().filter(s -> s.contains("DoorKey")).collect(Collectors.toList()));
        readMobs(linesList.stream().filter(s -> s.contains("MobStats")).collect(Collectors.toList()));
        readWeapons(linesList.stream().filter(s -> s.contains("WeaponStats")).collect(Collectors.toList()));
    }

    private void readPotions(final List<String> itemLines) {
        itemLines.stream()
                .forEach(l -> potions.add(new HealthPotion(HealthPotionStats.valueOf(l.split(" ")[1]), "0")
                        .setPos(new Position(Integer.parseInt(l.split(" ")[2].split(",")[0]),
                                Integer.parseInt(l.split(" ")[2].split(",")[1])))));
    }

    private void readKey(final List<String> itemLines) {
        itemLines.stream().forEach(
                l -> keys.add(new DoorKey().setPos(new Position(Integer.parseInt(l.split(" ")[1].split(",")[0]),
                        Integer.parseInt(l.split(" ")[1].split(",")[1])))));
    }

    private void readMobs(final List<String> itemLines) {
        itemLines.stream()
                .forEach(l -> mobs.add(new Mob(MobStats.valueOf(l.split(" ")[1]), Integer.parseInt(l.split(" ")[2]))
                        .setPos(new Position(Integer.parseInt(l.split(" ")[3].split(",")[0]),
                                Integer.parseInt(l.split(" ")[3].split(",")[1])))));
    }

    private void readWeapons(final List<String> itemLines) {
        itemLines.stream()
                .forEach(l -> weapons.add(new Weapon(WeaponStats.valueOf(l.split(" ")[1]), "0")
                        .setPos(new Position(Integer.parseInt(l.split(" ")[2].split(",")[0]),
                                Integer.parseInt(l.split(" ")[2].split(",")[1])))));
    }

    /**
     * Gets the potions from the file.
     * 
     * @return a list containing all the potions of the file
     */
    public List<HealthPotion> getHealthPotions() {
        return potions;
    }

    /**
     * Gets the keys from the file.
     * 
     * @return a list containing all the keys of the file
     */
    public List<DoorKey> getKeys() {
        return keys;
    }

    /**
     * Gets the mobs from the file.
     * 
     * @return a list containing all the mobs of the file
     */
    public List<Mob> getMobs() {
        return mobs;
    }

    /**
     * Gets the weapons from the file.
     * 
     * @return a list containing all the weapons of the file
     */
    public List<Weapon> getWeapons() {
        return weapons;
    }
}
