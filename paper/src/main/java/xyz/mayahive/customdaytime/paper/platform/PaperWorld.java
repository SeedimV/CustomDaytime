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

package xyz.mayahive.customdaytime.paper.platform;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.key.Key;
import org.bukkit.GameRules;
import org.bukkit.World;
import org.bukkit.entity.Player;
import xyz.mayahive.customdaytime.api.model.WorldKey;
import xyz.mayahive.customdaytime.api.platform.PlatformWorld;

import java.util.Optional;

@RequiredArgsConstructor
public class PaperWorld implements PlatformWorld {

    private final World world;

    @Override
    public WorldKey key() {
        Key key = world.getKey();
        return new WorldKey(key.namespace(), key.value());
    }

    @Override
    public String keyAsString() {
        return world.getKey().asString();
    }

    @Override
    public Optional<Long> time() {
        return Optional.of(world.getFullTime());
    }

    @Override
    public boolean time(long time) {
        if (world == null) {
            return false;
        }
        world.setFullTime(time);
        return true;
    }

    @Override
    public int playerCount() {
        return world.getPlayerCount();
    }

    @Override
    public int sleepingPlayerCount() {
        int count = 0;

        for (Player player : world.getPlayers()) {
            if (player.isSleeping()) {
                count++;
            }
        }

        return count;
    }

    @Override
    public boolean gameRuleAdvanceTime() {
        return Boolean.TRUE.equals(world.getGameRuleValue(GameRules.ADVANCE_TIME));
    }

    @Override
    public int gameRulePlayerSleepingPercentage() {
        int value = Optional.ofNullable(world.getGameRuleValue(GameRules.PLAYERS_SLEEPING_PERCENTAGE)).orElse(100);
        return Math.max(0, Math.min(100, value));
    }
}
