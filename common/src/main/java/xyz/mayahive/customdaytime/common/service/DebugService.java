/*
 * Copyright (c) 2026 Seedim
 * This file is part of Custom Daytime, which is licensed under GPL-3.0.
 * See the LICENSE file in the project root for full license text.
 */

package xyz.mayahive.customdaytime.common.service;

import xyz.mayahive.customdaytime.common.context.CustomDaytimeContext;

public class DebugService {

    public static void log(CustomDaytimeContext context, String message){
        if (context.platform().debug()) {
            context.platform().logger().info(message);
        }
    }
}
