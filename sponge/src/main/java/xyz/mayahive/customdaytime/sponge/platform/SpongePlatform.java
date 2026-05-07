/*
 * Copyright (c) 2026 Seedim
 * This file is part of Custom Daytime, which is licensed under GPL-3.0.
 * See the LICENSE file in the project root for full license text.
 */

package xyz.mayahive.customdaytime.sponge.platform;

import lombok.RequiredArgsConstructor;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.world.server.ServerWorld;
import xyz.mayahive.customdaytime.api.model.PlatformType;
import xyz.mayahive.customdaytime.api.model.WorldKey;
import xyz.mayahive.customdaytime.api.platform.Platform;
import xyz.mayahive.customdaytime.api.platform.PlatformLogger;
import xyz.mayahive.customdaytime.api.platform.PlatformScheduler;
import xyz.mayahive.customdaytime.api.platform.PlatformWorld;
import xyz.mayahive.customdaytime.sponge.CustomDaytimeSponge;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SpongePlatform implements Platform {

    private final CustomDaytimeSponge plugin;

    @Override
    public PlatformType platform() {
        return PlatformType.SPONGE;
    }

    @Override
    public String minecraftVersion() {
        return Sponge.platform().minecraftVersion().toString();
    }

    @Override
    public String projectVersion() {
        return plugin.container().metadata().version().toString();
    }

    @Override
    public Path configDirectory() {
        return plugin.configDir();
    }

    @Override
    public PlatformLogger logger() {
        return new SpongeLogger(plugin);
    }

    @Override
    public PlatformScheduler scheduler() {
        return new SpongeScheduler(plugin);
    }

    @Override
    public PlatformWorld world(WorldKey key) {
        ResourceKey worldKey = ResourceKey.of(key.namespace(), key.value());
        Optional<ServerWorld> world = Sponge.server().worldManager().world(worldKey);

        return new SpongeWorld(world.orElse(null));
    }

    @Override
    public List<PlatformWorld> worlds() {
        return Sponge.server().worldManager().worlds().stream().map(SpongeWorld::new).collect(Collectors.toList());
    }

    @Override
    public boolean debug() {
        return false;
    }
}
