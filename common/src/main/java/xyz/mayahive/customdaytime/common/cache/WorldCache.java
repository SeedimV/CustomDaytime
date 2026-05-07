/*
 * Copyright (c) 2026 Seedim
 * This file is part of Custom Daytime, which is licensed under GPL-3.0.
 * See the LICENSE file in the project root for full license text.
 */

package xyz.mayahive.customdaytime.common.cache;

import xyz.mayahive.customdaytime.api.platform.PlatformWorld;
import xyz.mayahive.customdaytime.api.model.WorldKey;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WorldCache {
    private final Map<WorldKey, PlatformWorld> worldCache = new HashMap<>();

    public PlatformWorld getWorld(WorldKey identifier) {
        return worldCache.get(identifier);
    }

    public void registerWorld(PlatformWorld world) {
        worldCache.put(world.key(), world);
    }

    public void unregisterWorld(PlatformWorld world) {
        worldCache.remove(world.key());
    }

    public Collection<PlatformWorld> getWorlds() {
        return Collections.unmodifiableCollection(worldCache.values());
    }
}
