package com.unibo.util;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.badlogic.gdx.files.FileHandle;
import com.unibo.maps.DescentMap;
import com.unibo.maps.DescentMapImpl;
import com.unibo.model.Level;
import com.unibo.model.Mob;
import com.unibo.model.items.DoorKey;
import com.unibo.model.items.HealthPotion;
import com.unibo.model.items.ManaPotion;
import com.unibo.model.items.Weapon;
import com.unibo.model.items.WearableItem;

/**
 * Class to read level data from text files.
 */
public class LevelFileReader {

    private final List<HealthPotion> healthPotions;
    private final List<ManaPotion> manaPotions;
    private final List<WearableItem> wearable;
    private final DoorKey key;
    private final List<Mob> mobs;
    private final List<Weapon> weapons;
    private final Pair<DescentMap, Float> map;
    private final Position doorPosition;

    /**
     * Reads a file.
     * 
     * @param file      File where to read level properties
     * @param isFileExt true if the file is external
     */
    public LevelFileReader(final FileHandle file, final Boolean isFileExt) {
        final List<String> linesList = Arrays.asList(file.readString().split("\\r?\\n")).stream()
                .filter(l -> !l.contains("//")).collect(Collectors.toList());
        healthPotions = new LinkedList<>();
        manaPotions = new LinkedList<>();
        wearable = new LinkedList<>();
        key = new DoorKey();
        mobs = new LinkedList<>();
        weapons = new LinkedList<>();
        map = new Pair<>();
        doorPosition = new Position();
        readMap(linesList.stream().filter(s -> s.contains("MapImpl")).limit(1).collect(Collectors.joining()),
                isFileExt);
        readHealthPotions(linesList.stream().filter(s -> s.contains("HealthPotionStats")).collect(Collectors.toList()));
        readManaPotions(linesList.stream().filter(s -> s.contains("ManaPotion")).collect(Collectors.toList()));
        readWearable(linesList.stream().filter(s -> s.contains("Wearable")).collect(Collectors.toList()));
        readKey(linesList.stream().filter(s -> s.contains("DoorKey")).limit(1).collect(Collectors.joining()));
        readMobs(linesList.stream().filter(s -> s.contains("MobStats")).collect(Collectors.toList()));
        readWeapons(linesList.stream().filter(s -> s.contains("WeaponStats")).collect(Collectors.toList()));
        readDoorPosition(
                linesList.stream().filter(s -> s.contains("DoorPosition")).limit(1).collect(Collectors.joining()));
    }

    private void readHealthPotions(final List<String> potionLines) {
        potionLines.stream()
                .forEach(l -> healthPotions.add(new HealthPotion(HealthPotionStats.valueOf(l.split(" ")[1]), "0")
                        .setPos(new Position(Integer.parseInt(l.split(" ")[2].split(",")[0]),
                                Integer.parseInt(l.split(" ")[2].split(",")[1])))));
    }

    private void readManaPotions(final List<String> potionLines) {
        potionLines.stream()
                .forEach(l -> manaPotions.add(new ManaPotion(l.split(" ")[1], "0", Double.parseDouble(l.split(" ")[2]))
                        .setPos(new Position(Integer.parseInt(l.split(" ")[2].split(",")[0]),
                                Integer.parseInt(l.split(" ")[2].split(",")[1])))));
    }

    private void readWearable(final List<String> wearableLines) {
        for (final String l : wearableLines) {
            final String[] str = l.split(" ");
            final WearableItem.Builder w = new WearableItem.Builder(str[1], "0");
            for (final String s : str) {
                if (s.contains("Hp")) {
                    w.health(Double.parseDouble(s.split(":")[1]));
                }
                if (s.contains("Exp")) {
                    w.exp(Integer.parseInt(s.split(":")[1]));
                }
                if (s.contains("Dmg")) {
                    w.power(Double.parseDouble(s.split(":")[1]));
                }
                if (s.contains(",")) {
                    final WearableItem wI = w.build();
                    wI.setPos(new Position(Integer.parseInt(s.split(",")[0]), Integer.parseInt(s.split(",")[1])));
                    wearable.add(wI);
                    break;
                }
            }
        }
    }

    private void readKey(final String keyLine) {
        key.setPos(new Position(Integer.parseInt(keyLine.split(" ")[1].split(",")[0]),
                Integer.parseInt(keyLine.split(" ")[1].split(",")[1])));
    }

    private void readMobs(final List<String> mobLines) {
        mobLines.stream()
                .forEach(l -> mobs.add(new Mob(MobStats.valueOf(l.split(" ")[1]), Integer.parseInt(l.split(" ")[2]))
                        .setPos(new Position(Integer.parseInt(l.split(" ")[3].split(",")[0]),
                                Integer.parseInt(l.split(" ")[3].split(",")[1])))));
    }

    private void readWeapons(final List<String> weaponLines) {
        weaponLines.stream()
                .forEach(l -> weapons.add(new Weapon(WeaponStats.valueOf(l.split(" ")[1]), "0")
                        .setPos(new Position(Integer.parseInt(l.split(" ")[2].split(",")[0]),
                                Integer.parseInt(l.split(" ")[2].split(",")[1])))));
    }

    private void readMap(final String mapLine, final Boolean isExt) {
        map.setFirst(new DescentMapImpl(mapLine.split(" ")[1],
                new Position(Integer.parseInt(mapLine.split(" ")[2].split(",")[0]),
                        Integer.parseInt(mapLine.split(" ")[2].split(",")[1])),
                Float.parseFloat(mapLine.split(" ")[3]), isExt, mapLine.split(" ")[4]));
        map.setSecond(Float.parseFloat(mapLine.split(" ")[3]));
    }

    private void readDoorPosition(final String doorPosLine) {
        doorPosition.setPosition(new Position(Integer.parseInt(doorPosLine.split(" ")[1].split(",")[0]),
                Integer.parseInt(doorPosLine.split(" ")[1].split(",")[1])));
    }

    /**
     * Gets the potions from the file.
     * 
     * @return a list containing all the potions of the file
     */
    public List<HealthPotion> getHealthPotions() {
        return healthPotions;
    }

    /**
     * Gets the potions from the file.
     * 
     * @return a list containing all the potions of the file
     */
    public List<ManaPotion> getManaPotions() {
        return manaPotions;
    }

    /**
     * Gets the wearable from the file.
     * 
     * @return a list containing all the wearable of the file
     */
    public List<WearableItem> getWearable() {
        return wearable;
    }

    /**
     * Gets the keys from the file.
     * 
     * @return a list containing all the keys of the file
     */
    public DoorKey getKey() {
        return key;
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

    /**
     * Gets the map from the file.
     * 
     * @return a pair containing a map and its unitScale
     */
    public Pair<DescentMap, Float> getMap() {
        return map;
    }

    /**
     * Builds a level with the provided properties.
     * 
     * @return the level
     */
    public Level getLevel() {
        final Level lvl = new Level(map);
        mobs.forEach(m -> lvl.addEnemies(m));
        healthPotions.forEach(p -> lvl.addItems(p));
        manaPotions.forEach(p -> lvl.addItems(p));
        wearable.forEach(w -> lvl.addItems(w));
        weapons.forEach(w -> lvl.addItems(w));
        lvl.addItems(key);
        lvl.setDoorPosition(doorPosition);
        return lvl;
    }
}
