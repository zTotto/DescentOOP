package com.unibo.util;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.unibo.model.Level;

/**
 * Class to read the level list file.
 */
public class LevelListReader {

    private final List<Level> lvlList;
    private final List<String> errorList;
    private final FileHandle file;

    /**
     * Constructor for the reader, requires the path of the file with all the
     * levels.
     * 
     * @param file      File where to read levels
     * @param isFileExt true if the file is external
     */
    public LevelListReader(final FileHandle file, final Boolean isFileExt) {
        lvlList = new LinkedList<>();
        errorList = new LinkedList<>();
        this.file = file;
        final List<String> levelLines = Arrays.asList(file.child("LevelList.txt").readString().split("\\r?\\n"))
                .stream().filter(l -> !l.contains("//")).collect(Collectors.toList());
        for (final String s : levelLines) {
            try {
                loadLevel(s, isFileExt);
            } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException | GdxRuntimeException e) {
                if (e instanceof IllegalArgumentException) {
                    errorList.add("Wrong Item Formatting in file " + s);
                } else if (e instanceof ArrayIndexOutOfBoundsException) {
                    errorList.add("Wrong Item Formatting in file " + s);
                } else {
                    errorList.add("Couldn't load file " + s);
                }
            }
        }
    }

    private void loadLevel(final String levelPropertiesPath, final Boolean isFileExt) {
        lvlList.add(new LevelFileReader(file.child(levelPropertiesPath + "/" + levelPropertiesPath + ".txt"), isFileExt)
                .getLevel());
    }

    /**
     * Getter for succesfully loaded levels.
     * 
     * @return a list containing all the succesfully loaded levels
     */
    public List<Level> getLevels() {
        return this.lvlList;
    }

    /**
     * @return a list containing the levels unable to load and the cause
     */
    public List<String> getErrorList() {
        return errorList;
    }
}
