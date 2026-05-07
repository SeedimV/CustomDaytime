/*
 * Copyright (c) 2026 Seedim
 * This file is part of Custom Daytime, which is licensed under GPL-3.0.
 * See the LICENSE file in the project root for full license text.
 */

package xyz.mayahive.customdaytime.common.context;

import lombok.Getter;
import xyz.mayahive.customdaytime.api.platform.Platform;
import xyz.mayahive.customdaytime.common.cache.WorldCache;
import xyz.mayahive.customdaytime.common.event.EventBus;
import xyz.mayahive.customdaytime.common.service.ConfigService;
import xyz.mayahive.customdaytime.common.world.WorldTimeManager;

public class CustomDaytimeContext {

    @Getter
    private final Platform platform;

    @Getter
    private final WorldCache worldCache;

    @Getter
    private final ConfigService configService;

    @Getter
    private final WorldTimeManager worldTimeManager;

    @Getter
    private final EventBus eventBus;

    public CustomDaytimeContext(Platform platform) {
        this.platform = platform;
        this.worldCache = new WorldCache();
        this.configService = new ConfigService(platform);
        this.worldTimeManager = new WorldTimeManager(this);
        this.eventBus = new EventBus();
    }
}
