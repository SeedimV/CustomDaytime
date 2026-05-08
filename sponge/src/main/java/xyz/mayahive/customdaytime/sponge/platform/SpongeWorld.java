/*
 *     Copyright (c) 2026 Seedim
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.mayahive.customdaytime.sponge.platform;

import lombok.RequiredArgsConstructor;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.util.MinecraftDayTime;
import org.spongepowered.api.util.Ticks;
import org.spongepowered.api.world.gamerule.GameRules;
import org.spongepowered.api.world.server.ServerWorld;
import xyz.mayahive.customdaytime.api.model.WorldKey;
import xyz.mayahive.customdaytime.api.platform.PlatformWorld;

import java.util.Optional;

@RequiredArgsConstructor
public class SpongeWorld implements PlatformWorld {

    private final ServerWorld world;

    @Override
    public WorldKey key() {
        ResourceKey key = world.key();
        return new WorldKey(key.namespace(), key.value());
    }

    @Override
    public String keyAsString() {
        return world.key().formatted();
    }

    @Override
    public Optional<Long> time() {
        //return Optional.of(world.properties().dayTime().asTicks().ticks());
        return Optional.of(((ServerLevel) world).getDayTime());
    }

    @Override
    public boolean time(long time) {
        if (world == null) {
            return false;
        }
        world.properties().setDayTime(MinecraftDayTime.of(Sponge.server(), Ticks.of(time)));
        return true;
    }

    @Override
    public int playerCount() {
        return world.players().size();
    }

    @Override
    public int sleepingPlayerCount() {
        int count = 0;

        for (ServerPlayer player : world.players()) {
            if (player.get(Keys.IS_SLEEPING).orElse(false)) {
                count++;
            }
        }
        return count;
    }

    @Override
    public boolean gameRuleAdvanceTime() {
        return world.properties().gameRule(GameRules.ADVANCE_TIME.get());
    }

    @Override
    public int gameRulePlayerSleepingPercentage() {
        return world.properties().gameRule(GameRules.PLAYERS_SLEEPING_PERCENTAGE.get());
    }
}
