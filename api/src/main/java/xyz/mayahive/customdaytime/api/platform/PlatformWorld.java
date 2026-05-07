/*
 * Copyright (c) 2026 Seedim
 * This file is part of Custom Daytime, which is licensed under GPL-3.0.
 * See the LICENSE file in the project root for full license text.
 */

package xyz.mayahive.customdaytime.api.platform;

import xyz.mayahive.customdaytime.api.model.WorldKey;

import java.util.Optional;

/**
 * Represents a world in a platform-agnostic way.
 * Provides access to world properties such as players, and game rules across different loaders.
 */
public interface PlatformWorld {

    /**
     * Returns the world key such as "minecraft:overworld".
     *
     * @return the world's key
     */
    WorldKey key();

    /**
     * Returns a human-readable key for this world, e.g., "minecraft:overworld".
     *
     * @return the world key as a string
     */
    String keyAsString();

    /**
     * Returns the current time of the world if available.
     *
     * @return an Optional containing the world time, or empty if unavailable
     */
    Optional<Long> time();

    /**
     * Sets the current time of the world.
     *
     * @param time the new time value
     * @return true if the time was successfully set, false otherwise
     */
    boolean time(long time);


    /**
     * Returns number of players in this world.
     *
     * @return player count
     */
    int playerCount();

    /**
     * Returns number of sleeping players in this world.
     *
     * @return number of sleeping players
     */
    int sleepingPlayerCount();

    /**
     * Returns whether the "doDaylightCycle" or equivalent game rule is enabled.
     *
     * @return true if time advances automatically, false otherwise
     */
    boolean gameRuleAdvanceTime();

    /**
     * Returns the required percentage of players sleeping to skip the night,
     * if the platform supports this game rule.
     *
     * @return a value between 0.0 and 100.0
     */
    int gameRulePlayerSleepingPercentage();
}
