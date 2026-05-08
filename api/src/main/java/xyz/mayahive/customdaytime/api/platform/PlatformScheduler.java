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

package xyz.mayahive.customdaytime.api.platform;

/**
 * Synchronous scheduler for running tasks on the main thread.
 */
public interface PlatformScheduler {

    /**
     * Runs a task repeatedly with the given interval in ticks.
     *
     * @param runnable the task to execute
     * @param intervalTicks number of ticks between executions (positive)
     * @return PlatformTask that can be canceled.
     */
    PlatformTask runRepeating(Runnable runnable, long intervalTicks);

    /**
     * Runs a task once after the given delay in ticks.
     *
     * @param runnable the task to execute
     * @param delayTicks number of ticks to wait before execution (non-negative)
     */
    void runLater(Runnable runnable, long delayTicks);

    /**
     * Runs task asynchronously.
     * @param runnable the task to execute
     */
    void runTaskAsync(Runnable runnable);
}
