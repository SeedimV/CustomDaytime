/*
 * Copyright (c) 2026 Seedim
 * This file is part of Custom Daytime, which is licensed under GPL-3.0.
 * See the LICENSE file in the project root for full license text.
 */

package xyz.mayahive.customdaytime.paper.platform;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import lombok.RequiredArgsConstructor;
import xyz.mayahive.customdaytime.api.platform.PlatformTask;

@RequiredArgsConstructor
public class PaperTask implements PlatformTask {

    private final ScheduledTask task;

    @Override
    public void cancel() {
        task.cancel();
    }
}
