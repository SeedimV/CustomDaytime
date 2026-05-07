/*
 * Copyright (c) 2026 Seedim
 * This file is part of Custom Daytime, which is licensed under GPL-3.0.
 * See the LICENSE file in the project root for full license text.
 */

package xyz.mayahive.customdaytime.common.registry;

import xyz.mayahive.customdaytime.common.context.CustomDaytimeContext;
import xyz.mayahive.customdaytime.common.event.EventBus;
import xyz.mayahive.customdaytime.common.event.listener.*;
import xyz.mayahive.customdaytime.common.event.type.*;

public final class CommonEventRegistry {

    public static void initialize(CustomDaytimeContext context) {

        EventBus eventBus = context.eventBus();

        eventBus.register(TimeSkipEvent.class, new TimeSkipListener(context)::onTimeSkipEvent);

        eventBus.register(WorldPlayerCountChangeEvent.class, new WorldActivityListener(context)::onWorldPlayerCountChangeEvent);

        eventBus.register(WorldSleepingPlayerCountChangeEvent.class, new WorldActivityListener(context)::onWorldSleepingPlayerCountChange);

        eventBus.register(WorldLoadEvent.class, new WorldLifeCycleListener(context)::onWorldLoad);

        eventBus.register(WorldUnloadEvent.class, new WorldLifeCycleListener(context)::onWorldUnload);

    }

}
