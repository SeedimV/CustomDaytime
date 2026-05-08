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

package xyz.mayahive.customdaytime.common.event.listener;

import lombok.RequiredArgsConstructor;
import xyz.mayahive.customdaytime.api.model.WorldKey;
import xyz.mayahive.customdaytime.api.platform.Platform;
import xyz.mayahive.customdaytime.api.platform.PlatformWorld;
import xyz.mayahive.customdaytime.common.context.CustomDaytimeContext;
import xyz.mayahive.customdaytime.common.event.type.WorldLoadEvent;
import xyz.mayahive.customdaytime.common.event.type.WorldUnloadEvent;

@RequiredArgsConstructor
public class WorldLifeCycleListener {

    private final CustomDaytimeContext context;

    public void onWorldLoad(WorldLoadEvent event) {
        Platform platform = context.platform();
        PlatformWorld world = event.world();
        WorldKey key = world.key();

        if (!context.configService().getRootKeys().contains(key.asString())) {
            if (platform.debug()) platform.logger().info("WorldLoadEvent: World '" + key.asString() + "' is not defined in config, skipping.");
            return;
        }

        context.worldCache().registerWorld(world);

        context.worldTimeManager().start(key);

        if (platform.debug()) platform.logger().info("Registered WorldLoadEvent for world: " + key.asString());
    }

    public void onWorldUnload(WorldUnloadEvent event) {
        Platform platform = context.platform();
        PlatformWorld world = event.world();
        WorldKey key = world.key();

        if (!context.configService().getRootKeys().contains(key.asString())) {
            if (platform.debug()) platform.logger().info("WorldUnloadEvent: World '" + key.asString() + "' is not defined in config, skipping.");
            return;
        }

        context.worldCache().unregisterWorld(world);

        context.worldTimeManager().stop(key);

        if (platform.debug()) platform.logger().info("Registered WorldUnloadEvent for world: " + key.asString());
    }

}
