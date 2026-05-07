/*
 * Copyright (c) 2026 Seedim
 * This file is part of Custom Daytime, which is licensed under GPL-3.0.
 * See the LICENSE file in the project root for full license text.
 */

package xyz.mayahive.customdaytime.common.service;

import xyz.mayahive.customdaytime.common.context.CustomDaytimeContext;
import xyz.mayahive.customdaytime.common.registry.CommonEventRegistry;

public class CommonInitializerService {

    public static void initialize(CustomDaytimeContext context) {
        CommonEventRegistry.initialize(context);
        new WorldInitializationService(context).syncExistingWorlds();
        new UpdateCheckerService(context.platform(), "C7YliNqw");
    }
}
