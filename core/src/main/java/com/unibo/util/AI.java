package com.unibo.util;

import java.util.Random;

import com.unibo.maps.DescentMap;
import com.unibo.view.MobView;

/**
 * 
 */
public final class AI {
    public static Direction randomDirection(final MobView mob, final DescentMap map) {
        final Direction[] directions = Direction.values();
        final Random random = new Random();
        return directions[random.nextInt(directions.length)];
    }
}
