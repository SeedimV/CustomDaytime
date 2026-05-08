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

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import xyz.mayahive.customdaytime.api.platform.PlatformTask;
import xyz.mayahive.customdaytime.api.platform.PlatformWorld;
import xyz.mayahive.customdaytime.api.model.WorldKey;
import xyz.mayahive.customdaytime.common.context.CustomDaytimeContext;
import xyz.mayahive.customdaytime.common.service.ConfigService;
import xyz.mayahive.customdaytime.common.service.DebugService;

@RequiredArgsConstructor
public class WorldTimeController {

    private final CustomDaytimeContext context;
    private final WorldKey key;
    private PlatformTask task;
    private PlatformWorld world;

    private double dayIncrement;
    private double nightIncrement;
    private double accelerationMultiplier;
    private long lastObservedWorldTime = -1;
    boolean accelerationEnabled = true;

    @Getter
    @Setter
    private int totalPlayers = 0;

    @Getter
    @Setter
    private int sleepingPlayers = 0;

    private double carry = 0.0;
    private boolean accelerating =  false;
    private long lastCycleTime = -1;
    private long baseTime;
    private long accumulatedTicks;

    public void start() {
        world = context.worldCache().getWorld(key);
        if (world != null) {

            world.time().ifPresent(time -> {
                baseTime = time;
                accumulatedTicks = 0;
            });

            totalPlayers = world.playerCount();
            sleepingPlayers = world.sleepingPlayerCount();
            DebugService.log(context, "Starting World Time Controller for world: " + key.asString());
        } else {
            DebugService.log(context, "World not loaded yet: " + key.asString());
        }

        reloadConfig();
        task = context.platform().scheduler().runRepeating(this::tick, 1);
    }

    public void stop() {
        if (task != null) {
            task.cancel();
            task = null;
            DebugService.log(context, "Stopping WorldTimeController for world: " + key.asString());
        }
    }

    private void reloadConfig() {
        ConfigService configService = context.configService();

        accelerationEnabled = configService.getConfigValue(Boolean.class, true, key.asString(), "accelerationEnabled");
        double dayMinutes = configService.getConfigValue(Double.class, 10.0, key.asString(), "dayLength");
        double nightMinutes = configService.getConfigValue(Double.class, 10.0, key.asString(), "nightLength");
        accelerationMultiplier = configService.getConfigValue(Double.class, 100.0, key.asString(), "AccelerationMultiplier");

        dayIncrement = calculateIncrement(true, dayMinutes, nightMinutes);
        nightIncrement = calculateIncrement(false, dayMinutes, nightMinutes);

        DebugService.log(context, "Reloaded config for world " + key.asString() + " | dayIncrement=" + dayIncrement + " nightIncrement=" + nightIncrement + " accelerationMultiplier=" + accelerationMultiplier);
    }

    private void tick() {
        if (world == null) {
            DebugService.log(context, "World is null, stopping controller: " + key.asString());
            stop();
            return;
        }

        if (!world.gameRuleAdvanceTime()) return;

        world.time().ifPresent(currentTime -> updateWorld(world, currentTime));
    }

    private void updateWorld(PlatformWorld world, long currentTime) {

        if (lastObservedWorldTime != -1) {
            long expectedTime = baseTime + accumulatedTicks;
            if (Math.abs(currentTime - expectedTime) > 20) {
                DebugService.log(context, "External time change detected. Resynchronizing controller.");
                baseTime = currentTime;
                accumulatedTicks = 0;
                carry = 0;
            }
        }

        lastObservedWorldTime = currentTime;

        long dayCycleTime = currentTime % 24000;
        boolean isDay = dayCycleTime < 12000;

        if (lastCycleTime != -1) {
            boolean lastWasDay = (lastCycleTime % 24000) < 12000;
            if (lastWasDay != isDay) {
                DebugService.log(context, "Day/Night cycle change detected for world " + world.keyAsString() + " (lastCycleTime=" + lastCycleTime + ", currentTime=" + currentTime + ")");
                reloadConfig();
                carry = 0;
            }
        }

        lastCycleTime = currentTime;

        double increment = isDay ? dayIncrement : nightIncrement;

        boolean nowAccelerating = shouldAccelerate(world, isDay);

        if (nowAccelerating) {
            increment *= accelerationMultiplier;
        }

        carry += increment;

        long wholeTicks = (long) carry;

        if (wholeTicks > 0) {
            accumulatedTicks += wholeTicks;
            carry -= wholeTicks;

            boolean setTime = world.time(baseTime + accumulatedTicks);
            if (!setTime) {
                context.platform().logger().error("Failed to set world time for world " + key.asString());
            }
        }

        handleAccelerationEvent(world, nowAccelerating);
    }

    private boolean shouldAccelerate(PlatformWorld world, boolean isDay) {

        if (!accelerationEnabled) return false;

        if (isDay) return false;

        if (totalPlayers == 0) return false;

        double percentage = ((double) sleepingPlayers / totalPlayers) * 100;
        int required = world.gameRulePlayerSleepingPercentage();

        if (context.platform().debug()) {
            context.platform().logger().info("Should Accelerate boolean value for world " + key.asString() + " (percentage=" + percentage + " / " + required + ")");
        }

        return percentage >= required;
    }

    private void handleAccelerationEvent(PlatformWorld world, boolean nowAccelerating) {
        if (nowAccelerating && !accelerating) {
            DebugService.log(context, "Time acceleration started for world " + world.keyAsString());
        } else if (!nowAccelerating && accelerating) {
            DebugService.log(context, "Time acceleration stopped for world " + world.keyAsString());
        }
        accelerating = nowAccelerating;
    }

    private double calculateIncrement(boolean isDay, double dayMinutes, double nightMinutes) {

        long halfDayWorldTicks = 12000;
        long realSeconds = (long) ((isDay ? dayMinutes : nightMinutes) * 60);
        long serverTicks = realSeconds * 20;

        return (double) halfDayWorldTicks / serverTicks;
    }
}
