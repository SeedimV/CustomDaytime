/*
 * Copyright (c) 2026 Seedim
 * This file is part of Custom Daytime, which is licensed under GPL-3.0.
 * See the LICENSE file in the project root for full license text.
 */

package xyz.mayahive.customdaytime.common.bootstrap;

import xyz.mayahive.customdaytime.api.platform.*;
import xyz.mayahive.customdaytime.common.context.CustomDaytimeContext;
import xyz.mayahive.customdaytime.common.service.CommonInitializerService;

public abstract class AbstractBootstrap {

    protected abstract Platform platform();

    private CustomDaytimeContext context;

    public CustomDaytimeContext context() {
        if (context == null) {
            throw new IllegalStateException("Context not initialized yet.");
        }
        return context;
    }

    public void initialize() {
        Platform platform = platform();

        this.context = new CustomDaytimeContext(platform);
        CommonInitializerService.initialize(context);

        /*
        Bootstrap for Common
        - Load Translations
        - Check for updates
        */
    }
}
