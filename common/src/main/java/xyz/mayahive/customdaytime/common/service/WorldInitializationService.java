/*
 * Copyright (c) 2026 Seedim
 * This file is part of Custom Daytime, which is licensed under GPL-3.0.
 * See the LICENSE file in the project root for full license text.
 */

package xyz.mayahive.customdaytime.common.service;

import lombok.RequiredArgsConstructor;
import xyz.mayahive.customdaytime.api.platform.PlatformWorld;
import xyz.mayahive.customdaytime.common.context.CustomDaytimeContext;
import xyz.mayahive.customdaytime.common.event.type.WorldLoadEvent;

@RequiredArgsConstructor
public class WorldInitializationService {

    private final CustomDaytimeContext context;

    public void syncExistingWorlds() {
        for (PlatformWorld world : context.platform().worlds()) {
            context.eventBus().fire(new WorldLoadEvent(world));
        }
    }
}
