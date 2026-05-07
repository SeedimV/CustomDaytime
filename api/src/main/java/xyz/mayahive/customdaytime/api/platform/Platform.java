/*
 * Copyright (c) 2026 Seedim
 * This file is part of Custom Daytime, which is licensed under GPL-3.0.
 * See the LICENSE file in the project root for full license text.
 */

package xyz.mayahive.customdaytime.api.platform;

import xyz.mayahive.customdaytime.api.model.PlatformType;
import xyz.mayahive.customdaytime.api.model.WorldKey;

import java.nio.file.Path;
import java.util.List;

/**
 * Represents the current server platform.
 */
public interface Platform {

    /**
     * Returns the name of the platform/loader (e.g., "Paper", "Folia", "Fabric").
     *
     * @return the platform name
     */
    PlatformType platform();

    /**
     * Returns the version of Minecraft.
     *
     * @return Minecraft version
     */
    String minecraftVersion();

    /**
     * Returns the version of the project.
     *
     * @return the project version
     */
    String projectVersion();


    /**
     * Returns the path for project's config folder.
     *
     * @return path for config folder
     */
    Path configDirectory();

    /**
     * Returns the logger for outputting messages.
     *
     * @return the logger
     */
    PlatformLogger logger();

    /**
     * Returns the scheduler for running tasks.
     *
     * @return the scheduler
     */
    PlatformScheduler scheduler();

    /**
     * Returns the {@link PlatformWorld} corresponding to the given WorldKey.
     *
     * @param key the world key
     * @return the PlatformWorld, or null if not found
     */
    PlatformWorld world(WorldKey key);


    /**
     * Returns list of all loaded {@link PlatformWorld}.
     *
     * @return list of loaded worlds
     */
    List<PlatformWorld> worlds();

    /**
     * Returns whether project is in debug mode.
     * Debug mode adds some logging to ease debug during development.
     *
     * @return true if debug mode is on, false otherwise.
     */
    boolean debug();
}