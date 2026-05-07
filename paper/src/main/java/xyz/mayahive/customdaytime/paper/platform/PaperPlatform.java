/*
 * Copyright (c) 2026 Seedim
 * This file is part of Custom Daytime, which is licensed under GPL-3.0.
 * See the LICENSE file in the project root for full license text.
 */

package xyz.mayahive.customdaytime.paper.platform;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import xyz.mayahive.customdaytime.api.model.PlatformType;
import xyz.mayahive.customdaytime.api.model.WorldKey;
import xyz.mayahive.customdaytime.api.platform.Platform;
import xyz.mayahive.customdaytime.api.platform.PlatformLogger;
import xyz.mayahive.customdaytime.api.platform.PlatformScheduler;
import xyz.mayahive.customdaytime.api.platform.PlatformWorld;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PaperPlatform implements Platform {

    private final Plugin plugin;

    @Override
    public PlatformType platform() {
        return PlatformType.PAPER;
    }

    @Override
    public String minecraftVersion() {
        return Bukkit.getMinecraftVersion();
    }

    @Override
    public String projectVersion() {
        return plugin.getPluginMeta().getVersion();
    }

    @Override
    public Path configDirectory() {
        return plugin.getDataFolder().toPath();
    }

    @Override
    public PlatformLogger logger() {
        return new PaperLogger(plugin);
    }

    @Override
    public PlatformScheduler scheduler() {
        return new PaperScheduler(plugin);
    }

    @Override
    public PlatformWorld world(WorldKey key) {
        return new PaperWorld(plugin.getServer().getWorld(key.asString()));
    }

    @Override
    public List<PlatformWorld> worlds() {
        return plugin.getServer().getWorlds().stream().map(PaperWorld::new).collect(Collectors.toList());
    }

    @Override
    public boolean debug() {
        return false;
    }
}
