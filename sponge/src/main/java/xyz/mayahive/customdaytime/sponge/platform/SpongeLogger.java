/*
 * Copyright (c) 2026 Seedim
 * This file is part of Custom Daytime, which is licensed under GPL-3.0.
 * See the LICENSE file in the project root for full license text.
 */

package xyz.mayahive.customdaytime.sponge.platform;

import lombok.RequiredArgsConstructor;
import xyz.mayahive.customdaytime.api.platform.PlatformLogger;
import xyz.mayahive.customdaytime.sponge.CustomDaytimeSponge;

@RequiredArgsConstructor
public class SpongeLogger implements PlatformLogger {

    private final CustomDaytimeSponge plugin;

    @Override
    public void info(String message) {
        plugin.logger().info(message);
    }

    @Override
    public void error(String message) {
        plugin.logger().warn(message);
    }
}
