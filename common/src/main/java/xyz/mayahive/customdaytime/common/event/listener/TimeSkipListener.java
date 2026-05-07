/*
 * Copyright (c) 2026 Seedim
 * This file is part of Custom Daytime, which is licensed under GPL-3.0.
 * See the LICENSE file in the project root for full license text.
 */

package xyz.mayahive.customdaytime.common.event.listener;

import lombok.RequiredArgsConstructor;
import xyz.mayahive.customdaytime.api.platform.Platform;
import xyz.mayahive.customdaytime.common.context.CustomDaytimeContext;
import xyz.mayahive.customdaytime.common.event.type.TimeSkipCause;
import xyz.mayahive.customdaytime.common.event.type.TimeSkipEvent;

@RequiredArgsConstructor
public class TimeSkipListener {

    private final CustomDaytimeContext context;

    public void onTimeSkipEvent(TimeSkipEvent event) {
        Platform platform = context.platform();

        TimeSkipCause cause = event.timeSkipCause();

        if (platform.debug()) platform.logger().info("Registered TimeSkipEvent. TimeSkipCause: " + cause);
    }

}
