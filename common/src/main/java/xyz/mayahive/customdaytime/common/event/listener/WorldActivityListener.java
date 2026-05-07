/*
 * Copyright (c) 2026 Seedim
 * This file is part of Custom Daytime, which is licensed under GPL-3.0.
 * See the LICENSE file in the project root for full license text.
 */

package xyz.mayahive.customdaytime.common.event.listener;

import lombok.RequiredArgsConstructor;
import xyz.mayahive.customdaytime.api.platform.PlatformWorld;
import xyz.mayahive.customdaytime.api.model.WorldKey;
import xyz.mayahive.customdaytime.api.platform.Platform;
import xyz.mayahive.customdaytime.common.context.CustomDaytimeContext;
import xyz.mayahive.customdaytime.common.event.type.WorldPlayerCountChangeEvent;
import xyz.mayahive.customdaytime.common.event.type.WorldSleepingPlayerCountChangeEvent;

@RequiredArgsConstructor
public class WorldActivityListener {

    private final CustomDaytimeContext context;

    public void onWorldPlayerCountChangeEvent(WorldPlayerCountChangeEvent event) {
        Platform platform = context.platform();
        PlatformWorld world = event.world();
        WorldKey key = world.key();
        int totalPlayers = world.playerCount();

        context.platform().scheduler().runLater(
                () -> context.worldTimeManager().setTotalPlayers(key, totalPlayers),
                1
        );

        if (platform.debug()) platform.logger().info("Registered WorldPlayerCountChangeEvent. Updated total players count to " + totalPlayers);
    }

    public void onWorldSleepingPlayerCountChange(WorldSleepingPlayerCountChangeEvent event) {
        Platform platform = context.platform();
        PlatformWorld world = event.world();
        WorldKey key = world.key();


        context.platform().scheduler().runLater(
                () -> {
                    int sleepingPlayers = world.sleepingPlayerCount();
                    context.worldTimeManager().setSleepingPlayers(key, sleepingPlayers);
                    if (platform.debug()) platform.logger().info("Registered WorldSleepingPlayerCountChangeEvent. Updated sleeping players count to " + sleepingPlayers);
                },
                1
        );
    }
}
