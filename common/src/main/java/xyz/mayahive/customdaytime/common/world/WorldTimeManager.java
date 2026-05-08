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

package xyz.mayahive.customdaytime.common.world;

import lombok.RequiredArgsConstructor;
import xyz.mayahive.customdaytime.api.model.WorldKey;
import xyz.mayahive.customdaytime.common.context.CustomDaytimeContext;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class WorldTimeManager {

    private final CustomDaytimeContext context;
    private final Map<WorldKey, WorldTimeController> controllers = new HashMap<>();

    public void start(WorldKey key) {
        if (controllers.containsKey(key)) return;

        WorldTimeController controller = new WorldTimeController(context, key);
        controller.start();

        controllers.put(key, controller);
    }

    public void stop(WorldKey key) {
        WorldTimeController controller = controllers.get(key);

        if (controller == null) return;

        controller.stop();
    }

    public void stopAll() {
        controllers.values().forEach(WorldTimeController::stop);
        controllers.clear();
    }

    public void setTotalPlayers(WorldKey key, int totalPlayers) {
        WorldTimeController controller = controllers.get(key);
        if (controller == null) return;
        controller.totalPlayers(totalPlayers);
    }

    public void setSleepingPlayers(WorldKey key, int sleepingPlayers) {
        WorldTimeController controller = controllers.get(key);
        if (controller == null) return;
        controller.sleepingPlayers(sleepingPlayers);
    }
}
