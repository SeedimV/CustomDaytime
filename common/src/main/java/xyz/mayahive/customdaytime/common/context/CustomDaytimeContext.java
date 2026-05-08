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
