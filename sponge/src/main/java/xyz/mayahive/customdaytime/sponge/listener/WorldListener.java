/*
 * Copyright (c) 2026 Seedim
 * This file is part of Custom Daytime, which is licensed under GPL-3.0.
 * See the LICENSE file in the project root for full license text.
 */

package xyz.mayahive.customdaytime.sponge.listener;

import lombok.RequiredArgsConstructor;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.world.LoadWorldEvent;
import org.spongepowered.api.event.world.UnloadWorldEvent;
import xyz.mayahive.customdaytime.common.event.EventBus;
import xyz.mayahive.customdaytime.common.event.type.WorldLoadEvent;
import xyz.mayahive.customdaytime.common.event.type.WorldUnloadEvent;
import xyz.mayahive.customdaytime.sponge.platform.SpongeWorld;

@RequiredArgsConstructor
public class WorldListener {

    private final EventBus eventBus;

    @Listener
    public void onWorldLoad(LoadWorldEvent event) {
        eventBus.fire(new WorldLoadEvent(new SpongeWorld(event.world())));
    }

    @Listener
    public void onWorldUnload(UnloadWorldEvent event) {
        eventBus.fire(new WorldUnloadEvent(new SpongeWorld(event.world())));
    }

}
