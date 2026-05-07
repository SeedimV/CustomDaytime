/*
 * Copyright (c) 2026 Seedim
 * This file is part of Custom Daytime, which is licensed under GPL-3.0.
 * See the LICENSE file in the project root for full license text.
 */

package xyz.mayahive.customdaytime.api.platform;

public interface PlatformTask {

    /**
     * Cancel this task.
     * If the task is repeating, it will stop further executions.
     */
    void cancel();
}
