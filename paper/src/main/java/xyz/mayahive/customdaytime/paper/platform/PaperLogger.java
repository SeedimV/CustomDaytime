/*
 * Copyright (c) 2026 Seedim
 * This file is part of Custom Daytime, which is licensed under GPL-3.0.
 * See the LICENSE file in the project root for full license text.
 */

package xyz.mayahive.customdaytime.paper.platform;

import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.Plugin;
import xyz.mayahive.customdaytime.api.platform.PlatformLogger;

@RequiredArgsConstructor
public class PaperLogger implements PlatformLogger {

    private final Plugin plugin;

    @Override
    public void info(String message) {
        plugin.getLogger().info(message);
    }

    @Override
    public void error(String message) {
        plugin.getLogger().severe(message);
    }
}
