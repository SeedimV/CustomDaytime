/*
 * Copyright (c) 2026 Seedim
 * This file is part of Custom Daytime, which is licensed under GPL-3.0.
 * See the LICENSE file in the project root for full license text.
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
