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

package xyz.mayahive.customdaytime.sponge.listener;

import lombok.RequiredArgsConstructor;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.action.SleepingEvent;
import xyz.mayahive.customdaytime.common.event.EventBus;
import xyz.mayahive.customdaytime.common.event.type.WorldSleepingPlayerCountChangeEvent;
import xyz.mayahive.customdaytime.sponge.platform.SpongeWorld;

@RequiredArgsConstructor
public class SleepingListener {

    private final EventBus eventBus;

    @Listener
    public void onSleepingPreEvent(SleepingEvent.Pre event) {
        Living living = event.living();
        if (!(living instanceof ServerPlayer player)) {return;}
        eventBus.fire(new WorldSleepingPlayerCountChangeEvent(new SpongeWorld(player.world())));
    }

    @Listener
    public void onSleepFinishEvent(SleepingEvent.Finish event) {
        Living living = event.living();
        if (!(living instanceof ServerPlayer player)) {return;}
        eventBus.fire(new WorldSleepingPlayerCountChangeEvent(new SpongeWorld(player.world())));
    }

}
