/*
 * Copyright (c) 2026 Seedim
 * This file is part of Custom Daytime, which is licensed under GPL-3.0.
 * See the LICENSE file in the project root for full license text.
 */

package xyz.mayahive.customdaytime.paper.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import xyz.mayahive.customdaytime.common.event.EventBus;
import xyz.mayahive.customdaytime.common.event.type.WorldSleepingPlayerCountChangeEvent;
import xyz.mayahive.customdaytime.paper.platform.PaperWorld;

@RequiredArgsConstructor
public class BedActivityListener implements Listener {

    private final EventBus eventBus;

    @EventHandler
    public void onBedEnter(PlayerBedEnterEvent event) {
        PaperWorld world = new PaperWorld(event.getPlayer().getWorld());

        eventBus.fire(new WorldSleepingPlayerCountChangeEvent(world));
    }

    @EventHandler
    public void onBedLeave(PlayerBedLeaveEvent event) {
        PaperWorld world = new PaperWorld(event.getPlayer().getWorld());

        eventBus.fire(new WorldSleepingPlayerCountChangeEvent(world));
    }
}
