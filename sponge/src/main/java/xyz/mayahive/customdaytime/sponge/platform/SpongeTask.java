/*
 * Copyright (c) 2026 Seedim
 * This file is part of Custom Daytime, which is licensed under GPL-3.0.
 * See the LICENSE file in the project root for full license text.
 */

package xyz.mayahive.customdaytime.sponge.platform;

import lombok.RequiredArgsConstructor;
import org.spongepowered.api.scheduler.ScheduledTask;
import xyz.mayahive.customdaytime.api.platform.PlatformTask;

@RequiredArgsConstructor
public class SpongeTask implements PlatformTask {

    private final ScheduledTask task;

    @Override
    public void cancel() {
        task.cancel();
    }
}
