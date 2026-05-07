/*
 * Copyright (c) 2026 Seedim
 * This file is part of Custom Daytime, which is licensed under GPL-3.0.
 * See the LICENSE file in the project root for full license text.
 */

package xyz.mayahive.customdaytime.sponge.platform;

import lombok.RequiredArgsConstructor;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.scheduler.ScheduledTask;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.util.Ticks;
import xyz.mayahive.customdaytime.api.platform.PlatformScheduler;
import xyz.mayahive.customdaytime.api.platform.PlatformTask;
import xyz.mayahive.customdaytime.sponge.CustomDaytimeSponge;

@RequiredArgsConstructor
public class SpongeScheduler implements PlatformScheduler {

    private final CustomDaytimeSponge plugin;

    @Override
    public PlatformTask runRepeating(Runnable runnable, long intervalTicks) {
        ScheduledTask task = Sponge.server().scheduler().submit(Task.builder().plugin(plugin.container()).execute(runnable).interval(Ticks.of(intervalTicks)).build());

        return new SpongeTask(task);
    }

    @Override
    public void runLater(Runnable runnable, long delayTicks) {
        Sponge.server().scheduler().submit(Task.builder().plugin(plugin.container()).execute(runnable).delay(Ticks.of(delayTicks)).build());
    }

    @Override
    public void runTaskAsync(Runnable runnable) {
        Sponge.asyncScheduler().submit(Task.builder().plugin(plugin.container()).execute(runnable).build());
    }
}
