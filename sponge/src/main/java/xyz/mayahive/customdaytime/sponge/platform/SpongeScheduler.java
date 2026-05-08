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
