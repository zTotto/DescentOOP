package com.unibo.model.items;

import com.unibo.util.Position;

/**
 * Class for the key that opens each level door.
 */
public class DoorKey extends Item {

    private DoorKey(final String name, final String id) {
        super(name, id);
    }

    /**
     * Constructor for the key.
     */
    public DoorKey() {
        this("Magic Key", "0");
    }

    @Override
    public DoorKey setPos(final Position p) {
        if (this.isPosNull()) {
            this.resetPos();
        }
        this.getPos().setPosition(p);
        return this;
    }
}
