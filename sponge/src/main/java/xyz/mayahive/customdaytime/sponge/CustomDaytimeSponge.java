/*
 * Copyright (c) 2026 Seedim
 * This file is part of Custom Daytime, which is licensed under GPL-3.0.
 * See the LICENSE file in the project root for full license text.
 */

package xyz.mayahive.customdaytime.sponge;

import com.google.inject.Inject;
import lombok.Getter;
import org.apache.logging.log4j.Logger;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.*;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;
import xyz.mayahive.customdaytime.api.platform.Platform;
import xyz.mayahive.customdaytime.common.bootstrap.AbstractBootstrap;
import xyz.mayahive.customdaytime.common.event.EventBus;
import xyz.mayahive.customdaytime.sponge.listener.SleepingListener;
import xyz.mayahive.customdaytime.sponge.listener.WorldActivityListener;
import xyz.mayahive.customdaytime.sponge.listener.WorldListener;
import xyz.mayahive.customdaytime.sponge.platform.SpongePlatform;

import java.lang.invoke.MethodHandles;
import java.nio.file.Path;

@Getter
@Plugin("customdaytime")
public class CustomDaytimeSponge {

    private final PluginContainer container;
    private final Logger logger;
    private final Path configDir;

    @Inject
    CustomDaytimeSponge(final PluginContainer container, final Logger logger, final @ConfigDir(sharedRoot = false) Path configDir) {
        this.container = container;
        this.logger = logger;
        this.configDir = configDir;
    }

    @Listener
    public void onConstructPlugin(final ConstructPluginEvent event) {
        this.logger.info("Constructing Custom Daytime");
    }

    @Listener
    public void onServerStarted(final StartedEngineEvent<Server> event) {

        AbstractBootstrap bootstrap = new AbstractBootstrap() {
            @Override
            protected Platform platform() {
                return new SpongePlatform(CustomDaytimeSponge.this);
            }
        };

        bootstrap.initialize();
        EventBus eventBus = bootstrap.context().eventBus();

        Sponge.eventManager().registerListeners(container, new WorldListener(eventBus), MethodHandles.lookup());
        Sponge.eventManager().registerListeners(container, new WorldActivityListener(eventBus), MethodHandles.lookup());
        Sponge.eventManager().registerListeners(container, new SleepingListener(eventBus), MethodHandles.lookup());
    }
}
