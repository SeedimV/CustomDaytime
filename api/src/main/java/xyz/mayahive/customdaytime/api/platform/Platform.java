/*
 *     Copyright (c) 2026 Seedim
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
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