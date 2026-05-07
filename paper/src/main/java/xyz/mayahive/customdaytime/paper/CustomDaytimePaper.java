/*
 * Copyright (c) 2026 Seedim
 * This file is part of Custom Daytime, which is licensed under GPL-3.0.
 * See the LICENSE file in the project root for full license text.
 */

package xyz.mayahive.customdaytime.paper;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.mayahive.customdaytime.api.platform.Platform;
import xyz.mayahive.customdaytime.common.bootstrap.AbstractBootstrap;
import xyz.mayahive.customdaytime.common.event.EventBus;
import xyz.mayahive.customdaytime.common.service.ConfigService;
import xyz.mayahive.customdaytime.paper.listener.BedActivityListener;
import xyz.mayahive.customdaytime.paper.listener.TimeSkipListener;
import xyz.mayahive.customdaytime.paper.listener.WorldActivityListener;
import xyz.mayahive.customdaytime.paper.listener.WorldListener;
import xyz.mayahive.customdaytime.paper.platform.PaperPlatform;

public final class CustomDaytimePaper extends JavaPlugin {

    @Override
    public void onEnable() {

        new Metrics(this, 26910);

        AbstractBootstrap bootstrap = new AbstractBootstrap() {
            @Override
            protected Platform platform() {
                return new PaperPlatform(CustomDaytimePaper.this);
            }
        };

        bootstrap.initialize();

        EventBus eventBus = bootstrap.context().eventBus();
        ConfigService configService = bootstrap.context().configService();

        Bukkit.getPluginManager().registerEvents(new BedActivityListener(eventBus), this);
        Bukkit.getPluginManager().registerEvents(new TimeSkipListener(configService), this);
        Bukkit.getPluginManager().registerEvents(new WorldActivityListener(eventBus), this);
        Bukkit.getPluginManager().registerEvents(new WorldListener(eventBus), this);
    }
}
