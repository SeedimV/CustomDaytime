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
